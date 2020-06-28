package com.leonpahole.workoutapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserProfile {
    private Long id;
    private String name;
    private String email;
}