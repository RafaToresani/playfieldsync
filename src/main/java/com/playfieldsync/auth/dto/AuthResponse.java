package com.playfieldsync.auth.dto;


import com.playfieldsync.entities.user.ERole;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String username;
    private ERole role;
}
