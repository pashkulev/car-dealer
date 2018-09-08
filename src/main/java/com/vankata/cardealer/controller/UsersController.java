package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.user.UserCreateDto;
import com.vankata.cardealer.domain.dto.user.UserDto;
import com.vankata.cardealer.domain.dto.user.UserLoginDto;
import com.vankata.cardealer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String viewLogin(HttpServletRequest request, RedirectAttributes redirectAttributes,  Model model) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId != null) {
            UserDto userDto = this.userService.findByUserId(userId);
            redirectAttributes.addFlashAttribute("loginSuccessMessage",
                    String.format("Hello %s:)) You have successfully logged in!", userDto.getUsername()));
            return "redirect:/";
        }

        UserLoginDto userLoginDto = new UserLoginDto();
        model.addAttribute("user", userLoginDto);
        String refererUrl = request.getHeader("referer");

        if (refererUrl != null) {
            String refererUri = refererUrl.substring(refererUrl.lastIndexOf(":8080") + 5);
            model.addAttribute("referer", refererUri);
        }

        return "user-login-form";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute(name = "user") UserLoginDto userLoginDto,
                        BindingResult bindingResult,
                        @RequestParam(name = "referer", defaultValue = "/") String refererUri,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "user-login-form";
        }

        UserDto foundUser = this.userService.findByUsername(userLoginDto.getUsername());
        if (foundUser == null || !foundUser.getUsername().equals(userLoginDto.getUsername())) {
            model.addAttribute("userExistsErrorMessage",
                    String.format("User '%s' does not exist!", userLoginDto.getUsername()));
            return "user-login-form";
        }

        if (!foundUser.getPassword().equals(userLoginDto.getPassword())) {
            model.addAttribute("incorrectPasswordErrorMessage",
                    String.format("Incorrect password for user '%s'!", userLoginDto.getUsername()));
            return "user-login-form";
        }

        session.setAttribute("userId", foundUser.getId());
        redirectAttributes.addFlashAttribute("loginSuccessMessage",
                String.format("Hello %s:)) You have successfully logged in!", userLoginDto.getUsername()));

        return "redirect:" + refererUri;
    }

    @GetMapping("/register")
    public ModelAndView viewRegisterPage(ModelAndView mav) {
        UserCreateDto useCreateDto = new UserCreateDto();
        mav.addObject("user", useCreateDto);
        mav.setViewName("user-register-form");
        return mav;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute(name = "user") UserCreateDto userCreateDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return "user-register-form";
        }

        UserDto foundUser = this.userService.findByUsername(userCreateDto.getUsername());
        if (foundUser != null) {
            model.addAttribute("errorMessage",
                    String.format("User with username '%s' already exists", userCreateDto.getUsername()));
            return "user-register-form";
        }

        UserDto userDto = this.userService.register(userCreateDto);
        redirectAttributes.addFlashAttribute("registerSuccessMessage",
                String.format("Hello %s:)) You have successfully registered!", userCreateDto.getUsername()));
        session.setAttribute("userId", userDto.getId());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
