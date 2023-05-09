package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
1. 파라미터 전송 기능
http://localhost:8080/request-param?username=hello&age=20

*/
@WebServlet(name="requestParamServlet",urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회]-start"); 
        //요즘 방식으로 전체 파라미터 조회하는 방법 --> 실제 실무에서 많이 쓰지는 아늠
        request.getParameterNames().asIterator().forEachRemaining(paramName-> System.out.println(paramName+ "= " + request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회]-end");

        System.out.println("[단일 파라미터 조회]-start");
        String username = request.getParameter("username"); //하여튼 value값 알아내는 것은 결국
        String age = request.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println("[단일 파라미터 조회]-end");

        System.out.println("이름이 같은 복수 파라미터 조회-start");
        String[] usernames= request.getParameterValues("username");
        for (String name:usernames){
            System.out.println("username = " + name);
        }
        System.out.println("이름이 같은 복수 파라미터 조회-start");

        response.getWriter().write("ok");
    }
}
