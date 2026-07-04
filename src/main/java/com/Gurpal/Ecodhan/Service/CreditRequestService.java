package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.CreditRequestRequestDto;
import com.Gurpal.Ecodhan.Dto.CreditRequestResponseDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.CreditRequest;
import com.Gurpal.Ecodhan.Entity.User;
import com.Gurpal.Ecodhan.Enum.CreditRequestStatus;
import com.Gurpal.Ecodhan.Enum.Role;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.CreditRequestRepository;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditRequestService {

    UserRepository userRepository;
    CreditRequestRepository creditRequestRepository;

    public CreditRequestService(UserRepository userRepository,CreditRequestRepository creditRequestRepository)
    {
        this.userRepository = userRepository;
        this.creditRequestRepository = creditRequestRepository;
    }

    public String setCreditRequest(String userName, CreditRequestRequestDto creditRequestRequestDto)
    {
        User user = userRepository.findByUserName(userName).get();
        if(user.getCompany().getWallet()==null)
        {
            return "Wallet Not Set";
        }
        if(user.getCompany()!=null)
        {
            Company company = user.getCompany();
            CreditRequest creditRequest = new CreditRequest();
            creditRequest.setDescription(creditRequestRequestDto.getDescription());
            creditRequest.setProofUrl(creditRequestRequestDto.getProofUrl());
            creditRequest.setStatus(CreditRequestStatus.PENDING);
            creditRequest.setCompany(company);
            creditRequest.setAmount(creditRequestRequestDto.getAmount());
            company.getCreditRequests().add(creditRequest);
            creditRequestRepository.save(creditRequest);
            return "Credit Request Made Successfully";
        }
        else
        {
            return "Company Not Registered";
        }
    }

    public String deleteCreditRequest(String userName,Long creditRequestId)
    {
        Company company = userRepository.findByUserName(userName).get().getCompany();
        if(company!=null && userRepository.findByUserName(userName).get().getCompany().getCreditRequests().contains(creditRequestRepository.findById(creditRequestId).get()))
        {
            CreditRequest creditRequest = creditRequestRepository.findById(creditRequestId).get();
            userRepository.findByUserName(userName).get().getCompany().getCreditRequests().remove(creditRequest);
            creditRequestRepository.delete(creditRequest);

            return "Credit Request Deleted Successfully";
        }
        else
        {
            throw new NotExistException("Wrong Id or Company Not Exists");
        }

    }

    public List<CreditRequestResponseDto> getCreditRequest(String userName)
    {
        if(userRepository.findByUserName(userName).get().getCompany()!=null)
        {
            List<CreditRequestResponseDto> dto = new ArrayList<>();
            List<CreditRequest> creditRequests = userRepository.findByUserName(userName).get().getCompany().getCreditRequests();
            for (CreditRequest creditRequest : creditRequests) {
                CreditRequestResponseDto creditRequestResponseDto = new CreditRequestResponseDto();

                creditRequestResponseDto.setDescription(creditRequest.getDescription());
                creditRequestResponseDto.setStatus(creditRequest.getStatus().toString());
                creditRequestResponseDto.setAmount(creditRequest.getAmount());
                creditRequestResponseDto.setReviewedAt(creditRequest.getReviewedAt());
                creditRequestResponseDto.setCreatedAt(creditRequest.getCreatedAt());
                creditRequestResponseDto.setProofUrl(creditRequest.getProofUrl());

                dto.add(creditRequestResponseDto);
            }

            return dto;
        }
        else
        {
            throw new NotExistException("Company Not Exists");
        }

    }
}
