package com.example.demo.controller;

import com.example.demo.model.Ocena;
import com.example.demo.sercive.OcenaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocena")
public class OcenaController {
    private final OcenaService ocenaService;

    public OcenaController(OcenaService ocenaService) {
        this.ocenaService = ocenaService;
    }

    @GetMapping
    public List<Ocena> getAllOcena() {
        return ocenaService.getAllOcena();
    }

    @GetMapping("/{id}")
    public Ocena getOcenaByID(@PathVariable long id){
        return ocenaService.getOcena(id);
    }

    @PostMapping
    public boolean addOcena(@RequestBody Ocena ocena){
        return ocenaService.addOcena(ocena);
    }

    @PatchMapping("/update/{id}")
    public boolean updateOcena(@RequestBody Ocena ocena, @PathVariable long id){
        return ocenaService.editOcena(ocena, id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteOcena(@PathVariable long id){
        return ocenaService.deleteOcena(id);
    }
}
