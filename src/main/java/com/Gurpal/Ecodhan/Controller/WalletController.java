package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.WalletResponseDto;
import com.Gurpal.Ecodhan.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService)
    {
        this.walletService = walletService;
    }


    @GetMapping
    public ResponseEntity<WalletResponseDto> getWallet(Authentication authentication)
    {
        String userName = authentication.getName();
        WalletResponseDto walletResponseDto = walletService.getWallet(userName);
        return ResponseEntity.ok(walletResponseDto);
    }

    @PutMapping("/topup")
    public ResponseEntity<String> topup(Authentication authentication,@RequestParam Double amount)
    {
        String userName = authentication.getName();
        String result = walletService.topup(userName,amount);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> addWallet(Authentication authentication,@RequestParam Double amount)
    {
        String userName = authentication.getName();
        String result = walletService.createWallet(userName,amount);
        return ResponseEntity.ok(result);
    }
}
