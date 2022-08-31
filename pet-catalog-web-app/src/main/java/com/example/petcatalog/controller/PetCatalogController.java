package com.example.petcatalog.controller;

import com.example.domain.PetItemDomain;
import com.example.petcatalog.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PetCatalogController {
    @Autowired
    GrpcClientService grpcClientService;

    @GetMapping("/")
    public ModelAndView load() {
        ModelAndView modelAndView = new ModelAndView("pets");
        List<PetItemDomain.Pet> pets = grpcClientService.loadAllPets();
        modelAndView.addObject("pets", pets);
        return modelAndView;
    }

    @PostMapping("/pet")
    public String save(@RequestParam String petName, @RequestParam String petImage) {
        grpcClientService.add(petName, petImage);
        return "redirect:/";
    }
}