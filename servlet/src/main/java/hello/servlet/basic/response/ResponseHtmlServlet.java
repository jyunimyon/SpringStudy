package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet",urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //첫 번째 할것: Content-type 잡기: text/html, utf-8
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer=response.getWriter();
        // 서블릿으로 html 렌더링하는 방법. 따라서 이 로직을 좀 바꾸면 동적으로도 html 생성 가능(if문 등..)
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>안뇽안뇽 나는 쥬니얌</div>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
