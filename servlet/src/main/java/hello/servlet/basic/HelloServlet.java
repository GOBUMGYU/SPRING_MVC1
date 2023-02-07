package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//서블릿은 HttpServlet 을 상속받아야 한다.
//@WebServlet = 서블릿 애노테이션 name = 서블릿 이름 urlPatterns = URL매핑
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    //HTTP 요청을 통해 URL이 호출되면 서블릿 컨테이너는 다음 메서드를 실행한다.


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("request = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain"); //데이터 형식
        response.setCharacterEncoding("utf-8"); //문자 인코딩
        response.getWriter().write("hello " + username);
    }
}
