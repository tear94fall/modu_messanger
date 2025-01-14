package com.example.profileservice.profile.entity;

import com.example.profileservice.global.entity.BaseTimeEntity;
import com.example.profileservice.profile.dto.CreateProfileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Profile extends BaseTimeEntity {

    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private ProfileType profileType;

    private String value;

    public Profile(CreateProfileDto createProfileDto) {
        this.memberId = createProfileDto.getMemberId();
        this.profileType = createProfileDto.getProfileType();
        this.value = createProfileDto.getValue();
    }
}
