package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.car.CarDto;
import com.vankata.cardealer.domain.dto.customer.CustomerDto;
import com.vankata.cardealer.domain.dto.sale.SaleCreateDto;
import com.vankata.cardealer.domain.dto.sale.SaleDetailDto;
import com.vankata.cardealer.domain.dto.sale.SaleDto;
import com.vankata.cardealer.domain.dto.sale.SaleDtoWithPercent;
import com.vankata.cardealer.service.CarService;
import com.vankata.cardealer.service.CustomerService;
import com.vankata.cardealer.service.SalesService;
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
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    private final CustomerService customerService;

    private final CarService carService;

    @Autowired
    public SalesController(SalesService salesService, CustomerService customerService, CarService carService) {
        this.salesService = salesService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @GetMapping("/all")
    public ModelAndView allSales(ModelAndView mav) {
        List<SaleDto> saleDtos = this.salesService.findAllOrderedByDate();
        mav.addObject("sales", saleDtos);
        mav.addObject("isPercentPresent", false);
        mav.addObject("heading", "All Sales");
        mav.setViewName("sales");
        return mav;
    }

    @GetMapping("/add")
    public String viewAddSale(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        SaleCreateDto saleCreateDto = new SaleCreateDto();
        List<CustomerDto> customerDtos = this.customerService.findAllOrderedByName();
        List<CarDto> carDtos = this.carService.findAllOrderedByBrandAndModel();

        model.addAttribute("sale", saleCreateDto);
        model.addAttribute("customers", customerDtos);
        model.addAttribute("cars", carDtos);

        return "sale-form";
    }

    @PostMapping("/add")
    public String addSaleForReview(@Valid @ModelAttribute(name = "sale") SaleCreateDto saleCreateDto,
                                   BindingResult bindingResult,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            List<CustomerDto> customerDtos = this.customerService.findAllOrderedByName();
            List<CarDto> carDtos = this.carService.findAllOrderedByBrandAndModel();

            model.addAttribute("customers", customerDtos);
            model.addAttribute("cars", carDtos);
            return "sale-form";
        }

        SaleDto saleDto = this.salesService.prepareSale(saleCreateDto, session);
        redirectAttributes.addFlashAttribute("sale", saleDto);
        return "redirect:/sales/add/review";
    }

    @GetMapping("/add/review")
    public String reviewSale(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        return "sale-review";
    }

    @PostMapping("/add/review")
    public String addSale(HttpSession session, RedirectAttributes redirectAttributes) {
        this.salesService.addSale(session);
        redirectAttributes.addFlashAttribute("successMessage", "Sale registered successfully!");
        return "redirect:/sales/all";
    }

    @GetMapping("/{id}")
    public ModelAndView saleById(@PathVariable("id") long id, ModelAndView mav) {
        SaleDetailDto saleDetailDto = this.salesService.findById(id);
        mav.addObject("sale", saleDetailDto);
        mav.setViewName("sales-details");
        return mav;
    }

    @GetMapping("/discounted")
    public ModelAndView discountedSales(ModelAndView mav) {
        List<SaleDto> saleDtos = this.salesService.findDiscountedSales();
        mav.addObject("sales", saleDtos);
        mav.addObject("isPercentPresent", false);
        mav.addObject("heading", "Discounted Sales");
        mav.setViewName("sales");
        return mav;
    }

    @GetMapping("/discounted/{percent}")
    public ModelAndView discountedSalesByDiscount(@PathVariable("percent") int percent, ModelAndView mav) {
        List<SaleDtoWithPercent> saleDtos = this.salesService.findDiscountedSalesWithDiscountPercent(percent);
        mav.addObject("sales", saleDtos);
        mav.addObject("isPercentPresent", true);
        mav.addObject("heading", "Discounted Sales By Given Percent");
        mav.setViewName("sales");
        return mav;
    }
}
