package com.fei.foodTrackerApi.service.implementations;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.AccountTypes;
import com.fei.foodTrackerApi.dto.CustomUserDetails;
import com.fei.foodTrackerApi.dto.RestaurantDTO;
import com.fei.foodTrackerApi.dto.RestaurantLocationDTO;
import com.fei.foodTrackerApi.model.Account;
import com.fei.foodTrackerApi.model.Restaurant;
import com.fei.foodTrackerApi.repository.AccountRepository;
import com.fei.foodTrackerApi.repository.RestaurantRepository;
import com.fei.foodTrackerApi.service.interfaces.IRestaurant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public Map<String, Object> createRestaurant(Integer id, RestaurantDTO restaurantDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (restaurantRepository.existsByAccount(account)) {
            throw new RuntimeException("This account already has a restaurant registered.");
        }

        if (account.getAccountType().equals(AccountTypes.CLIENT.toString())) {
            account.setAccountType(AccountTypes.OWNER.toString());
            accountRepository.save(account);
        } else if (!account.getAccountType().equals(AccountTypes.OWNER.toString())) {
            throw new RuntimeException("This account type cannot register a restaurant.");
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

        CustomUserDetails userDetails = new CustomUserDetails(account);
        String newToken = jwtUtil.generateToken(userDetails);

        RestaurantDTO savedRestaurant = modelMapper.map(restaurant, RestaurantDTO.class);
        Map<String, Object> response = new HashMap<>();
        response.put("restaurant", savedRestaurant);
        response.put("newToken", newToken);

        return response;
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

    @Override
    public RestaurantDTO getRestaurantById(Integer id) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(id);
            return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Override
    public List<RestaurantLocationDTO> getAllLocationRestaurants() {
        List<RestaurantLocationDTO> restaurantDTOList = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            RestaurantLocationDTO RestaurantLocationDTO = modelMapper.map(restaurant, RestaurantLocationDTO.class);
            RestaurantLocationDTO.setLocation(restaurant.getLocation());
            restaurantDTOList.add(RestaurantLocationDTO);
        }
        return restaurantDTOList;
    }
}
