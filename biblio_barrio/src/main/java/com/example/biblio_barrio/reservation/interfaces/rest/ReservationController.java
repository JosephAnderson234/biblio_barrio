package com.example.biblio_barrio.reservation.interfaces.rest;

import com.example.biblio_barrio.reservation.application.ReservationApplicationService;
import com.example.biblio_barrio.reservation.interfaces.rest.dto.ReservationCreateRequest;
import com.example.biblio_barrio.reservation.interfaces.rest.dto.ReservationResponse;
import com.example.biblio_barrio.shared.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationApplicationService reservationApplicationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationApplicationService.create(request, userId));
    }
}
