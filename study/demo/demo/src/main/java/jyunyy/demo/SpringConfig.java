package jyunyy.demo;

import jyunyy.demo.repository.MemberRepository;
import jyunyy.demo.repository.MemoryMemberRepository;
import jyunyy.demo.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository(); // 인터페이스는 new가 안 되니까 구현체를 반환
    }
}
