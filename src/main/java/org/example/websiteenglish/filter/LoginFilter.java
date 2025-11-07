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

        boolean isStaticResource = uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot|ico)$");
        boolean isPublicEndpoint =
                uri.equals("/") ||
                uri.endsWith("/login") ||
                uri.endsWith("/register");

        boolean publicPage = isStaticResource || isPublicEndpoint;

        if (!loggedIn && !publicPage) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (!loggedIn && uri.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
