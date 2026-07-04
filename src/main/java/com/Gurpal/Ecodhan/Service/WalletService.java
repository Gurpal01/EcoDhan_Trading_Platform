package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.WalletResponseDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.Wallet;
import com.Gurpal.Ecodhan.Entity.WalletTransactionHistory;
import com.Gurpal.Ecodhan.Enum.WalletTransactionType;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import com.Gurpal.Ecodhan.Repository.WalletRepository;
import com.Gurpal.Ecodhan.Repository.WalletTransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    WalletRepository walletRepository;
    UserRepository userRepository;
    WalletTransactionHistoryRepository walletTransactionHistoryRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository,UserRepository userRepository,WalletTransactionHistoryRepository walletTransactionHistoryRepository)
    {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.walletTransactionHistoryRepository = walletTransactionHistoryRepository;
    }

    public String createWallet(String userName,Double amount)
    {
        if(userRepository.findByUserName(userName).get().getCompany()==null)
        {
            throw new NotExistException("Company Not Exists");
        }
        if(userRepository.findByUserName(userName).get().getCompany().getWallet()!=null)
        {
            throw new NotExistException("Wallet already Exists");
        }
        Wallet wallet = new Wallet();
        wallet.setMoneyBalance(amount);
        wallet.setGreenCredit(0);
        Company company = userRepository.findByUserName(userName).get().getCompany();
        wallet.setCompany(company);
        company.setWallet(wallet);
        walletRepository.save(wallet);
        if(amount !=0)
        {
            WalletTransactionHistory walletTransactionHistory = new WalletTransactionHistory();
            walletTransactionHistory.setType(WalletTransactionType.TOP_UP);
            walletTransactionHistory.setCreditAmount(0);
            walletTransactionHistory.setMoneyAmount(0.0);
            walletTransactionHistory.setMoneyAmountAfterTransaction(amount);
            walletTransactionHistory.setCreditAmountAfterTransaction(0);
            walletTransactionHistory.setWallet(wallet);
            walletTransactionHistory.setCreditChange(0);
            walletTransactionHistory.setMoneyChange(amount);
            wallet.getWalletTransactionHistoryList().add(walletTransactionHistory);
            wallet.setWalletTransactionHistoryList(wallet.getWalletTransactionHistoryList());
            walletTransactionHistoryRepository.save(walletTransactionHistory);
        }

        return "Wallet Created and top up with "+amount;
    }

    public String topup(String userName,Double amount)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company==null)
        {
            throw new NotExistException("Company Not Exists");
        }
        if(company.getWallet()==null)
        {
            throw new NotExistException("Wallet Not Exists");
        }
        Wallet wallet = company.getWallet();
        wallet.setMoneyBalance(wallet.getMoneyBalance()+amount);
        WalletTransactionHistory walletTransactionHistory = new WalletTransactionHistory();
        walletTransactionHistory.setType(WalletTransactionType.TOP_UP);
        walletTransactionHistory.setCreditAmount(wallet.getGreenCredit());
        walletTransactionHistory.setMoneyAmount(wallet.getMoneyBalance()-amount);
        walletTransactionHistory.setMoneyAmountAfterTransaction(wallet.getMoneyBalance());
        walletTransactionHistory.setCreditAmountAfterTransaction(wallet.getGreenCredit());
        walletTransactionHistory.setWallet(wallet);
        walletTransactionHistory.setCreditChange(0);
        walletTransactionHistory.setMoneyChange(amount);
        List<WalletTransactionHistory>list = wallet.getWalletTransactionHistoryList();
        list.add(walletTransactionHistory);
        wallet.setWalletTransactionHistoryList(list);
        walletTransactionHistoryRepository.save(walletTransactionHistory);
        return "Wallet is top us with "+amount;
    }

    public WalletResponseDto getWallet(String userName)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company == null)
        {
            throw new NotExistException("Company Not Exists");
        }
        Wallet wallet = company.getWallet();
        if(wallet==null)
        {
            throw new NotExistException("Wallet Not Exists");
        }
        WalletResponseDto walletResponseDto = new WalletResponseDto();
        walletResponseDto.setGreenCredit(wallet.getGreenCredit());
        walletResponseDto.setMoneyBalance(wallet.getMoneyBalance());
        return walletResponseDto;
    }
}
