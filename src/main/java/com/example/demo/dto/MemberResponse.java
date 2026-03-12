package com.example.demo.dto;

import com.example.demo.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public MemberResponse(Member member){
        this.id = member.getId();
        this.name=member.getName();
        this.email=member.getEmail();
        this.createdAt= member.getCreatedAt();
    }
}
