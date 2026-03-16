package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.dto.TokenResponse;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.JwtTokenProvider;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private  final MemberRepository memberRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtTokenProvider jwtTokenProvider;


//    로그인
    @Transactional
    public TokenResponse login(LoginRequest request){
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 이메일 입니다."));

        if(!passwordEncoder.matches(request.getPassword(),member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일지하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getId(),member.getRole());

        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        member.updateRefreshToken(refreshToken);

        return new TokenResponse(accessToken,refreshToken);

    }

    @Transactional
    public TokenResponse reissue(String refreshToken){
        if(!jwtTokenProvider.validateToken(refreshToken)){
            throw new IllegalArgumentException("유효하지 않은 토큰 입니다.");
        }

        Member member= memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));


        String newAcessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getRole());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return new TokenResponse(newAcessToken,newRefreshToken);
    }

}
