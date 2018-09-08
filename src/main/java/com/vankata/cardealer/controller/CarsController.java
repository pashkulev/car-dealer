package com.vankata.cardealer.controller;

import com.vankata.cardealer.domain.dto.car.CarDto;
import com.vankata.cardealer.domain.dto.car.CarDtoWithParts;
import com.vankata.cardealer.domain.dto.part.FormPartDto;
import com.vankata.cardealer.domain.dto.supplier.SupplierDto;
import com.vankata.cardealer.service.CarService;
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
@RequestMapping("/cars")
public class CarsController {

    private final CarService carService;
    private final PartService partService;
    private final SupplierService supplierService;

    @Autowired
    public CarsController(CarService carService, PartService partService, SupplierService supplierService) {
        this.carService = carService;
        this.partService = partService;
        this.supplierService = supplierService;
    }

    @GetMapping("/all")
    public ModelAndView allCars(ModelAndView mav) {
        List<CarDto> carDtos = this.carService.findAll();
        List<String> carBrands = this.carService.getCarBrands();

        mav.addObject("cars", carDtos);
        mav.addObject("carBrands", carBrands);
        mav.addObject("heading", "All Cars");
        mav.setViewName("cars");
        return mav;
    }

    @GetMapping("/{make}")
    @ResponseBody
    public List<CarDto> carsByMake(@PathVariable("make") String make) {
        return this.carService.findByMake(make);
    }

    @GetMapping("/{id}/parts")
    public ModelAndView carsWithParts(@PathVariable("id") long id, ModelAndView mav) {
        CarDtoWithParts carDtoWithParts = this.carService.findByIdWithParts(id);
        mav.addObject("car", carDtoWithParts);
        mav.setViewName("cars-parts");
        return mav;
    }

    @GetMapping("/add")
    public String viewAddCar(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/users/login";
        }

        CarDto carDto = new CarDto();
        List<String> carBrands = this.carService.getCarBrands();
        List<SupplierDto> simpleSupplierDtos = this.supplierService.findAllSimpleSuppliers();

        model.addAttribute("car", carDto);
        model.addAttribute("carBrands", carBrands);
        model.addAttribute("suppliers", simpleSupplierDtos);
        model.addAttribute("heading", "Add Car");

        return "car-form";
    }

    @PostMapping("/add")
    public String addCar(@Valid @ModelAttribute("car") CarDto carDto,
                         BindingResult bindingResult,
                         @RequestParam(value = "parts", required = false) List<String> parts,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            List<String> carBrands = this.carService.getCarBrands();
            String carBrand = carDto.getMake();
            if (!carBrand.isEmpty()) {
                if (carBrands.contains(carBrand)) {
                    model.addAttribute("selectedBrand", carBrand);
                } else {
                    model.addAttribute("newBrand", carBrand);
                }
            }

            List<SupplierDto> simpleSupplierDtos = this.supplierService.findAllSimpleSuppliers();

            model.addAttribute("car", carDto);
            model.addAttribute("carBrands", carBrands);
            model.addAttribute("suppliers", simpleSupplierDtos);
            model.addAttribute("heading", "Add Car");

            if (parts != null) {
                List<FormPartDto> formPartDtos = this.partService.findByFormString(parts);
                model.addAttribute("parts", formPartDtos);
            }

            return "car-form";
        }

        this.carService.addCarWithParts(carDto, parts);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Car '%s - %s' was successfully added!", carDto.getMake(), carDto.getModel()));
        return "redirect:/cars/all";
    }

    @GetMapping("/{brand}/models")
    @ResponseBody
    public List<String> getBrandModels(@PathVariable("brand") String brand) {
        return this.carService.getBrandModels(brand);
    }
}
