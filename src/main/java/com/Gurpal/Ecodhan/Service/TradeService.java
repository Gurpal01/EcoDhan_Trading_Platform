package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.TradeDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.Orders;
import com.Gurpal.Ecodhan.Entity.Trade;
import com.Gurpal.Ecodhan.Enum.OrderType;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.TradeRepository;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {

    TradeRepository tradeRepository;
    UserRepository userRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository,UserRepository userRepository)
    {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    public void tradeCreate(Orders orders, Orders orders1)
    {
        Integer storedQuant = orders1.getRemainingQuantity();
        Trade trade = new Trade();

        if(orders.getRemainingQuantity()>=storedQuant)
        {
            if(orders.getType() == OrderType.BUY)
            {
                trade.setBuyerCompany(orders.getCompany().getCompanyName());
                trade.setSellerCompany(orders1.getCompany().getCompanyName());
                trade.setPrice(orders1.getPrice());
                trade.setQuantity(storedQuant);
                trade.setBuyOrder(orders);
                trade.setSellOrder(orders1);
                tradeRepository.save(trade);
            }
            else
            {
                trade.setBuyerCompany(orders1.getCompany().getCompanyName());
                trade.setSellerCompany(orders.getCompany().getCompanyName());
                trade.setPrice(orders1.getPrice());
                trade.setQuantity(storedQuant);
                trade.setBuyOrder(orders1);
                trade.setSellOrder(orders);
                tradeRepository.save(trade);
            }
        }
        else
        {
            if(orders.getType() == OrderType.BUY)
            {
                trade.setBuyerCompany(orders.getCompany().getCompanyName());
                trade.setSellerCompany(orders1.getCompany().getCompanyName());
                trade.setPrice(orders1.getPrice());
                trade.setQuantity(orders.getRemainingQuantity());
                trade.setBuyOrder(orders);
                trade.setSellOrder(orders1);
                tradeRepository.save(trade);
            }
            else
            {
                trade.setBuyerCompany(orders1.getCompany().getCompanyName());
                trade.setSellerCompany(orders.getCompany().getCompanyName());
                trade.setPrice(orders1.getPrice());
                trade.setQuantity(orders.getRemainingQuantity());
                trade.setBuyOrder(orders1);
                trade.setSellOrder(orders);
                tradeRepository.save(trade);
            }
        }
    }

    public List<TradeDto> getTrades(String userName)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company==null)
        {
            throw new NotExistException("Company Not Exits");
        }
        List<Trade>list = tradeRepository.findByBuyerCompanyOrSellerCompany(company.getCompanyName(),company.getCompanyName());
        List<TradeDto> dto = new ArrayList<>();
        for(Trade trade:list)
        {
            TradeDto tradeDto = new TradeDto();
            tradeDto.setBuyerCompany(trade.getBuyerCompany());
            tradeDto.setSellerCompany(trade.getSellerCompany());
            tradeDto.setPrice(trade.getPrice());
            tradeDto.setQuantity(trade.getQuantity());
            tradeDto.setCreateAt(trade.getCreateAt());
            dto.add(tradeDto);
        }
        return dto;
    }
}
