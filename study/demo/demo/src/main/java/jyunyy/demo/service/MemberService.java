package jyunyy.demo.service;

import jyunyy.demo.domain.Member;
import jyunyy.demo.repository.MemberRepository;
import jyunyy.demo.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    //리포지토리 생성
    //private final MemberRepository memberRepository=new MemoryMemberRepository(); //memberRepository 객체 생성
    private final MemberRepository memberRepository; //위에 처럼 메인 코드에서 직접 new하는게 아니라
    public MemberService(MemberRepository memberRepository){ // 이렇게 외부에서 멤버리포지토리를 넣어줌 이게 바로 Dependency Injection
        this.memberRepository=memberRepository;
    }
    /*회원가입*/
    public long join(Member member){
        /*요구사항 --> 같은 이름의 중복 회원 안 됨 */
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m-> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); 이렇게 해도 되지만 아래처럼 하는 것을 권장. */
        /*
         memberRepository.findByName(member.getName())
                        .ifPresent(m ->{
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        } );
          근데 이렇게 로직이 나오는 경우엔 메소드로 추출하는 것이 좋음
        */
        validDuplicateMember(member); //중복회원 검증 메소드
        memberRepository.save(member);
        return member.getId();
    }

    private void validDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                } );
    }
    /*전체 회원 조회*/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
