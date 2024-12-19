package com.fei.foodTrackerApi.service.implementations;

import com.fei.foodTrackerApi.dto.RatingDTO;
import com.fei.foodTrackerApi.model.Client;
import com.fei.foodTrackerApi.model.Rating;
import com.fei.foodTrackerApi.model.Restaurant;
import com.fei.foodTrackerApi.repository.ClientRepository;
import com.fei.foodTrackerApi.repository.RatingRepository;
import com.fei.foodTrackerApi.repository.RestaurantRepository;
import com.fei.foodTrackerApi.service.interfaces.IRating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService implements IRating {

    private final RatingRepository ratingRepository;
    private final ClientRepository clientRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RatingDTO createRating(Integer accountID, String restaurantName, RatingDTO ratingDTO) {
        Optional<Client> clientOptional = clientRepository.findIdByAccountId(accountID);
        Optional<Restaurant> restaurantOptional = restaurantRepository.getRestaurantByRestaurantName(restaurantName);

        if (clientOptional.isEmpty()) {
            throw new RuntimeException("Client not found");
        }

        if (restaurantOptional.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        Client client = clientOptional.get();
        Restaurant restaurant = restaurantOptional.get();

        boolean alreadyRated = ratingRepository.existsByClientAndRestaurant(client, restaurant);
        if (alreadyRated) {
            throw new RuntimeException("Client has already rated this restaurant");
        }

        Rating rating = new Rating();
        rating.setClient(client);
        rating.setRestaurant(restaurant);
        rating.setRate(ratingDTO.getRate());
        rating.setComment(ratingDTO.getComment());
        ratingRepository.save(rating);

        updateRestaurantRating(restaurantName);
        ratingDTO.setClientName(client.getClientName());
        return modelMapper.map(rating, RatingDTO.class);
    }

    @Override
    public List<RatingDTO> getAllRatingsRestaurant(String restaurantName) {
        List<RatingDTO> ratingDTOList = new ArrayList<>();

        for (Rating rating : ratingRepository.findAllByRestaurant_RestaurantName(restaurantName)) {
            RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);

            String clientName = rating.getClient().getClientName();
            ratingDTO.setClientName(clientName);

            ratingDTOList.add(ratingDTO);
        }

        return ratingDTOList;
    }

    @Override
    public void updateRestaurantRating(String restaurantName) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.getRestaurantByRestaurantName(restaurantName);

        if (restaurantOptional.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        Restaurant restaurant = restaurantOptional.get();
        List<Rating> ratings = ratingRepository.findAllByRestaurant_RestaurantName(restaurantName);

        BigDecimal total = BigDecimal.ZERO;
        for (Rating rating : ratings) {
            total = total.add(rating.getRate());
        }

        BigDecimal average = BigDecimal.ZERO;
        if (!ratings.isEmpty()) {
            average = total.divide(BigDecimal.valueOf(ratings.size()), 2, BigDecimal.ROUND_HALF_UP);
        }

        restaurant.setAverageRating(average);
        restaurantRepository.save(restaurant);
    }
}
