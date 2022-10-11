package jyunyy.demo.repository;

import jyunyy.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository{
    Member save(Member member); //회원이 저장소에 저장
    Optional<Member> findById(Long id);
    //저장소에 저장된 id, name 으로 회원 찾기 기능
    Optional<Member> findByName(String name);
    //optional 이란 null 처리 방법
    // 지금까지 저장된 모든 회원 리스트 반환
    List<Member> findAll();
}