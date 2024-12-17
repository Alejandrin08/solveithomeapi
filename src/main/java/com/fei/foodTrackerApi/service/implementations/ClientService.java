package com.fei.foodTrackerApi.service.implementations;

import com.fei.foodTrackerApi.dto.AccountTypes;
import com.fei.foodTrackerApi.dto.ClientDTO;
import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.model.Client;
import com.fei.foodTrackerApi.repository.AccountRepository;
import com.fei.foodTrackerApi.repository.ClientRepository;
import com.fei.foodTrackerApi.service.interfaces.IClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements IClient {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ClientDTO createClient(Integer id, ClientDTO clientDTO) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOptional.get();

        String accountType = AccountTypes.CLIENT.toString();
        if (!accountType.equals(account.getAccountType())) {
            throw new RuntimeException("Account must be of type client");
        }

        if (clientRepository.existsByAccountId(account.getId())) {
            throw new RuntimeException("A client is already associated with this account");
        }

        Client client = new Client();
        client.setAccount(accountOptional.get());
        client.setClientName(clientDTO.getName());
        client.setPhoneNumber(clientDTO.getPhone());
        client.setLocationClient(clientDTO.getLocation());
        clientRepository.save(client);

        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO getClient(Integer id) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isEmpty()) {
            throw new RuntimeException("Client not found");
        }

        return modelMapper.map(client.get(), ClientDTO.class);
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Integer id, ClientDTO clientDTO) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new RuntimeException("Client not found");
        }
        Client client = clientOptional.get();

        client.setClientName(clientDTO.getName());
        client.setPhoneNumber(clientDTO.getPhone());
        client.setLocationClient(clientDTO.getLocation());
        clientRepository.save(client);

        return modelMapper.map(client, ClientDTO.class);
    }
}