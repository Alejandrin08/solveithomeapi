package com.fei.foodTrackerApi.dto;

import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.model.Client;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class CustomUserDetails implements UserDetails {

    private final Account account;
    private final Client client;

    public CustomUserDetails(Account account) {
        this.account = account;
        this.client = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getAccountType()));
    }

    public Integer getId() {
        return account.getId();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getClients().stream()
                .findFirst()
                .map(Client::getClientName)
                .orElse("N/A");
    }

    public String getAccountType() {
        return account.getAccountType();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
