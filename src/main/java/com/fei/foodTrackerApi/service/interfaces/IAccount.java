package com.fei.foodTrackerApi.service.interfaces;

import com.fei.foodTrackerApi.dto.AccountDTO;

public interface IAccount {
    AccountDTO createAccount(AccountDTO accountDTO);
    String login(AccountDTO loginDTO);
    AccountDTO getAccount(Integer id);
    AccountDTO updateAccount(Integer id, AccountDTO accountDTO);
}
