package com.Gurpal.Ecodhan.Service;

import com.Gurpal.Ecodhan.Dto.CompanyRequestDto;
import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Entity.User;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import com.Gurpal.Ecodhan.Repository.CompanyRepository;
import com.Gurpal.Ecodhan.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    CompanyRepository companyRepository;
    UserRepository userRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,UserRepository userRepository)
    {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public String saveCompany(String userName, CompanyRequestDto companyRequestDto)
    {
        if(userRepository.findByUserName(userName).get().getCompany()!=null)
        {
            return "Company Already Exists";
        }
        Company company = new Company();
        company.setCompanyName(companyRequestDto.getCompanyName());
        company.setUser(userRepository.findByUserName(userName).get());
        User user = userRepository.findByUserName(userName).get();
        user.setCompany(company);
        companyRepository.save(company);
        return "Company Saved Successfully";
    }

    public String deleteCompany(String userName)
    {
        User user = userRepository.findByUserName(userName).get();
        if(user.getCompany()==null)
        {
            throw new NotExistException("Company Not Exists");
        }
        Company company = user.getCompany();
        user.setCompany(null);
        companyRepository.delete(company);
        return "Company Deleted Successfully";
    }

    public String updateCompany(String userName,CompanyRequestDto companyRequestDto)
    {
        User user  = userRepository.findByUserName(userName).get();
        if(user.getCompany()==null)
        {
            throw new NotExistException("Company Not Exists");
        }
        Company company = new Company();
        company.setCompanyId(user.getCompany().getCompanyId());
        company.setCompanyName(companyRequestDto.getCompanyName());
        company.setUser(user);
        companyRepository.save(company);

        return "Company Updated Successfully";
    }

}
