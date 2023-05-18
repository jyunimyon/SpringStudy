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
  Build and run using & Run tests using : Gradle ➡️ IntelliJ IDEA 으로 변경
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

> 쿼리 파라미터는 url에 `?`로 시작하여 `&`로 데이터를 추가하고 보낼 수 있다. ex: `localhost:8080/request-param?username=jyuny&age=22`

**RequestParamServlet.java**
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
- ⭐ **`request.getParameter()`** 메소드로 파라미터의 value를 꺼내올 수 있다.
- **`request.getParameterNames()`** 메소드로 파라미터의 이름을 모두 조회할 수 있다.
- **`request.getParameterValues()`** 메소드로 복수 파라미터를 조회할 수 있다

> `username=jyuny&username=kangin`과 같이 하나의 파라미터 이름에 값이 복수라면 `getParameterValues()`를 사용해야 한다. 하지만 실무에서 이럴만한 상황은 거의 없다 😊

#### 2️⃣ HTTP 요청 데이터 - POST/ HTML form
메세지 바디에 데이터를 넣어 보낼 때는 데이터의 **content-type**을 지정해줘야 한다. html form 을 통해 전달된 데이터는 메세지 바디에 **쿼리 파라미터 형식**으로 데이터를 전달한다. 이 때 `content-type`은 `application/x-www-form-urlencoded`이다.

**[`hello-form.html`](https://github.com/jyunimyon/SpringStudy/blob/main/servlet/src/main/webapp/basic/hello-form.html)**

> ⭐ 쿼리 파라미터로 전달하는 것은 동일하기 때문에 쿼리 파라미터 조회 메소드를 그대로 사용한다. 즉, `request.getParameter()`은 **GET url 쿼리 파라미터 형식과 POST html form 형식 모두 지원**한다
<br><br>

**동작 방식**
1. localhost:8080/basic/hello-form.html 실행
2. 웹 브라우저에서 HTTP 메세지 생성<br> 요청 url: localhost:8080/request-param, content-type: application/x-www-form-urlencoded
3. message body: `username=jyuny&age=22`

> **postman을 이용한 테스트** 이런 테스트 하나하나에 `hello-form.html`와 같은 html form을 만들지 않고 다양한 타입의 데이터를 전송할 수 이따! 매우 편하다 🥲🥲

postman을 이용한 테스트 결과 ➡️
<img width="278" alt="query" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/627730cf-fce1-4ae0-8b40-814b47b224de">

#### 3️⃣ HTTP 요청 데이터 - POST/ 메세지 바디 - 단순 텍스트

HTTP API에서 주로 사용한다. 첫 번째 예시는 단순 텍스트 메세지를 메세지 바디에 담아 전송하고 읽는 것이다.

**RequestBodyStringServlet.java**
```java
@WebServlet(name="requestBodyStringServlet",urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); 
        String messagebody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println("messagebody = " + messagebody);
        response.getWriter().write("ok");
    }
}
```
- `getInputStream()` 메소드로 메세지 바디의 내용을 바이트 코드로 바로 얻을 수 있다.
- `StreamUtils`는 스프링이 제공하는 유틸리티 클래스이다. <br> 바이트 코드를 우리가 읽을 수 있는 문자로 인코딩하기 위해선 **인코딩 정보**를 알려줘야 한다. 여기선 `chatset=utf-8`로 지정하였다.

postman에서 다음과 같이 데이터를 전송하면
- url: localhost:8080/request-body-string
- content-type: text/plain
- message body: hello

콘솔에서 다음과 같은 결과를 얻을 수 있다. 
➡️ `messagebody=hello`

#### 4️⃣ HTTP 요청 데이터 - POST/ 메세지 바디 - JSON

json은 HTTP API에서 가장 많이 사용하는 데이터 형식이다. 
<br>
ex: `{"username":"jyuny", "age":"22"}`
<br>

> ✅ JSON 결과를 파싱하기 위해선 먼저 JSON 문자열을 **파싱 가능한 자바 객체로 변환**하여야 한다. 이 때 가장 많이 사용하는 JSON 변환 라이브러리는 스프링이 기본 제공하는 **Jackson 라이브러리**이다. 

**HelloData.java**
```java
@Getter 
@Setter// 롬복 이용하여 getter,setter 메소드 작성 안 해도 됨
public class HelloData {
    private String username;
    private int age;
}
```
**RequestBodyJsonServlet.java**
```java
@WebServlet(name="requestBodyJsonServlet",urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {
    private ObjectMapper objectMapper=new ObjectMapper();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println("messageBody = " + messageBody);
        //HelloData 객체로 변환
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); //객체로 짠 하고 변함
        System.out.println("helloData.Username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());
        response.getWriter().write("ok");
        
    }
}
```

postman에서 다음과 같이 데이터를 전송하면
- url: localhost:8080/request-body-json
- content-type: application/json
- message body: `{"username":"jyuny", "age":"22"}`

콘솔에서 다음과 같은 결과를 얻을 수 있다 ➡️
<img width="283" alt="json" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/0b56fde0-0b68-4f9e-b1d4-3c363024c4e3">

---
### HttpServletResponse

`HttpServletResponse` 객체의 역할은 다음과 같다.
- HTTP 응답 코드 지정
- 헤더 생성
- 바디 생성
- Content-type
- 쿠키
- redirect

#### HttpServletResponse 기본 사용법

**ResponseHeaderServlet.java**
```java
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //status line
        response.setStatus(HttpServletResponse.SC_OK);
        //response-header
        response.setHeader("Content-type","text/plain;charset=utf-8");
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");// 캐시 무효화
        response.setHeader("Pragma","no-cache");
        response.setHeader("my-header","hello");
        //header의 편의 메소드
        //content(response);
        //cookie(response);
        //redirect(response);
        PrintWriter writer=response.getWriter();
        writer.println("ok");
    }
    ...
 }
```
1. `response.setStatus()`를 통해 status 코드를 정해줄 수 있다. 숫자(`200`)를 직접 명시할 수도 있지만 `SC_OK` 처럼 의미를 바로 확인할 수 있도록 정의되어 있는 것을 쓰는 것이 좋다.<br><br><img width="485" alt="response_200" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/3b0643e5-a81e-440d-b824-3e2a0a00a725"><img width="462" alt="response_400" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/c6196577-4eeb-4f1a-8f91-4de9512f8d6e">
2. `response.setHeader(name, value)`를 통해 여러가지 response 헤더의 정보를 설정할 수 있다.<br> - charset 설정을 해주지 않으면 내장 톰캣이 자동으로 설정하는데 잘못하면 한글이 깨질 수도 있다<br> - 내가 원하는 임의의 헤더를 만들 수도 있다. 응답 헤더에 내가 만든 헤더의 정보가 실린다. 

```java
private void content(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }
```
- `response.setHeader("Content-Type", "text/plain;charset=utf-8")`대신에 위와 같은 편의 메소드를 이용하여 `content-type`과 `charset`을 설정해줄 수 있다.

```java
private void cookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //유효 시간 600초
        response.addCookie(cookie);
    }
```
- 위와 같은 편의 메소드로 쿠키 또한 편하게 만들 수 있다. 다음은 [localhost:8080/response-header](http://localhost:8080/response-header) 실행 후 `Set-Cookie`속성의 결과와 새로고침 후 `Cookie` 속성의 결과이다.<br><br><img width="352" alt="cookie" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/5a428eed-0667-4be4-be5a-50f4fc661f66"><img width="363" alt="cookie_f5" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/d1e93802-bdca-4b7a-a959-945c4f3630c5">

```java
private void redirect(HttpServletResponse response) throws IOException {
   response.sendRedirect("/basic/hello-form.html");    
    } 
```
- redirect도 쉽게 할 수 있다. [localhost:8080/response-header](http://localhost:8080/response-header)을 실행하면 자동으로 hello-form.html로 이동한다. 다음 결과에서 위 url로 요청했을 경우 302 리디렉션 상태 코드가 뜨고, 다음에 redirect url로 요청을 재전송하는 것을 볼 수 있다. <br><br> <img width="536" alt="질문" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/afede879-89fc-4564-8e12-aa2629230053">

#### 1️⃣ HTTP 응답 데이터 - 단순 텍스트, HTML

결국 응답도 대부분 다음 3가지 중 하나로 보내게 된다.
- 단순 텍스트 응답 `writer.println("ok");` (기본 사용법에서 했던 것)
- HTML 응답
- HTTP API 응답 (json)

**ResponseHtmlServlet.java**
```java
@Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer=response.getWriter();
	//서블릿으로 html 렌더링
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>안뇽안뇽 나는 쥬니얌</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
```
- **응답을 보낼 때 제일 먼저 할 것은 `content-type` 설정**이다.
- 렌더링 하는 부분의 로직을 바꾸면 동적으로도 html 생성이 가능하다(ex: `if`문 사용).

결과는 다음과 같다.<br>
<img width="611" alt="html결과" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/f070a997-1dfd-4f3c-9377-cd8e54b26bd7">

#### 2️⃣ HTTP 응답 데이터 - HTTP API(json)

주로 HTTP API를 만들 때 사용하는 응답 데이터를 json 형식으로 보내는 방법에 대해 알아보자.
<br><br>
**ResponseJsonServlet.java**
```java
private ObjectMapper objectMapper=new ObjectMapper();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData=new HelloData();
        helloData.setUsername("jyuny");
        helloData.setAge(22);

	String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);
    }
```
- ⭐ 요청 데이터로 온 json을 파싱할 때 파싱할 수 있는 자바 객체인 HelloData로 바꾸었다. json으로 응답을 보낼 때는 이에 반대로 HelloData 객체에 정보를 담아 이것을 json 형식으로 변환하여 응답을 보내야 한다. 라이브러리는 동일하다.

결과는 다음과 같다.<br>
<img width="572" alt="json결과" src="https://github.com/jyunimyon/SpringStudy/assets/101866554/6f2cfb98-5050-4e48-8c05-c03d178745c6">
