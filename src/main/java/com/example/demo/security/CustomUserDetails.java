package com.example.demo.security;

import com.example.demo.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private final Member member;

    public CustomUserDetails(Member member){
        this.member =member;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(member.getRole()));
    }
    @Override
    public String getPassword(){
        return member.getPassword();
    }

    @Override
    public String getUsername(){
        return member.getEmail();
    }

    public Long getMemberId(){
        return member.getId();
    }
}
