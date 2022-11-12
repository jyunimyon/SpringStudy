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
  
  
  ```Java
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
  ```html
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
#### 11강 회원 도메인과 리포지토리 만들기    
* **회원 객체 생성**
  * Member.java 클래스로 구현
  * id와 name을 저장
  ```Java
  private Long id;
  private String name;
  ```
  * private 선언, setter/ getter함수로 접근

* **회원 리포지토리 인터페이스**
  * MemberRepository.java 인터페이스 구현
  * MemoryMemberRepository에서 구현할 함수 제한
  ```Java
  Member save(Member member);
  Optional<Member> findById(Long id);
  Optional<Member> findByName(String name);
  List<Member> findAll();
  ```
    * Optional 이란?
      null이 올 수 있는 값을 감싸는 Wrapper 클래스
      
      그냥 null값을 반환하지 않는다.
* **회원 리포지토리 구현체**
  * 키는 아이디로, 값은 Member 클래스로
  ```Java
  private static Map<Long, Member> store = new HashMap<>();
  ```
  * save 함수에서는 아이디 세팅 후 store객체에 저장
  * findById 함수에서는 아이디를 통해 해당 Member 값 반환
  * findAll 함수에서는 모든 Member 값 반환
  * findByName 함수에서는 이름(name)을 통해 해당 Member 값 반환

#### 12강 회원 리포지토리 테스트 케이스 작성
* **테스트 케이스 작성의 이유:**
  보통은 main 메소드나 컨트롤러를 통해 실행하지만 시간이 오래 걸리며 반복이 어렵다는 단점 있음
  --> JUnit 프레임워크 이용, test case 작성 및 실행
* **test의 하위 폴더에 MemoryMemberRepositoryTest.java 구현**
  * MemoryMemberRepository에 있는 여러가지 메소드들을 테스트할 코드를 구현한다.
    * Assertion이란?
    개발자가 자신이 개발한 프로그램에서 가정하고 있는 사실이 올바른 지 검사할 수 있는 기능이다
    
    즉 검증을 위한 것이다
    ```Java
    import static org.assertj.core.api.Assertions.*;
    ```
    import를 static으로 할 경우 코드에서
    ```Java
    Assertions.assertThat(result).isEqualTo(member);
    ```
    대신에
    ```Java
    assertThat(result).isEqualTo(member);
    ```
    를 바로 쓸 수 있어 편리하다.
    
