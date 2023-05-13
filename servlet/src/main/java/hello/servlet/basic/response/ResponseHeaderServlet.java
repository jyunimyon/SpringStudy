package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //status line
        response.setStatus(HttpServletResponse.SC_OK);// 상수 200을 직접 적는 것보다 의미를 바로 확인할 수 있도록 정의되어있는 것을 쓰는 것이 좋음 (ok==200)
        //response-header
        response.setHeader("Content-type","text/plain;charset=utf-8"); // charset 설정 안 해주면 tomcat이 자동으로 설정하는데 잘못하면 한글이 꺠질 수가 있음
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");// 캐시 무효화
        response.setHeader("Pragma","no-cache");
        response.setHeader("my-header","hello");// 내가 원하는 임의의 헤더를 만들 수도 있음 , http 응답의 header에 이 정보가 실림
        //header의 편의 메소드
        //content(response);
        //cookie(response);
        redirect(response);
        PrintWriter writer=response.getWriter();
        writer.println("ok");
    }
    private void content(HttpServletResponse response) {
    //Content-Type: text/plain;charset=utf-8
    //Content-Length: 2
    // response.setHeader("Content-Type", "text/plain;charset=utf-8"); 대신에 아래처럼 메소드를 이용할 수 있음
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        /* //첫 번째 방법
        response.setStatus(HttpServletResponse.SC_FOUND); //302
        response.setHeader("Location", "/basic/hello-form.html");
        */
        // 더 축약
        response.sendRedirect("/basic/hello-form.html");
         
    }
}
