package controllers.filters;

import controllers.Utils.ServletsUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter",urlPatterns = {"/users","/messages","/addMessage","/deleteTweet","/follow","/unfollow"})

public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String userLoginFromSession = ServletsUtils.getUserLoginFromSession((HttpServletRequest) request);
        if(userLoginFromSession == null){
            request.getRequestDispatcher("/login").forward(request,response);
            return;
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
