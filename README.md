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
--------------------
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
---------------------
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
-------------------------
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
  * (중요) 테스트 케이스 하나가 끝나면 객체가 누적되어 에러가 발생하는 것을 방지하기 위해 데이터를 클리어 해줘야 한다
  
      1. MemoryMemberRepository에 clearStore 메소드 구현
      
        ```Java
        public void clearStore() {
          store.clear();
          }
        ```
      2. MemoryMemberRepositoryTest에 afterEach 이용, clearStore메소드 호출 코드 구현
    
      * @AfterEach--> 테스트(메소드) 하나 끝날 때마다 호출되는 메소드
        
        ```Java
          @AfterEach
          public void afterEach() {
            repository.clearStore();
            }
        ```
    
  * 같은 구조 중복 작성 시 변수 명만 바꾸는 단축키 shift+F6
----------------
#### 13강 회원 서비스 개발

회원 리포지토리와 도메인을 이용
* **service package 생성, MemberService.java를 통해 구현**
  * 회원가입 메소드 
  ```Java
  public Long join(Member member){
    //중복 회원 검증
    // 회원 정보 저장
    // ID 반환
  }
  ```
    <중복 회원 검증>
  
    요구사항에서 같은 이름은 회원 가입을 하지 못하도록 함을 명시
  
    Optional이 제공하는 ifPresent() 메소드 호출, null 발생 시에 null을 감싸서 반환
  
    > "로직이 나오는 경우 메소드를 뽑는 것이 좋다"
    shift+ctrl+t+Alt :extract method
    
    ```Java
    private void validateDuplicateMember(Member member) {
      memberRepository.findByName(member.getName())
          .ifPresent(m -> { // findByName의 결과가 이미 Optional이므로 바로 결과 자체에 메소드 호출 가능 
              throw new IllegalStateException("이미 존재하는 회원입니다.");
          });
    ```
    join 내에서 호출
    
    <회원 정보 저장> : 간단하게 save이용
    
  * 전체 회원 조회 메소드
     
  ```Java
  public List<Member> findMembers() {
    return memberRepository.findAll();
   }
  ```
  * 반환 타입 추출 단축키: ctrl+Alt+v
---------------------
#### 14강 회원 서비스 클래스 테스트

* 테스트 케이스 쉽게 만들깅 : ctrl+ shift+ t
* 테스트 케이스 구현 기본 문법 : given/ when/ then
* 예외 사항엔 try catch 대신 assertThrows
* test case에는 편리성을 위해 한글 사용 괜찮음
 
 **ex)**
 
 ```Java
 public void 중복_회원_예외() throws Exception { //한글 사용
  //Given
  Member member1 = new Member();
  member1.setName("spring");
  Member member2 = new Member();
  member2.setName("spring");
  //When
  memberService.join(member1);
  IllegalStateException e = assertThrows(IllegalStateException.class, //try catch 대신 assertThrows
    () -> memberService.join(member2));// memberService.join(member2)); 로직 실행시 IllegalStateException 예외가 발생해야 한다. 
   //then
  assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }
  ```
**# DI (Dependency Injection)**
+ 객체 누적 방지를 위해 MemoryMemberRepository의 clearStore를 호출 해야 함
+ MemberService와 MemberServiceTest의 main 메소드에서 MemoryMemberRepository를 직접 생성할 경우 다른 인스턴스를 사용하게 됨
+ 해결 방법 
  + MemberService의 MemberRepository 객체 생성 방법을 생성자로 바꾸어준다 
  ```Java
  private final MemberRepository memberRepository = new MemoryMemberRepository(); // 선언과 생성이 main에서 이루어짐
  ```
  -->
  
  ```Java
  private final MemberRepository memberRepository; // 선언만 main
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository; //생성은 생성자에서
   }
  ```
  + MemberServiveTest에서 BeforeEach 이용, 메소드 실행 전마다 객체 생성 메소드가 호출되게 함
    + **이렇듯 직접 생성하는 것이 아니라 외부의 것을 받아와서 사용하는 것이 DI**
  ```Java
  MemberService memberService;
  MemoryMemberRepository memberRepository;
  @BeforeEach
  public void beforeEach() {
  memberRepository = new MemoryMemberRepository();
  memberService = new MemberService(memberRepository);
  }
  ```  
+ 짠~ 같은 인스턴스 사용 가능 ~
----------------------
#### 15강 스프링 빈과 의존관계 ㅎㅎ

MemberService와 MemberRepository에 화면을 붙이고 싶음 --> controller와 vue template있어야 함
* **Spring Bean 등록 방법**
  1. **컴포넌트 스캔**: Spring이 뜰 때 component와 관련된 annotation이 있으면 스프링 빈을 만들어서 container에 등록
  2. 자바 코드로 직접 등록
* MemberController의 구현
  * MemberService 객체 생성 : main에서 new하지 않고 spring container에 객체 등록 후 가져다 씀
    1. new 대신 constructor 생성
    2. @Autowired --> 객체 생성 시점에 container에서 스프링 빈을 찾아 주입 (여기선 memberService와 memberController의 연결)
    ```Java
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
    this.memberService = memberService;
      }
    ```
* MemberService 와 MemberRepository 클래스의 변경
  * 순수한 자바 클래스는 Spring이 인식할 수 없음 --> annotation 추가 필요
  ```Java
  @Service
  public class MemberService {
  ```
  * 여기서 AutoWired는 memberService와 memberRepository간의 연결을 의미
  ```Java
  @Autowired
  public MemberService(MemberRepository memberRepository) {
  ```
  * @Repository의 추가
  ```Java
  @Repository
  public class MemoryMemberRepository implements MemberRepository {}
  ```
* **컨트롤러, 서비스, 리포지토리 등은 이렇게 정형화 되어있다.**
-----------------------
#### 16강 자바 코드로 스프링 빈 등록하기
* SpringConfig.java 구현
  * Component annotation은 제거 후 구현
  * **@Bean 사용**
  ```Java
  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }
  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
  ```
-------------------
#### DI 구현의 3가지 방법
1. constructor 주입 --> best
2. field 주입
3. setter 주입

#### 컴포넌트 스캔보다 자바 코드를 통한 스프링 빈 등록을 권장하는 이유
* 정형화된 컨트롤러, 서비스, 리포지토리 등은 컴포넌트 스캔이 편리함
* 하지만 상황에 따라 구현 클래스를 변경해야 하면 관련된 모든 코드의 변경이 필요함
* 자바 코드로 스프링 빈을 등록할 경우 '설정 파일'만 변경하면 됨
