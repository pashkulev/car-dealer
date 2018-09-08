package com.vankata.cardealer.interceptor;

import com.vankata.cardealer.domain.dto.user.UserDto;
import com.vankata.cardealer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Autowired
    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId != null) {
            UserDto userDto = this.userService.findByUserId(userId);
            request.setAttribute("username", userDto.getUsername());
            request.setAttribute("isLoggedIn", true);
        }
        return true;
    }
}
