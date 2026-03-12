package com.example.demo.controller;

import com.example.demo.dto.CreateMemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.dto.UpdateMemberRequest;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    //    생성
    @PostMapping
    public MemberResponse create(@RequestBody CreateMemberRequest request) {
        return memberService.create(request);
    }
    //    전체 조회
    @GetMapping
    public List<MemberResponse> findAll(){
        return memberService.findAll();
    }

//    단건조회
    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id){
        return memberService.findById(id);
    }
//    수정
    @PatchMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @RequestBody UpdateMemberRequest request){
        return memberService.update(id, request);
    }
//    삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
    }
}
