package com.vankata.cardealer.interceptor;

import com.vankata.cardealer.domain.entity.Log;
import com.vankata.cardealer.domain.entity.User;
import com.vankata.cardealer.domain.enums.Operation;
import com.vankata.cardealer.repository.UserRepository;
import com.vankata.cardealer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    private final LogService logService;

    @Autowired
    public LoggingInterceptor(UserRepository userRepository, LogService logService) {
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getMethod().equalsIgnoreCase("POST")) {
            String requestUri = request.getRequestURI().substring(1);
            String rootUri = requestUri.substring(0, requestUri.indexOf("/", 1));
            String[] uriParams = requestUri.split("/");
            String userId = (String) request.getSession().getAttribute("userId");

            User user = null;
            if (userId != null) {
                user = this.userRepository
                        .findById(userId)
                        .orElse(null);
            }

            switch (rootUri) {
                case "customers":
                    if (uriParams.length == 2) {
                        this.createLog(user, "Customer", Operation.Add);
                    } else if (uriParams.length == 3) {
                        this.createLog(user, "Customer", Operation.Edit);
                    }

                    break;
                case "sales":
                    if (uriParams.length > 2 && uriParams[2].equals("review")) {
                        this.createLog(user, "Sale", Operation.Add);
                    }

                    break;
                case "cars":
                    this.createLog(user, "Car", Operation.Add);
                    break;
                case "parts":
                    if (uriParams.length == 2) {
                        this.createLog(user, "Part", Operation.Add);
                    } else if (uriParams.length == 3) {
                        if (uriParams[2].equals("edit")) {
                            this.createLog(user, "Part", Operation.Edit);
                        } else {
                            this.createLog(user, "Part", Operation.Delete);
                        }
                    }

                    break;
                case "suppliers":
                    if (uriParams.length == 2) {
                        this.createLog(user, "Supplier", Operation.Add);
                    } else if (uriParams.length == 3) {
                        if (uriParams[2].equals("edit")) {
                            this.createLog(user, "Supplier", Operation.Edit);
                        } else {
                            this.createLog(user, "Supplier", Operation.Delete);
                        }
                    }

                    break;
            }
        }
    }

    private void createLog(User user, String table, Operation operation) {
        Log log = new Log();
        log.setModifiedTable(table);
        log.setUser(user);
        log.setOperation(operation);
        log.setTime(LocalDateTime.now());

        this.logService.saveLog(log);
    }
}
