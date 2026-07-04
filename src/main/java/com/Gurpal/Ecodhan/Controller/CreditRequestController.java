package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.CreditRequestRequestDto;
import com.Gurpal.Ecodhan.Dto.CreditRequestResponseDto;
import com.Gurpal.Ecodhan.Service.CreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("creditrequest")
public class CreditRequestController {

    CreditRequestService creditRequestService;

    @Autowired
    public CreditRequestController(CreditRequestService creditRequestService)
    {
        this.creditRequestService = creditRequestService;
    }

    @PostMapping
    public ResponseEntity<String> addCreditRequest(@RequestBody CreditRequestRequestDto creditRequestRequestDto, Authentication authentication)
    {
        String userName = authentication.getName();
        return ResponseEntity.ok(creditRequestService.setCreditRequest(userName,creditRequestRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<CreditRequestResponseDto>> getCreditRequest(Authentication authentication)
    {
        String userName = authentication.getName();
        return ResponseEntity.ok(creditRequestService.getCreditRequest(userName));
    }

    @DeleteMapping("/{creditRequestId}")
    public ResponseEntity<String> deleteCreditRequest(@PathVariable Long creditRequestId, Authentication authentication)
    {
        String userName = authentication.getName();
        return ResponseEntity.ok(creditRequestService.deleteCreditRequest(userName,creditRequestId));
    }

}
