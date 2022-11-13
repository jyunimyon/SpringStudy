package jyunyy.demo.controller;

import jyunyy.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    //필드 주입
    /*@Autowired*/ private MemberService memberService;// =new MemberService(); 권장 X
    //    생성자 주입
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }
    //setter주입
//    @Autowired
//    public void setMemberService(MemberService memberService){
//        this.memberService=memberService;
//    }
}
