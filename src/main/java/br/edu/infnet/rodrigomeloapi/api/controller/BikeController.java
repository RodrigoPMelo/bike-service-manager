package br.edu.infnet.rodrigomeloapi.api.controller;

import br.edu.infnet.rodrigomeloapi.application.service.BikeService;
import br.edu.infnet.rodrigomeloapi.domain.model.Bike;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bikes")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;

    @GetMapping
    public List<Bike> listAll() {
        return bikeService.findAll();
    }
}
