package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.AdminCreateDto;
import com.Gurpal.Ecodhan.Dto.AdminCreditRequestListDto;
import com.Gurpal.Ecodhan.Dto.AdminDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.CreditRequest;
import com.Gurpal.Ecodhan.Entity.User;
import com.Gurpal.Ecodhan.Entity.WalletTransactionHistory;
import com.Gurpal.Ecodhan.Enum.CreditRequestStatus;
import com.Gurpal.Ecodhan.Enum.Role;
import com.Gurpal.Ecodhan.Enum.WalletTransactionType;
import com.Gurpal.Ecodhan.Repository.CompanyRepository;
import com.Gurpal.Ecodhan.Repository.CreditRequestRepository;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import com.Gurpal.Ecodhan.Repository.WalletTransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    UserRepository userRepository;
    CreditRequestRepository creditRequestRepository;
    WalletTransactionHistoryRepository walletTransactionHistoryRepository;
    PasswordEncoder passwordEncoder;
    CompanyRepository companyRepository;

    @Autowired
    public AdminService(UserRepository userRepository,CreditRequestRepository creditRequestRepository,WalletTransactionHistoryRepository walletTransactionHistoryRepository,PasswordEncoder passwordEncoder,CompanyRepository companyRepository)
    {
        this.userRepository = userRepository;
        this.creditRequestRepository = creditRequestRepository;
        this.walletTransactionHistoryRepository = walletTransactionHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
    }

    public String acceptCredit(AdminDto adminDto)
    {
        String status = adminDto.getStatus().trim().toUpperCase();
        Long creditRequestId = adminDto.getCreditRequestId();
        if(status.equals(CreditRequestStatus.APPROVED.name()))
        {
            CreditRequest creditRequest = creditRequestRepository.findById(creditRequestId).get();
            Company company = creditRequest.getCompany();
            company.getWallet().setGreenCredit(company.getWallet().getGreenCredit() + creditRequest.getAmount());
            creditRequest.setStatus(CreditRequestStatus.APPROVED);
            creditRequest.setReviewedAt(LocalDateTime.now());
            WalletTransactionHistory walletTransactionHistory = new WalletTransactionHistory();
            walletTransactionHistory.setWallet(company.getWallet());
            walletTransactionHistory.setCreditAmount(company.getWallet().getGreenCredit()-creditRequest.getAmount());
            walletTransactionHistory.setCreditAmountAfterTransaction(company.getWallet().getGreenCredit());
            walletTransactionHistory.setCreditChange(creditRequest.getAmount());
            walletTransactionHistory.setMoneyAmount(company.getWallet().getMoneyBalance());
            walletTransactionHistory.setMoneyAmountAfterTransaction(company.getWallet().getMoneyBalance());
            walletTransactionHistory.setMoneyChange(0.0);
            walletTransactionHistory.setType(WalletTransactionType.REWARDED);
            company.getWallet().getWalletTransactionHistoryList().add(walletTransactionHistory);
            walletTransactionHistoryRepository.save(walletTransactionHistory);
        }
        else if(status.equals(CreditRequestStatus.REJECTED.name()))
        {
            CreditRequest creditRequest = creditRequestRepository.findById(creditRequestId).get();
            creditRequest.setStatus(CreditRequestStatus.REJECTED);
            creditRequest.setReviewedAt(LocalDateTime.now());
            creditRequestRepository.save(creditRequest);
        }

        return "Respond Done";
    }

    public List<AdminCreditRequestListDto> getCreditRequestList()
    {
        List<CreditRequest> list = creditRequestRepository.findByStatus(CreditRequestStatus.PENDING);
        List<AdminCreditRequestListDto> dto = new ArrayList<>();
        for(CreditRequest creditRequest:list)
        {
            AdminCreditRequestListDto adminCreditRequestListDto = new AdminCreditRequestListDto();
            adminCreditRequestListDto.setCreditRequestId(creditRequest.getCreditRequestId());
            adminCreditRequestListDto.setAmount(creditRequest.getAmount());
            adminCreditRequestListDto.setProofUrl(creditRequest.getProofUrl());
            adminCreditRequestListDto.setDescription(creditRequest.getDescription());
            adminCreditRequestListDto.setCreatedAt(creditRequest.getCreatedAt());
            adminCreditRequestListDto.setCompany(creditRequest.getCompany().getCompanyName());
            dto.add(adminCreditRequestListDto);;
        }
        return dto;
    }

    public String createAdmin(AdminCreateDto adminCreateDto)
    {
        if(userRepository.findByUserName(adminCreateDto.getUserName()).isPresent())
        {
            return "Username Already Exists";
        }
        User user = new User();
        user.setUserName(adminCreateDto.getUserName());
        user.setEmail(adminCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(adminCreateDto.getPassword()));
        Company company = new Company();
        company.setUser(user);
        company.setCompanyName(adminCreateDto.getCompany());
        user.setCompany(company);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        companyRepository.save(company);

        return "Admin Created Successfully";

    }

}
