package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.log.LogDto;
import com.vankata.cardealer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogsController {

    private final LogService logService;

    @Autowired
    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/all")
    public String viewLogs(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        List<LogDto> logDtos = this.logService.findAll();
        model.addAttribute("logs", logDtos);
        return "logs";
    }

    @PostMapping("/all/delete")
    public String deleteAllLogs(RedirectAttributes redirectAttributes) {
        this.logService.deleteAll();
        redirectAttributes.addFlashAttribute("successMessage", "All Logs were deleted!");
        return "redirect:/logs/all";
    }

    @GetMapping("/{username}")
    @ResponseBody
    public List<LogDto> getLogsByUsername(@PathVariable(name = "username") String username) {
        List<LogDto> logs = this.logService.findLogsByUsername(username);
        return logs;
    }
}
