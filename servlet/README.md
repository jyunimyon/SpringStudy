## 들어가며
클라이언트 쪽에서 요청을 보냈을 때, 서버에서 처리해야 하는 업무는 매우 많다. 

- 서버 TCP/IP 연결 대기, 소켓 연결
- HTTP 요청 메세지 파싱해서 읽기
- content-type 확인 등등..

따라서 서버의 위험성이 커진다.
서블릿은 서버가 **의미있는 비즈니스 로직**만을 실행할 수 있도록 위와 같은 비즈니스 로직 의외의 모든 것들을 담당하는 객체이다.

---
### 서블릿
#### 서블릿 객체와 서블릿 내장 객체
<br>

> HTTP 요청시, WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체를 호출한다

<br>

**서블릿 내장 객체**는 서블릿이 처리하는 요청과 응답에 관련된 정보와 기능을 제공하는 객체이다. 
<br>
위 문장에서 Request와 Response가 서블릿 내장 객체이고, 스프링에서 제공하는 서블릿 내장 객체가 `HttpServletRequest`와 
`HttpServletResponse`인 것이다. 개발자는 Request 객체에서 HTTP 요청 데이터를 편리하게 꺼내서 사용할 수 있고, Response객체에
HTTP 응답 정보를 편리하게 입력할 수 있다. 즉, Request 객체는 요청 메세지를 기반으로 만들어지고 response 객체를 기반으로
응답 메세지를 만드는 것이다. ⭐서블릿 내장 객체는 요청마다 독립적인 상태를 유지해야 하므로 요청이 발생할 때마다 새로 생성된다.
<br><br>
**서블릿 객체**는 말 그대로 서블릿 그 자체이다. <br>
보통 싱글톤 패턴으로 구현된다. 따라서 웹 애플리케이션에서 단 하나의 인스턴스만 존재하여 ⭐요청이 들어올 때마다 재사용된다.
<br><br>
**서블릿 컨테이너**는 서블릿을 지원하는 WAS이다. 이 서블릿 컨테이너가 서블릿 내장 객체와 서블릿 객체를 관리한다. 서블릿 객체를 생성, 초기화,
호출, 종료하는 생명주기를 관리한다. 

![IMG_0285](https://github.com/jyunimyon/SpringStudy/assets/101866554/df3564b2-f4e3-4fbe-b16c-774d0bf16b71)

---
### 프로젝트 생성

[start.io](https://start.spring.io) 에서 스프링 프로젝트를 생성하여 다운 받는다. (_서블릿은 스프링과 연관이 없지만 내장 톰캣 등 여러 편의 기능 때문에 스프링 환경에서 서블릿을 실행하는 것이다_)
<br>
>✅ 가장 최신에 나온 스프링은 버전이 3 이상이다. 하지만 스프링 3 이상부터는 자바 17이 필수이다. 하지만 나는 타 프로젝트 때문에 자바 11을 써야해서 최근에 17 ➡️ 11로 변경을 하였기 때문에 그냥 버전 2.7.11 사용

- Gradle-Groovy
- java
- 2.7.11 version
- packaging : war
- java version : 11
- dependencies : Spring Web, Lombok
<br>
파일 생성 후, 

- `Build.gradle` 파일 open
- settings ➡️ Build, Execution, Deployment ➡️ Build Tools ➡️ Gradle <br>
  Build and run using & Run tests using : Gradle ➡️ IntelliJ IDEA
  > 나같은 경우엔 무료 인텔리제이를 사용해서 위와 같이 바꿀 경우 War 파일에서 톰캣이 정상작동하지 않는다. 난 실행 속도가 느리더라도 좀 참아야 한다.

---
### Hello 서블릿
**ServletApplication.java**
```java
@ServletComponentScan // 서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}
}
```
- `@ServletComponentScan` : 사용자가 서블릿을 직접 등록해서 사용할 수 있도록 스프링이 지원하는 어노테이션이다. <u>하위 패키지들에서 서블릿 컨테이너를 모두 찾아(`@WebServlet`) 서블릿으로 등록</u>한다.
<br><br>

**HelloServlet.java**
```java
@WebServlet(name="helloServlet",urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override // 서블릿이 실행이 되면 이 메소드가 호출이 됨
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ...
    System.out.println("req = " + req);
    System.out.println("resp = " + resp);
    ...
    resp.gerWriter().write("hello"+username);
  }
}
```
- `@WebServlet` : 사용자가 url을 실행하였을 때, 해당 url에 매칭되는 자바파일이 실행되어야 한다. 즉, `@WebServlet`은 <U> 어노테이션을 통한 url 매핑 역할을 한다 </U>. 또한 스프링에게 이 클래스가 서블릿임을 알려준다. `name` 은 서블릿의 이름을, `urlPatterns`는 매칭되는 url의 정보를 담는다.  
- `service()` : HTTP 요청을 통해 매핑된 url이 호출되면 서블릿 컨테이너가 실행하는 메소드이다. 이 곳에서 request 객체가 담고 있는 정보를 통해 response 객체에 정보를 넣는다.
<br>

>  HTTP 요청 보내기: `ServletApplication`을 실행하여 스프링이 올라오면, 로컬 웹브라우저에서 [localhost:8080+http 요청 쿼리](http://localhost:8080/)으로 실행한다. **또는 postman 사용 😊**

> `System.out.println` ➡️ 콘솔에 출력 <br>
> `response.getWriter().write()` ➡️ 응답 페이지에 출력

⭐ 스프링이 서블릿을 실행하는 원리는 다음과 같다.
![IMG_0286](https://github.com/jyunimyon/SpringStudy/assets/101866554/a592b178-3d54-4c4e-a7b7-ba33f0cdab4a)

**Welcome page 추가**

welcome page는 특정 url을 보내지 않고 [localhost:8080](http://localhost:8080/)만 실행했을 때 볼 수 있는 페이지이다.<br>
`webapp` 디렉토리에 `index.html` 파일을 만들어 사용한다.<br>

> webapp 디렉토리는 정적 파일(HTML, CSS, JavaScript, 이미지 등)을 저장 및 제공하는 공간이다. 정적 파일을 제공해달라는 요청이 들어오면 해당 파일을 제공한다. 그럼 디렉토리 내의 정적 파일들 중에서 index.html 파일이 기본으로 실행되는 이유는 무엇일까? 그 이유는 대부분의 웹 서버는 기본적으로 index.html 파일을 찾아서 welcome page로 실행시키기 때문이다. 

---
### HttpServletRequest -개요

**`HttpServletRequest`** 는 HTTP 요청 메세지를 편리하게 사용할 수 있도록 메세지 파싱 정보를 담아둔 객체이다.

> HTTP 요청 메세지는 START LINE, 헤더, 바디로 이루어진다 

#### ✅ 기본 기능

1. start line 정보
2. 헤더 정보
3. 헤더 조회
4. 기타 정보
<br>

**RequestHeaderServlet.java**
```java
@WebServlet(name="requestHeaderServlet",urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {
    //header 정보를 어떻게 출력하는지 알아보자
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
    }
...
}
```
> 엄청 중요한 내용은 없고 조금 중요하다 싶으면 [코드](https://github.com/jyunimyon/SpringStudy/blob/main/servlet/src/main/java/hello/servlet/basic/request/RequestHeaderServlet.java)에 주석으로 달아두었다. 
<br>

#### ✅ HTTP 요청 데이터 - 개요
HTTP 요청 메세지로 클라이언트가 서버에 데이터를 전달하는 방법은 주로 다음 3가지를 이용한다.

1. GET 메소드, **쿼리 파라미터**<br>ex:`/url?username=jyuny&age=22`<br>
2. POST 메소드, **HTML FORM**<br>메세지 바디에 <U>쿼리 파라미터 형식</U>으로 전달 <br>ex: `content-type: application/x-www-form-urlencoded`<br>
3. POST 메소드(주로), **HTTP message body**<br> 데이터 형식은 주로 JSON 사용<br>

#### 1️⃣ HTTP 요청 데이터 - GET/ 쿼리 파라미터
GET 메소드로 데이터를 전달하는 경우, 메세지 바디 없이 url의 쿼리 파라미터에 데이터를 담아 보낸다.

> 쿼리 파라미터는 url에 ?로 시작하여 &로 데이터를 추가하고 보낼 수 있다. ex: `localhost:8080/request-param?username=jyuny&age=22`

```java
@WebServlet(name="requestParamServlet",urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회]"); 
        request.getParameterNames().asIterator().forEachRemaining(paramName-> System.out.println(paramName+ "= " + request.getParameter(paramName)));

        System.out.println("[단일 파라미터 조회]-start");
        String username = request.getParameter("username"); 
	String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
	
        System.out.println("이름이 같은 복수 파라미터 조회-start");
        String[] usernames= request.getParameterValues("username");
        for (String name:usernames){
            System.out.println("username = " + name);
        }
    }
}
```
- **`request.getParameter()`** 메소드로 파라미터의 value를 꺼내올 수 있다.
- **`request.getParameterNames()`** 메소드로 파라미터의 이름을 모두 조회할 수 있다.
- **`request.getParameterValues()`** 메소드로 복수 파라미터를 조회할 수 있다

> `username=jyuny&username=kangin`과 같이 하나의 파라미터 이름에 값이 복수라면 `getParameterValues()`를 사용해야 한다. 하지만 실무에서 이럴만한 상황은 거의 없다 😊

