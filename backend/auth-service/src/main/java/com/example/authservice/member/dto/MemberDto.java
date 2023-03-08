package com.example.authservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto implements Serializable {

    private String userId;
    private String auth;
    private Role role;
    private String email;
    private String username;
    private String statusMessage;
    private String profileImage;
    private String wallpaperImage;
    private List<ProfileDto> profileDtoList = new ArrayList<>();
}