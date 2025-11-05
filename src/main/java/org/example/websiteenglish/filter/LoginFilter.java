package org.example.websiteenglish.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("userId") != null;

        boolean publicPage =
                uri.equals("/") ||
                        uri.endsWith("/login") ||
                        uri.endsWith("login.jsp") ||
                        uri.endsWith("/register") ||
                        uri.endsWith("register.jsp") ||
                        uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot|ico)$");

        // ✅ Если пользователь не авторизован и это не public — редиректим на login
        if (!loggedIn && !publicPage) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ✅ Если он пришёл на "/" и он не авторизован — редирект на login
        if (!loggedIn && uri.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
