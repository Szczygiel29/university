package com.example.demo.controller;

import com.example.demo.model.Wykladowca;
import com.example.demo.sercive.WykladowcaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wykladowca")
public class WykladowcaController {
    private final WykladowcaService wykladowcaService;

    public WykladowcaController(WykladowcaService wykladowcaService) {
        this.wykladowcaService = wykladowcaService;
    }

    @GetMapping
    public List<Wykladowca> getAllWykladowca(){
        return wykladowcaService.getAllWykladowca();
    }

    @GetMapping("/{id}")
    public Wykladowca getWykladowacByID(@PathVariable long id){
        return wykladowcaService.getWykladowca(id);
    }

    @PostMapping
    public boolean addWykladowca(@RequestBody Wykladowca wykladowca){
        return wykladowcaService.addWykladowca(wykladowca);
    }

    @PatchMapping("/update/{id}")
    public boolean updateWykladowca(@RequestBody Wykladowca wykladowca, @PathVariable long id){
        return wykladowcaService.editWykladowca(wykladowca, id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteWykladowca(@PathVariable long id){
        return wykladowcaService.deleteWykladowca(id);
    }
}
