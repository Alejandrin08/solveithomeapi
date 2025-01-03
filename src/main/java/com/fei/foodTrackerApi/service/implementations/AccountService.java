package com.fei.foodTrackerApi.service.implementations;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.AccountDTO;
import com.fei.foodTrackerApi.dto.AccountTypes;
import com.fei.foodTrackerApi.dto.CustomUserDetails;
import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.repository.AccountRepository;
import com.fei.foodTrackerApi.service.interfaces.IAccount;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccount {

    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setAccountType(accountDTO.getAccountType().toString());
        accountRepository.save(account);
        AccountDTO response = modelMapper.map(account, AccountDTO.class);
        response.setPassword(null);
        return response;
    }

    @Override
    public String login(AccountDTO accountDTO) {
        Optional<Account> accountOptional = accountRepository.findByEmail(accountDTO.getEmail());
        String token = null;

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (passwordEncoder.matches(accountDTO.getPassword(), account.getPassword())) {
                CustomUserDetails userDetails = new CustomUserDetails(account);
                token = jwtUtil.generateToken(userDetails);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } else {
            throw new RuntimeException("Account not found");
        }
        return token;
    }

    @Override
    public AccountDTO getAccount(Integer id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOptional.get();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPassword(null);
        accountDTO.setAccountType(AccountTypes.valueOf(account.getAccountType()));
        return accountDTO;
    }

    @Override
    @Transactional
    public String updateEmail(Integer id, String email) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOptional.get();

        if (!account.getEmail().equals(email) && accountRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        account.setEmail(email);
        accountRepository.save(account);

        CustomUserDetails userDetails = new CustomUserDetails(account);
        return jwtUtil.generateToken(userDetails);
    }

    @Override
    @Transactional
    public boolean updatePassword(String email, String newPassword) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        Account account = accountOptional.get();
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean isEmailValid(String email) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        return accountOptional.isPresent();
    }
}