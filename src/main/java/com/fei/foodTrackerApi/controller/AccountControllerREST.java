package com.fei.foodTrackerApi.controller;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.AccountDTO;
import com.fei.foodTrackerApi.dto.LoginResponseDTO;
import com.fei.foodTrackerApi.dto.UpdatePasswordDTO;
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

    @PutMapping("/")
    public ResponseEntity<Boolean> updatePassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        boolean result = accountService.updatePassword(updatePasswordDTO.getEmail(), updatePasswordDTO.getPassword());
        if (result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Boolean> isEmailValid(@PathVariable @Valid String email) {
        boolean isEmailValid = accountService.isEmailValid(email);
        if (isEmailValid) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateEmail(@PathVariable Integer id, @RequestBody @Valid String email) {
        Integer authenticatedUserId = jwtUtil.getAuthenticatedUserId();

        if (!id.equals(authenticatedUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            boolean isUpdated = accountService.updateEmail(id, email);
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
