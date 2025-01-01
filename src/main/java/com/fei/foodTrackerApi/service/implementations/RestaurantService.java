package com.fei.foodTrackerApi.service.implementations;

import com.fei.foodTrackerApi.dto.AccountTypes;
import com.fei.foodTrackerApi.dto.RestaurantDTO;
import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.model.Restaurant;
import com.fei.foodTrackerApi.repository.AccountRepository;
import com.fei.foodTrackerApi.repository.RestaurantRepository;
import com.fei.foodTrackerApi.service.interfaces.IRestaurant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RestaurantDTO createRestaurant(Integer id, RestaurantDTO restaurantDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (restaurantRepository.existsByAccount(account)) {
            throw new RuntimeException("This account already has a restaurant registered.");
        }

        if (account.getAccountType().equals(AccountTypes.CLIENT.toString())) {
            account.setAccountType(AccountTypes.OWNER.toString());
            accountRepository.save(account);
        } else {
            throw new RuntimeException("This account does not have a restaurant registered.");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setAccount(account);
        restaurant.setRestaurantName(restaurantDTO.getRestaurantName());
        restaurant.setSchedule(restaurantDTO.getSchedule());
        restaurant.setPhoneNumberRestaurant(restaurantDTO.getPhoneNumber());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setCategoryName(restaurantDTO.getCategoryName());
        restaurant.setImageUrl(restaurantDTO.getImageUrl());
        restaurantRepository.save(restaurant);

        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
            restaurantDTOList.add(restaurantDTO);
        }
        return restaurantDTOList;
    }
    
    @Override
    @Transactional
    public RestaurantDTO updateRestaurant(Integer id, RestaurantDTO restaurantDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Optional<Restaurant> restaurantOptional = restaurantRepository.getRestaurantByAccount(account);

        if (restaurantOptional.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        Restaurant restaurant = restaurantOptional.get();

        restaurant.setSchedule(restaurantDTO.getSchedule());
        restaurant.setPhoneNumberRestaurant(restaurantDTO.getPhoneNumber());
        restaurant.setImageUrl(restaurantDTO.getImageUrl());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurantRepository.save(restaurant);

        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurantsByCategory(String category) {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.getAllRestaurantsByCategoryName(category)) {
            RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
            restaurantDTOList.add(restaurantDTO);
        }
        return restaurantDTOList;
    }
}
