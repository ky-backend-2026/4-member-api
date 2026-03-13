package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.CreateMemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.dto.UpdateMemberRequest;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    //회원 생성
    public MemberResponse create(CreateMemberRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member member = new Member(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                "ROLE_USER"
        );

        memberRepository.save(member);
        return new MemberResponse(member);
    }

//    전체 조회
    @Transactional(readOnly = true)
    public List<MemberResponse> findAll(){
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }
//    단건조회
    @Transactional(readOnly = true)
    public MemberResponse findById(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Member not found: "+id));
        return new MemberResponse(member);
    }

//    수정
    public MemberResponse update(Long id, UpdateMemberRequest request){
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Member not found: "+id));

        member.update(request.getName(), request.getEmail());

        return new MemberResponse(member);
    }
//    삭제

    public void delete(Long id){
        if(!memberRepository.existsById(id)){
            throw new IllegalArgumentException("Member not found: "+id);
        }
        memberRepository.deleteById(id);
    }
}
