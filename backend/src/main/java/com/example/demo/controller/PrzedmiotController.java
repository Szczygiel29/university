package com.example.demo.controller;

import com.example.demo.model.Przedmiot;
import com.example.demo.sercive.PrzedmiotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/przemdiot")
public class PrzedmiotController {
    private final PrzedmiotService przedmiotService;

    public PrzedmiotController(PrzedmiotService przedmiotService) {
        this.przedmiotService = przedmiotService;
    }

    @GetMapping
    public List<Przedmiot> getAllPrzedmiot(){
        return przedmiotService.getAllPrzedmiot();
    }

    @GetMapping("/{id}")
    public Przedmiot getPrzedmiotByID(@PathVariable long id){
        return przedmiotService.getPrzedmiot(id);
    }

    @PostMapping
    public boolean addPrzedmiot(@RequestBody Przedmiot przedmiot){
        return przedmiotService.addPrzedmiot(przedmiot);
    }

    @PatchMapping("/update/{id}")
    public boolean updatePrzedmiot(@RequestBody Przedmiot przedmiot, @PathVariable long id){
        return przedmiotService.editPrzedmiot(przedmiot,id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deletePrzedmiot(@PathVariable long id){
        return przedmiotService.deletePrzedmiot(id);
    }
}
