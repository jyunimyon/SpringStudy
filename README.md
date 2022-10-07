# SpringStudy
## section2
### 웹 개발 3가지
* **정적 컨텐츠**

  직관적으로 파일을 그대로 내려준다고 이해하면 됨
  
  hello-static.html 작성
  
  * 원리
  
    내장 톰캣 서버가 html 요청 받음-> 컨트롤러에서 html 찾음 -> 없을 시 static에서 html 찾음 -> 찾으면 반환
* **MVC(model view controller)**
  * view -> 화면 그리는 데에 모든 역량 집중
  * controller, model -> 서버, 내부 장치에 역량 집중
  HElloController에 다음 코드 추가
  
  ```
  
  @GetMapping("hello-mvc")
  public String helloMvc(@RequestParam("name") String name, Model model){
   model.addAttribute("name",name);
   return "hello-template"; }
   
  ```
