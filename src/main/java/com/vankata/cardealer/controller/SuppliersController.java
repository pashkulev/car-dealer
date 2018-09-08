package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.supplier.SupplierDto;
import com.vankata.cardealer.domain.dto.supplier.SupplierDtoWithPartsCount;
import com.vankata.cardealer.service.SupplierService;
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
@RequestMapping("/suppliers")
public class SuppliersController {

    private final SupplierService supplierService;

    @Autowired
    public SuppliersController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/all")
    public ModelAndView viewAll(ModelAndView mav) {
        List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findAll();
        mav.addObject("suppliers", supplierDtos);
        mav.addObject("heading", "All Suppliers");
        mav.setViewName("suppliers");

        return mav;
    }

    @GetMapping("/local")
    public ModelAndView local(ModelAndView mav) {
        List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findLocalSuppliers();
        mav.addObject("suppliers", supplierDtos);
        mav.addObject("heading", "Local Suppliers");
        mav.setViewName("suppliers");
        return mav;
    }

    @GetMapping("/importers")
    public ModelAndView importers(ModelAndView mav) {
        List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findImporters();
        mav.addObject("suppliers", supplierDtos);
        mav.addObject("heading", "Importers");
        mav.setViewName("suppliers");
        return mav;
    }

    @GetMapping("/add")
    public String viewAdd(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        SupplierDto supplierDto = new SupplierDto();
        model.addAttribute("supplier", supplierDto);
        model.addAttribute("heading", "Add Supplier");
        return "supplier-form";
    }

    @PostMapping("/add")
    public String addSupplier(@Valid @ModelAttribute(name = "supplier") SupplierDto supplierDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("heading", "Add Supplier");
            return "supplier-form";
        }

        this.supplierService.addSupplier(supplierDto);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Supplier '%s' was successfully registered", supplierDto.getName()));
        return "redirect:/suppliers/all";
    }

    @GetMapping("/{id}/edit")
    public String viewEdit(@PathVariable(name = "id") long id, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        SupplierDto supplierDto = this.supplierService.findById(id);
        model.addAttribute("supplier", supplierDto);
        model.addAttribute("heading", "Edit Supplier");

        return "supplier-form";
    }

    @PostMapping("/{id}/edit")
    public String editSupplier(@Valid @ModelAttribute(name = "supplier") SupplierDto supplierDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("heading", "Edit Supplier");
            return "supplier-form";
        }

        this.supplierService.editSupplier(supplierDto);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Supplier '%s' was successfully edited!", supplierDto.getName()));
        return "redirect:/suppliers/all";
    }

    @PostMapping("/{id}/delete")
    public String deleteSupplier(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        SupplierDto supplierDto = this.supplierService.findById(id);
        this.supplierService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Supplier with name '%s' was successfully deleted!", supplierDto.getName()));
        return "redirect:/suppliers/all";
    }
}
