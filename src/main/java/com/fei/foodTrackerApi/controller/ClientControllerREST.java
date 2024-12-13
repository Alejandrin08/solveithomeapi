package com.fei.foodTrackerApi.controller;

import com.fei.foodTrackerApi.config.JwtUtil;
import com.fei.foodTrackerApi.dto.ClientDTO;
import com.fei.foodTrackerApi.service.interfaces.IClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientControllerREST {

    private final IClient clientService;
    private final JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable @Valid Integer id) {
        Integer authenticatedUserId = jwtUtil.getAuthenticatedUserId();

        if (!id.equals(authenticatedUserId)) {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ClientDTO clientDTO = clientService.getClient(id);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ClientDTO> createClient(@PathVariable @Valid Integer id, @RequestBody @Valid ClientDTO clientDTO) {
        ClientDTO client = clientService.registerClient(id, clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable @Valid Integer id, @RequestBody @Valid ClientDTO clientDTO) {
        Integer authenticatedUserId = jwtUtil.getAuthenticatedUserId();

        if (!id.equals(authenticatedUserId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ClientDTO client = clientService.updateClient(id, clientDTO);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}
