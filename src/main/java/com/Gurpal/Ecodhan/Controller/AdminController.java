package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Dto.AdminCreateDto;
import com.Gurpal.Ecodhan.Dto.AdminCreditRequestListDto;
import com.Gurpal.Ecodhan.Dto.AdminDto;
import com.Gurpal.Ecodhan.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/requesthistory")
    public ResponseEntity<List<AdminCreditRequestListDto>> getRequestHistory()
    {
        return ResponseEntity.ok(adminService.getCreditRequestList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/requeststatus")
    public ResponseEntity<String> responseAdmin(@RequestBody AdminDto adminDto)
    {
        return ResponseEntity.ok(adminService.acceptCredit(adminDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createadmin")
    public ResponseEntity<String> setAdmin(@RequestBody AdminCreateDto adminCreateDto)
    {
        return ResponseEntity.ok(adminService.createAdmin(adminCreateDto));
    }

}
