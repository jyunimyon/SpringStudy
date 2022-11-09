package jyunyy.demo.repository;


import jyunyy.demo.domain.Member;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MemoryMemberRepositoryTest {
    //굳이 public 안 해도 됨
    MemoryMemberRepository repository=new MemoryMemberRepository();
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    @Test
    public void save(){
        Member member =new Member();
        member.setName("Spring");

        repository.save(member);

        Member result=repository.findById(member.getId()).get();
        //get한게 똑같으면 참
        //System.out.println("result= "+(result == member));
        //Assertions.assertEquals(member,result); --> 이건 junit에서 쓰는 매소드
        Assertions.assertThat(member).isEqualTo(result); //요즘엔 이걸 제일 많이 씀
    }
    @Test
    public void findByName(){
        Member member1=new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("Spring2");
        repository.save(member2); //현재 spring1과 spring2라는 사람이 회원으로 등록된거임

        Member result= repository.findByName("Spring1").get(); //Spring2 라고 하면 오류 뜸. 당연함 member1의 값은 Spring1임
        Assertions.assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result=repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

}

