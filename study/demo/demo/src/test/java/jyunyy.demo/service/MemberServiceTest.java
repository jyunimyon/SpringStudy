package jyunyy.demo.service;


import jyunyy.demo.domain.Member;
import jyunyy.demo.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach(){
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }
    @Test //테스트는 과감하게 한글로 바꿔도 됨.
    void 회원가입() { //Memberservice의 join 검증
        //given
        Member member=new Member();
        member.setName("hello");
        //when
        long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복회원예외(){
        //given
        Member member1=new Member();
        member1.setName("spring");

        Member member2=new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        /* 일단 이게 기본
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));*/
        //message가 맞는지 확인하고 싶을 땐
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*try{
        memberService.join(member2);
        fail();}
        catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}