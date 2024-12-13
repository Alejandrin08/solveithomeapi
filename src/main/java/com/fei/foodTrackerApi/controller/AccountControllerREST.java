package com.fei.foodTrackerApi.controller;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.AccountDTO;
import com.fei.foodTrackerApi.dto.LoginResponseDTO;
import com.fei.foodTrackerApi.service.interfaces.IAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountControllerREST {

    private final IAccount accountService;
    private final JwtUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        AccountDTO account = accountService.createAccount(accountDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(account);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AccountDTO accountDTO) {
        String token = accountService.login(accountDTO);
        if (token != null) {
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable @Valid Integer id) {
        Integer authenticatedUserId = jwtUtil.getAuthenticatedUserId();

        if (!id.equals(authenticatedUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        AccountDTO accountDTO = accountService.getAccount(id);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable @Valid Integer id, @RequestBody @Valid AccountDTO accountDTO) {
        Integer authenticatedUserId = jwtUtil.getAuthenticatedUserId();

        if (!id.equals(authenticatedUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        AccountDTO account = accountService.updateAccount(id, accountDTO);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
