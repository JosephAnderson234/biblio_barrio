package com.example.biblio_barrio.activity.interfaces.rest;

import com.example.biblio_barrio.activity.application.ActivityApplicationService;
import com.example.biblio_barrio.activity.interfaces.rest.dto.ActivityItemResponse;
import com.example.biblio_barrio.shared.security.SecurityUtils;
import com.example.biblio_barrio.shared.web.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityApplicationService activityApplicationService;

    @GetMapping("/my-activity")
    public ResponseEntity<PageResponse<ActivityItemResponse>> getMyActivity(
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(activityApplicationService.getMyActivity(userId, type, page, size));
    }
}
