package com.fei.foodTrackerApi.service.interfaces;

import com.fei.foodTrackerApi.dto.ClientDTO;

public interface IClient {
    ClientDTO createClient(Integer accountID, ClientDTO clientDTO);
    ClientDTO getClient(Integer clientID);
    ClientDTO updateClient(Integer id, ClientDTO clientDTO);

}
