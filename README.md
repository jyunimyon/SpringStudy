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
  
  
  HelloController에 다음 코드 추가
  
  
  ```
  @GetMapping("hello-mvc")
  public String helloMvc(@RequestParam("name") String name, Model model){
  model.addAttribute("name",name);
  return "hello-template"; }
  ```
  ("name"이 키 값, String name의 name은 그냥 파라미터)
  
  **원리**
  
  return 한 template의 html로 이동함
  
  예를 들어 내가 localhost:8080/hello-mvc?name=spring!!! 으로 하면
  
  name 키가 반응해서 String name의 name이 spring!!!으로 바뀌고 model에 담김 - ${name}이 모델에서 값을 꺼내는거임
  
  그리고 hello-template으로 이동해서 hello-spring을 반환하는거임

  따라서 localhost:8080/hello-mvc?name=spring!!! 의 소스 보기 누르면 html 문법 안에 hello spring!!!이 있음
  
  

  hello-template.html 코드
  ```
  <p th:text="'hello ' + ${name}">hello! empty</p>
  ```
## section 4
### 회원 관리 시스템 만들기
####  10강 비즈니스 요구사항 정리

  일반적인 웹 application의 계층 구조
  1. 컨트롤러
    웹 MVC의 컨트롤러 역할
  2. 서비스
    핵심 비즈니스 로직 구현
  3. 리포지토리
    데이터베이스에 접근, 도메인 객체를 DB에 저장 후 관리
  4. 도메인
    비즈네스 도메인 객체
    
* **회원 객체 생성**
  * Member.java 클래스로 구현
  * id와 name을 저장
  ```
  private Long id;
  private String name;
  ```
  * private 선언, setter/ getter함수로 접근

* **회원 리포지토리 인터페이스**
  * MemberRepository.java 인터페이스 구현
  * MemoryMemberRepository에서 구현할 함수 제한
  ```
  Member save(Member member);
  Optional<Member> findById(Long id);
  Optional<Member> findByName(String name);
  List<Member> findAll();
  ```



