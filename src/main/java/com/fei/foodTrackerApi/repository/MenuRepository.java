package com.fei.foodTrackerApi.repository;

import com.fei.foodTrackerApi.dto.MenuDTO;
import com.fei.foodTrackerApi.model.Menu;
import com.fei.foodTrackerApi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT new com.fei.foodTrackerApi.dto.MenuDTO(m.dish, m.price, m.imageUrl, m.description) " +
            "FROM Menu m JOIN m.restaurant r " +
            "WHERE r.restaurantName = :restaurantName AND m.dish = :dish")
    Optional<MenuDTO> getMenuDishByRestaurant(@Param("restaurantName") String restaurantName,
                                              @Param("dish") String dish);

    @Query("SELECT m FROM Menu m JOIN m.restaurant r WHERE r.restaurantName = :restaurantName")
    List<Menu> getAllMenusByRestaurant(@Param("restaurantName") String restaurantName);

    boolean existsByRestaurantAndDish(Restaurant restaurant, String dish);

    Optional<Menu> findByRestaurantAndDish(Restaurant restaurant, String dish);
}
