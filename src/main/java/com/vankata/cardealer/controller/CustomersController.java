package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.customer.CustomerDto;
import com.vankata.cardealer.domain.dto.customer.CustomerSalesDto;
import com.vankata.cardealer.domain.entity.Customer;
import com.vankata.cardealer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomersController {

    private CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all/ascending")
    public ModelAndView allAscending(ModelAndView mav) {
        List<CustomerDto> customerDtos = this.customerService.findAllAscending();
        mav.addObject("customers", customerDtos);
        mav.addObject("heading", "All Customers in Ascending Order");
        mav.setViewName("customers");
        return mav;
    }

    @GetMapping("/all/descending")
    public ModelAndView allDescending(ModelAndView mav) {
        List<CustomerDto> customerDtos = this.customerService.findAllDescending();
        mav.addObject("customers", customerDtos);
        mav.addObject("heading", "All Customers in Descending Order");
        mav.setViewName("customers");
        return mav;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CustomerSalesDto getCustomerTotalSalesById(@PathVariable("id") long id) {
        return this.customerService.getTotalSalesByCustomerId(id);
    }

    @GetMapping("/add")
    public String viewAddCustomer(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        model.addAttribute("customer", new CustomerDto());
        return "customer-form";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customer-form";
        }

        if (this.customerService.findByName(customerDto.getName()) != null) {
            model.addAttribute("errorMessage", "Customer with name " + customerDto.getName() + " already exist!");
            return "customer-form";
        }

        boolean isAdded = this.customerService.addCustomer(customerDto);
        if (isAdded) {
            redirectAttributes.addFlashAttribute("successMessage",
                    String.format("Customer with name %s was successfully added!", customerDto.getName()));
            return "redirect:/customers/all/ascending";
        }

        return "customer-form";
    }

    @GetMapping("/{id}/edit")
    public String viewEditCustomer(@PathVariable("id") long id, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        CustomerDto customerDto = this.customerService.findById(id);
        model.addAttribute("customer", customerDto);
        return "customer-form";
    }

    @PostMapping("/{id}/edit")
    public String editCustomer(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customer-form";
        }

        Customer foundCustomer = this.customerService.findByName(customerDto.getName());
        if (foundCustomer != null && foundCustomer.getId() != customerDto.getId()) {
            model.addAttribute("errorMessage", "Customer with name " + customerDto.getName() + " already exist!");
            return "customer-form";
        }

        this.customerService.editCustomer(customerDto);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Customer with name %s was successfully edited!", customerDto.getName()));
        return "redirect:/customers/all/ascending";
    }
}