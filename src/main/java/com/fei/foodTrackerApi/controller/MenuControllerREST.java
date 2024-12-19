package com.fei.foodTrackerApi.controller;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.MenuDTO;
import com.fei.foodTrackerApi.service.interfaces.IMenu;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuControllerREST {

    private final IMenu menuService;
    private final JwtUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody @Valid MenuDTO menuDTO) {
        Integer id = jwtUtil.getAuthenticatedUserId();
        MenuDTO menu = menuService.createMenu(id, menuDTO);
        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<MenuDTO>> getAllMenusRestaurant(@RequestParam @Valid String restaurantName) {
        List<MenuDTO> menu = menuService.getAllMenuRestaurant(restaurantName);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @GetMapping("/{dish}")
    public ResponseEntity<MenuDTO> getMenu(@RequestParam @Valid String restaurantName, @PathVariable @Valid String dish) {
        MenuDTO menu = menuService.getMenuRestaurant(restaurantName, dish);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PutMapping("/{dish}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable @Valid String dish, @RequestBody @Valid MenuDTO menuDTO) {
        Integer id = jwtUtil.getAuthenticatedUserId();
        MenuDTO menu = menuService.updateMenuRestaurant(id, dish, menuDTO);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping("/{dish}")
    public ResponseEntity<Void> deleteMenu(@PathVariable @Valid String dish) {
        Integer id = jwtUtil.getAuthenticatedUserId();
        boolean deleted = menuService.deleteMenuRestaurant(id, dish);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
