package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.CompanyRequestDto;
import com.Gurpal.Ecodhan.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<String> saveCompany(Authentication authentication, @RequestBody CompanyRequestDto companyRequestDto)
    {
        String userName = authentication.getName();
        String result = companyService.saveCompany(userName,companyRequestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public  ResponseEntity<String> deleteCompany(Authentication authentication)
    {
        String userName = authentication.getName();
        String result = companyService.deleteCompany(userName);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> updateCompany(Authentication authentication, @RequestBody CompanyRequestDto companyRequestDto)
    {
        String userName = authentication.getName();
        String result = companyService.updateCompany(userName,companyRequestDto);
        return ResponseEntity.ok(result);
    }
}
