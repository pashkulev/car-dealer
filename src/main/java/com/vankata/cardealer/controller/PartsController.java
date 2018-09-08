package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.part.FormPartDto;
import com.vankata.cardealer.domain.dto.part.PartDto;
import com.vankata.cardealer.domain.dto.part.ViewPartDto;
import com.vankata.cardealer.domain.dto.supplier.SupplierDtoWithPartsCount;
import com.vankata.cardealer.service.PartService;
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
@RequestMapping("/parts")
public class PartsController {

    private final PartService partService;

    private final SupplierService supplierService;

    @Autowired
    public PartsController(PartService partService, SupplierService supplierService) {
        this.partService = partService;
        this.supplierService = supplierService;
    }

    @GetMapping("/all")
    public ModelAndView viewAll(ModelAndView mav) {
        List<ViewPartDto> viewPartDtos = this.partService.findAll();
        mav.addObject("parts", viewPartDtos);
        mav.addObject("heading", "All Parts");
        mav.setViewName("parts");
        return mav;
    }

    @GetMapping("/add")
    public String viewAddPart(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findAll();
        FormPartDto formPartDto = new FormPartDto();
        model.addAttribute("suppliers", supplierDtos);
        model.addAttribute("part", formPartDto);
        model.addAttribute("heading", "Add Part");
        return "parts-form";
    }

    @PostMapping("/add")
    public String addPart(@Valid @ModelAttribute("part") FormPartDto formPartDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        if (bindingResult.hasErrors()) {
            List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findAll();
            model.addAttribute("suppliers", supplierDtos);
            model.addAttribute("heading", "Add Part");
            return "parts-form";
        }

        this.partService.addPart(formPartDto);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Part '%s' was successfully added!", formPartDto.getName()));
        return "redirect:/parts/all";
    }

    @GetMapping("/{id}/edit")
    public String viewEditPart(@PathVariable("id") long id, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findAll();
        FormPartDto formPartDto = this.partService.findById(id);
        model.addAttribute("suppliers", supplierDtos);
        model.addAttribute("part", formPartDto);
        model.addAttribute("heading", "Edit Part");

        return "parts-form";
    }

    @PostMapping("/{id}/edit")
    public String editPart(@PathVariable("id") long id,
                           @Valid @ModelAttribute("part") FormPartDto formPartDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (bindingResult.hasErrors()) {
            List<SupplierDtoWithPartsCount> supplierDtos = this.supplierService.findAll();
            model.addAttribute("suppliers", supplierDtos);
            model.addAttribute("heading", "Edit Part");
            return "parts-form";
        }

        this.partService.editPart(formPartDto);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Part '%s' was successfully edited!", formPartDto.getName()));
        return "redirect:/parts/all";
    }

    @PostMapping("/{id}/delete")
    public String deletePart(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        FormPartDto part = this.partService.findById(id);
        this.partService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Part with name '%s' was successfully deleted!", part.getName()));
        return "redirect:/parts/all";
    }

    @GetMapping("/{id}/suppliers")
    @ResponseBody
    public List<PartDto> loadPartsBySupplierId(@PathVariable("id") long supplierId) {
        return this.partService.findBySupplierId(supplierId);
    }
}
