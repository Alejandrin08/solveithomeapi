package com.fei.foodTrackerApi.service.interfaces;

import com.fei.foodTrackerApi.dto.MenuDTO;

import java.util.List;

public interface IMenu {
    MenuDTO createMenu(Integer accountId, MenuDTO menuDTO);
    MenuDTO getMenuRestaurant(String restaurantName, String dish);
    List<MenuDTO> getAllMenuRestaurant(String restaurantName);
    MenuDTO updateMenuRestaurant(Integer accountId, String dish, MenuDTO menuDTO);
    boolean deleteMenuRestaurant(Integer accountId, String dish);
}
