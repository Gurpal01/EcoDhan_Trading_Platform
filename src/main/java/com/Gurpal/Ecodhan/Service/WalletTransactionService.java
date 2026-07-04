package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.WalletTransactionHistoryDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.Orders;
import com.Gurpal.Ecodhan.Entity.Wallet;
import com.Gurpal.Ecodhan.Entity.WalletTransactionHistory;
import com.Gurpal.Ecodhan.Enum.OrderType;
import com.Gurpal.Ecodhan.Enum.WalletTransactionType;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import com.Gurpal.Ecodhan.Repository.WalletTransactionHistoryRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletTransactionService {

    WalletTransactionHistoryRepository walletTransactionHistoryRepository;
    UserRepository userRepository;

    @Autowired
    public WalletTransactionService(WalletTransactionHistoryRepository walletTransactionHistoryRepository,UserRepository userRepository)
    {
        this.walletTransactionHistoryRepository = walletTransactionHistoryRepository;
        this.userRepository = userRepository;
    }

    public List<WalletTransactionHistoryDto> getWalletTransaction(String userName)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company==null)
        {
            throw new NotExistException("Company Not Exist");
        }
        Wallet wallet = company.getWallet();
        if(wallet ==null)
        {
            throw new NotExistException("Wallet Not Exist");
        }
        List<WalletTransactionHistoryDto>dto = new ArrayList<>();
        List<WalletTransactionHistory>list = wallet.getWalletTransactionHistoryList();
        for(WalletTransactionHistory walletTransactionHistory:list)
        {
            WalletTransactionHistoryDto walletTransactionHistoryDto = new WalletTransactionHistoryDto();
            walletTransactionHistoryDto.setType(walletTransactionHistory.getType().toString());
            walletTransactionHistoryDto.setCreditAmount(walletTransactionHistory.getCreditAmount());
            walletTransactionHistoryDto.setMoneyAmount(walletTransactionHistory.getMoneyAmount());
            walletTransactionHistoryDto.setCreatedAt(walletTransactionHistory.getCreatedAt());
            walletTransactionHistoryDto.setMoneyChange(walletTransactionHistory.getMoneyChange());
            walletTransactionHistoryDto.setCreditChange(walletTransactionHistory.getCreditChange());
            walletTransactionHistoryDto.setMoneyAmountAfterTransaction(walletTransactionHistory.getMoneyAmountAfterTransaction());
            walletTransactionHistoryDto.setCreditAmountAfterTransaction(walletTransactionHistory.getCreditAmountAfterTransaction());
            dto.add(walletTransactionHistoryDto);
        }
        return dto;
    }

    public void createWalletTransaction(Orders orders,Orders orders1)
    {
        if(orders.getRemainingQuantity()>=orders1.getRemainingQuantity())
        {
            if(orders.getType() == OrderType.BUY)
            {
               WalletTransactionHistory walletTransactionHistorybuy = new WalletTransactionHistory();
               WalletTransactionHistory walletTransactionHistorysell = new WalletTransactionHistory();

               Wallet walletbuy = orders.getCompany().getWallet();
               Wallet walletsell = orders1.getCompany().getWallet();

               walletTransactionHistorybuy.setType(WalletTransactionType.BUY);
               walletTransactionHistorybuy.setCreditAmount(walletbuy.getGreenCredit());
               walletbuy.setGreenCredit(walletbuy.getGreenCredit()+orders1.getRemainingQuantity());
               walletTransactionHistorybuy.setCreditChange(orders1.getRemainingQuantity());
               walletTransactionHistorybuy.setMoneyAmount(walletbuy.getMoneyBalance());
               walletbuy.setMoneyBalance(walletbuy.getMoneyBalance()-orders1.getPrice()*orders1.getRemainingQuantity());
               walletTransactionHistorybuy.setMoneyChange(orders1.getPrice()*orders1.getRemainingQuantity());
               walletTransactionHistorybuy.setMoneyAmountAfterTransaction(walletbuy.getMoneyBalance());
               walletTransactionHistorybuy.setCreditAmountAfterTransaction(walletbuy.getGreenCredit());
               walletTransactionHistorybuy.setWallet(walletbuy);
               walletbuy.getWalletTransactionHistoryList().add(walletTransactionHistorybuy);
               walletTransactionHistoryRepository.save(walletTransactionHistorybuy);

               walletTransactionHistorysell.setType(WalletTransactionType.SELL);
               walletTransactionHistorysell.setCreditAmount(walletsell.getGreenCredit());
               walletsell.setGreenCredit(walletsell.getGreenCredit()-orders1.getRemainingQuantity());
               walletTransactionHistorysell.setCreditChange(orders1.getRemainingQuantity());
               walletTransactionHistorysell.setMoneyAmount(walletsell.getMoneyBalance());
               walletsell.setMoneyBalance(walletsell.getMoneyBalance()+orders1.getPrice()*orders1.getRemainingQuantity());
               walletTransactionHistorysell.setMoneyChange(orders1.getPrice()*orders1.getRemainingQuantity());
               walletTransactionHistorysell.setMoneyAmountAfterTransaction(walletsell.getMoneyBalance());
               walletTransactionHistorysell.setCreditAmountAfterTransaction(walletsell.getGreenCredit());
               walletTransactionHistorysell.setWallet(walletsell);
               walletsell.getWalletTransactionHistoryList().add(walletTransactionHistorysell);
               walletTransactionHistoryRepository.save(walletTransactionHistorysell);
            }
            else
            {
                WalletTransactionHistory walletTransactionHistorybuy = new WalletTransactionHistory();
                WalletTransactionHistory walletTransactionHistorysell = new WalletTransactionHistory();

                Wallet walletbuy = orders1.getCompany().getWallet();
                Wallet walletsell = orders.getCompany().getWallet();

                walletTransactionHistorybuy.setType(WalletTransactionType.BUY);
                walletTransactionHistorybuy.setCreditAmount(walletbuy.getGreenCredit());
                walletbuy.setGreenCredit(walletbuy.getGreenCredit()+orders1.getRemainingQuantity());
                walletTransactionHistorybuy.setCreditChange(orders1.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyAmount(walletbuy.getMoneyBalance());
                walletbuy.setMoneyBalance(walletbuy.getMoneyBalance()-orders1.getPrice()*orders1.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyChange(orders1.getPrice()*orders1.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyAmountAfterTransaction(walletbuy.getMoneyBalance());
                walletTransactionHistorybuy.setCreditAmountAfterTransaction(walletbuy.getGreenCredit());
                walletTransactionHistorybuy.setWallet(walletbuy);
                walletbuy.getWalletTransactionHistoryList().add(walletTransactionHistorybuy);
                walletTransactionHistoryRepository.save(walletTransactionHistorybuy);

                walletTransactionHistorysell.setType(WalletTransactionType.SELL);
                walletTransactionHistorysell.setCreditAmount(walletsell.getGreenCredit());
                walletsell.setGreenCredit(walletsell.getGreenCredit()-orders1.getRemainingQuantity());
                walletTransactionHistorysell.setCreditChange(orders1.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyAmount(walletsell.getMoneyBalance());
                walletsell.setMoneyBalance(walletsell.getMoneyBalance()+orders1.getPrice()*orders1.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyChange(orders1.getPrice()*orders1.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyAmountAfterTransaction(walletsell.getMoneyBalance());
                walletTransactionHistorysell.setCreditAmountAfterTransaction(walletsell.getGreenCredit());
                walletTransactionHistorysell.setWallet(walletsell);
                walletsell.getWalletTransactionHistoryList().add(walletTransactionHistorysell);
                walletTransactionHistoryRepository.save(walletTransactionHistorysell);
            }
        }
        else
        {
            if(orders.getType() == OrderType.BUY)
            {
                WalletTransactionHistory walletTransactionHistorybuy = new WalletTransactionHistory();
                WalletTransactionHistory walletTransactionHistorysell = new WalletTransactionHistory();

                Wallet walletbuy = orders.getCompany().getWallet();
                Wallet walletsell = orders1.getCompany().getWallet();

                walletTransactionHistorybuy.setType(WalletTransactionType.BUY);
                walletTransactionHistorybuy.setCreditAmount(walletbuy.getGreenCredit());
                walletbuy.setGreenCredit(walletbuy.getGreenCredit()+orders.getRemainingQuantity());
                walletTransactionHistorybuy.setCreditChange(orders.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyAmount(walletbuy.getMoneyBalance());
                walletbuy.setMoneyBalance(walletbuy.getMoneyBalance()-orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyChange(orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyAmountAfterTransaction(walletbuy.getMoneyBalance());
                walletTransactionHistorybuy.setCreditAmountAfterTransaction(walletbuy.getGreenCredit());
                walletTransactionHistorybuy.setWallet(walletbuy);
                walletbuy.getWalletTransactionHistoryList().add(walletTransactionHistorybuy);
                walletTransactionHistoryRepository.save(walletTransactionHistorybuy);

                walletTransactionHistorysell.setType(WalletTransactionType.SELL);
                walletTransactionHistorysell.setCreditAmount(walletsell.getGreenCredit());
                walletsell.setGreenCredit(walletsell.getGreenCredit()-orders.getRemainingQuantity());
                walletTransactionHistorysell.setCreditChange(orders.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyAmount(walletsell.getMoneyBalance());
                walletsell.setMoneyBalance(walletsell.getMoneyBalance()+orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyChange(orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyAmountAfterTransaction(walletsell.getMoneyBalance());
                walletTransactionHistorysell.setCreditAmountAfterTransaction(walletsell.getGreenCredit());
                walletTransactionHistorysell.setWallet(walletsell);
                walletsell.getWalletTransactionHistoryList().add(walletTransactionHistorysell);
                walletTransactionHistoryRepository.save(walletTransactionHistorysell);
            }
            else
            {
                WalletTransactionHistory walletTransactionHistorybuy = new WalletTransactionHistory();
                WalletTransactionHistory walletTransactionHistorysell = new WalletTransactionHistory();

                Wallet walletbuy = orders1.getCompany().getWallet();
                Wallet walletsell = orders.getCompany().getWallet();

                walletTransactionHistorybuy.setType(WalletTransactionType.BUY);
                walletTransactionHistorybuy.setCreditAmount(walletbuy.getGreenCredit());
                walletbuy.setGreenCredit(walletbuy.getGreenCredit()+orders.getRemainingQuantity());
                walletTransactionHistorybuy.setCreditChange(orders.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyAmount(walletbuy.getMoneyBalance());
                walletbuy.setMoneyBalance(walletbuy.getMoneyBalance()-orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyChange(orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorybuy.setMoneyAmountAfterTransaction(walletbuy.getMoneyBalance());
                walletTransactionHistorybuy.setCreditAmountAfterTransaction(walletbuy.getGreenCredit());
                walletTransactionHistorybuy.setWallet(walletbuy);
                walletbuy.getWalletTransactionHistoryList().add(walletTransactionHistorybuy);
                walletTransactionHistoryRepository.save(walletTransactionHistorybuy);

                walletTransactionHistorysell.setType(WalletTransactionType.SELL);
                walletTransactionHistorysell.setCreditAmount(walletsell.getGreenCredit());
                walletsell.setGreenCredit(walletsell.getGreenCredit()-orders.getRemainingQuantity());
                walletTransactionHistorysell.setCreditChange(orders.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyAmount(walletsell.getMoneyBalance());
                walletsell.setMoneyBalance(walletsell.getMoneyBalance()+orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyChange(orders1.getPrice()*orders.getRemainingQuantity());
                walletTransactionHistorysell.setMoneyAmountAfterTransaction(walletsell.getMoneyBalance());
                walletTransactionHistorysell.setCreditAmountAfterTransaction(walletsell.getGreenCredit());
                walletTransactionHistorysell.setWallet(walletsell);
                walletsell.getWalletTransactionHistoryList().add(walletTransactionHistorysell);
                walletTransactionHistoryRepository.save(walletTransactionHistorysell);
            }
        }

    }
}
