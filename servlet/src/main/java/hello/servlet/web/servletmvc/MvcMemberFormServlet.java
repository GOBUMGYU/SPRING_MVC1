package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        //클라이언트가 보낸 request 객체를 전달한 경로를 getRequestDispatcher()메서드로 지정
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        //forward()메서드를 통해 미리 지정한 경로로 request, response를 전달
        //제어권을 지정한 경로로 넘겨주는 것 !
        dispatcher.forward(request, response);

    }
}
