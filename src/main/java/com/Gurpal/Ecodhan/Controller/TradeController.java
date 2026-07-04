package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.TradeDto;
import com.Gurpal.Ecodhan.Service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class TradeController {

    TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService)
    {
        this.tradeService = tradeService;
    }


    @GetMapping
    public ResponseEntity<List<TradeDto>> getTrades(Authentication authentication)
    {
        String userName = authentication.getName();
        List<TradeDto> list = tradeService.getTrades(userName);
        return ResponseEntity.ok(list);
    }
}
