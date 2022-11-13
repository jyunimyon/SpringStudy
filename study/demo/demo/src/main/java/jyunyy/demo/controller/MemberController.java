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
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
        //여기로 이동해라. 이거 실행되면 내가 template에 만들어둔 members/create어쩌구 여기로 이동하는거임
        //여튼 createMemberForm.html이 뿌려지는거임
    }
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member=new Member();
        member.setName(form.getName());
        //System.out.println("member="+member.getName());
        memberService.join(member); //join을 통해 넣어주기

        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList"; //이거 이제 만들러 가야함 왜냐 여기로 이동해야되니까
    }
}
