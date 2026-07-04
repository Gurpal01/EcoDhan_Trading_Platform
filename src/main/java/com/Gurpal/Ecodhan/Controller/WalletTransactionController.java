package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.WalletTransactionHistoryDto;
import com.Gurpal.Ecodhan.Service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class WalletTransactionController {

    WalletTransactionService walletTransactionService;

    @Autowired
    public WalletTransactionController(WalletTransactionService walletTransactionService)
    {
        this.walletTransactionService = walletTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<WalletTransactionHistoryDto>> getWalletTransaction(Authentication authentication)
    {
        String userName = authentication.getName();
        List<WalletTransactionHistoryDto>list = walletTransactionService.getWalletTransaction(userName);
        return ResponseEntity.ok(list);
    }
}
