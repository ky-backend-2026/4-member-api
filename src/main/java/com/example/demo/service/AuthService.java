package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private  final MemberRepository memberRepository;
    private  static final String LOGIN_MEMBER_ID="LOGIN_MEMBER_ID";

//    로그인
    @Transactional
    public MemberResponse login(LoginRequest request, HttpSession session){
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 이메일 입니다."));

        if(!member.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일지하지 않습니다.");
        }

        session.setAttribute(LOGIN_MEMBER_ID,member.getId());

        return new MemberResponse(member);

    }
//    로그아웃
    @Transactional
    public  void  logout(HttpSession session){
        session.invalidate();
    }

//    getLoginMember
    public MemberResponse getLoginMember(HttpSession session){
        Long loginMemberId = (Long) session.getAttribute(LOGIN_MEMBER_ID);

        if(loginMemberId==null){
            throw new IllegalArgumentException("로그인한 사용자가 없습니다");
        }
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(()->new IllegalArgumentException("회원을 찾을 수 없습니다"));

        return new MemberResponse(member);
    }
}
