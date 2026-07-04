package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.OrderRequestDto;
import com.Gurpal.Ecodhan.Dto.OrderResponseDto;
import com.Gurpal.Ecodhan.Entity.*;
import com.Gurpal.Ecodhan.Enum.OrderStatus;
import com.Gurpal.Ecodhan.Enum.OrderType;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.OrderRepository;
import com.Gurpal.Ecodhan.Repository.TradeRepository;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    TradeService tradeService;
    WalletTransactionService walletTransactionService;

    @Autowired
    public OrderService(OrderRepository orderRepository,UserRepository userRepository,TradeService tradeService,WalletTransactionService walletTransactionService)
    {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tradeService = tradeService;
        this.walletTransactionService = walletTransactionService;
    }

    @Transactional
    public String createOrder(OrderRequestDto orderRequestDto, String userName)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company==null)
        {
            throw new NotExistException("Company Not Exists");
        }

        Wallet wallet = company.getWallet();
        if(wallet==null)
        {
            throw new NotExistException("Company Not Created Yet");
        }

        Integer quantity = orderRequestDto.getQuantity();
        Double price = orderRequestDto.getPrice();
        OrderType type = orderRequestDto.getType();
        Integer remainingQuantity = orderRequestDto.getQuantity();

        if(type==OrderType.BUY && wallet.getMoneyBalance()<quantity*price)
        {
            throw new NotExistException("Not Enough Balance in Wallet.");
        }
        if(type==OrderType.SELL && wallet.getGreenCredit()<quantity)
        {
            throw new NotExistException("Not Enough Green Credits in Wallet");
        }

        Orders orders = new Orders();
        orders.setQuantity(quantity);
        orders.setPrice(price);
        orders.setType(type);
        orders.setRemainingQuantity(remainingQuantity);
        orders.setStatus(OrderStatus.OPEN);
        orders.setCompany(company);
        company.getOrders().add(orders);
        orderRepository.save(orders);

        OrderType orderType;
        List<Orders> list;
        List<OrderStatus> l = new ArrayList<>();
        l.add(OrderStatus.OPEN);
        l.add(OrderStatus.PARTIALLY_OPEN);
        if(type == OrderType.BUY)
        {
            orderType = OrderType.SELL;
            list = orderRepository.findByTypeAndPriceLessThanEqualAndStatusInOrderByPriceAscCreatedAtAsc(orderType,price,l);
        }
        else
        {
            orderType = OrderType.BUY;
            list = orderRepository.findByTypeAndPriceGreaterThanEqualAndStatusInOrderByPriceDescCreatedAtAsc(orderType,price,l);
        }

        if(list.isEmpty())
        {
            return "Order is Listed";
        }
        else
        {
            for(Orders orders1:list)
            {
                Integer storedQuant = orders1.getRemainingQuantity();
                if(storedQuant < orders.getRemainingQuantity())
                {
                    tradeService.tradeCreate(orders,orders1);
                    walletTransactionService.createWalletTransaction(orders,orders1);

                    orders1.setRemainingQuantity(0);
                    orders1.setStatus(OrderStatus.COMPLETED);
                    orders.setRemainingQuantity(orders.getRemainingQuantity()-storedQuant);
                    if(orders.getStatus()==OrderStatus.OPEN)
                    {
                        orders.setStatus(OrderStatus.PARTIALLY_OPEN);
                    }
                    if(orders.getRemainingQuantity()==0)
                    {
                        break;
                    }
                }
                else if(storedQuant>orders.getRemainingQuantity())
                {
                    tradeService.tradeCreate(orders,orders1);
                    walletTransactionService.createWalletTransaction(orders,orders1);

                    if(orders1.getStatus()==OrderStatus.OPEN)
                    {
                        orders1.setStatus(OrderStatus.PARTIALLY_OPEN);
                    }

                    orders1.setRemainingQuantity(storedQuant-orders.getRemainingQuantity());
                    orders.setStatus(OrderStatus.COMPLETED);
                    orders.setRemainingQuantity(0);

                    break;
                }
                else
                {
                    tradeService.tradeCreate(orders,orders1);
                    walletTransactionService.createWalletTransaction(orders,orders1);

                    orders.setStatus(OrderStatus.COMPLETED);
                    orders1.setStatus(OrderStatus.COMPLETED);
                    orders.setRemainingQuantity(0);
                    orders1.setRemainingQuantity(0);

                    break;
                }
            }

        }

        return "Order is Listed";
    }

    public List<OrderResponseDto> getOrders(String userName)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company==null)
        {
            throw new NotExistException("Company Not Exist");
        }
        List<Orders>list = company.getOrders();
        List<OrderResponseDto>dto = new ArrayList<>();
        for(Orders orders:list)
        {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(orders.getOrderId());
            orderResponseDto.setQuantity(orders.getQuantity());
            orderResponseDto.setRemainingQuantity(orders.getRemainingQuantity());
            orderResponseDto.setPrice(orders.getPrice());
            orderResponseDto.setType(orders.getType().toString());
            orderResponseDto.setStatus(orders.getStatus().toString());
            dto.add(orderResponseDto);
        }
        return dto;
    }

}
