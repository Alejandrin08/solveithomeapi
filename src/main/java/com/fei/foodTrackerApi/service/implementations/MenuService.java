package com.fei.foodTrackerApi.service.implementations;

import com.fei.foodTrackerApi.dto.MenuDTO;
import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.model.Menu;
import com.fei.foodTrackerApi.model.Restaurant;
import com.fei.foodTrackerApi.repository.AccountRepository;
import com.fei.foodTrackerApi.repository.MenuRepository;
import com.fei.foodTrackerApi.repository.RestaurantRepository;
import com.fei.foodTrackerApi.service.interfaces.IMenu;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService implements IMenu {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    private Optional<Restaurant> getRestaurantByAccount(Integer accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        return restaurantRepository.getRestaurantByAccount(account.get());
    }

    private boolean dishExists(Restaurant restaurant, String dishName) {
        return menuRepository.existsByRestaurantAndDish(restaurant, dishName);
    }

    private Menu saveMenu(Restaurant restaurant, MenuDTO menuDTO, Menu existingMenu) {
        Menu menu = existingMenu != null ? existingMenu : new Menu();

        menu.setDish(menuDTO.getDish());
        menu.setPrice(menuDTO.getPrice());
        menu.setDescription(menuDTO.getDescription());
        menu.setImageUrl(menuDTO.getImageUrl());
        menu.setRestaurant(restaurant);

        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public MenuDTO createMenu(Integer accountId, MenuDTO menuDTO) {
        Optional<Restaurant> restaurant = getRestaurantByAccount(accountId);
        Menu menu = new Menu();
        if (restaurant.isPresent()) {
            if (dishExists(restaurant.get(), menuDTO.getDish())) {
                throw new RuntimeException("A dish with the same name already exists in this restaurant.");
            }
            menu = saveMenu(restaurant.get(), menuDTO, null);
        }
        return modelMapper.map(menu, MenuDTO.class);
    }

    @Override
    public MenuDTO getMenuRestaurant(String restaurantName, String dish) {
        Optional<MenuDTO> menu = menuRepository.getMenuDishByRestaurant(restaurantName, dish);
        return modelMapper.map(menu, MenuDTO.class);
    }

    @Override
    public List<MenuDTO> getAllMenuRestaurant(String restaurantName) {
        List<MenuDTO> menuList = new ArrayList<>();
        for (Menu menu : menuRepository.getAllMenusByRestaurant(restaurantName)) {
            menuList.add(modelMapper.map(menu, MenuDTO.class));
        }
        return menuList;
    }

    @Override
    @Transactional
    public MenuDTO updateMenuRestaurant(Integer accountId, String dish, MenuDTO menuDTO) {
        Optional<Restaurant> restaurant = getRestaurantByAccount(accountId);
        Menu menu = new Menu();
        if (restaurant.isPresent()) {
            Optional<Menu> existingMenu = menuRepository.findByRestaurantAndDish(restaurant.get(), dish);
            if (existingMenu.isEmpty()) {
                throw new RuntimeException("Dish not found in the specified restaurant.");
            }

            if (!existingMenu.get().getDish().equals(menuDTO.getDish()) && dishExists(restaurant.get(), menuDTO.getDish())) {
                throw new RuntimeException("A dish with the same name already exists in this restaurant.");
            }

            menu = saveMenu(restaurant.get(), menuDTO, existingMenu.orElse(null));
        }
        return modelMapper.map(menu, MenuDTO.class);
    }

    @Override
    @Transactional
    public boolean deleteMenuRestaurant(Integer accountId, String dish) {
        Optional<Restaurant> restaurant = getRestaurantByAccount(accountId);

        if (restaurant.isEmpty()) {
            throw new RuntimeException("Restaurant not found for account id: " + accountId);
        }

        Optional<Menu> existingMenu = menuRepository.findByRestaurantAndDish(restaurant.get(), dish);

        if (existingMenu.isEmpty()) {
            return false;
        }

        menuRepository.delete(existingMenu.get());
        return true;
    }
}
