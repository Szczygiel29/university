package com.example.demo.controller;

import com.example.demo.model.Przedmiot;
import com.example.demo.sercive.PrzedmiotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/przedmiot")
public class PrzedmiotController {
    private final PrzedmiotService przedmiotService;

    public PrzedmiotController(PrzedmiotService przedmiotService) {
        this.przedmiotService = przedmiotService;
    }

    @Operation(summary = "Get all przedmioty", description = "Retrieve a list of all przedmioty from the database.")
    @GetMapping
    public List<Przedmiot> getAllPrzedmiot() {
        return przedmiotService.getAllPrzedmiot();
    }

    @Operation(summary = "Get przedmiot by ID", description = "Retrieve a przedmiot from the database by its ID.")
    @GetMapping("/{id}")
    public Przedmiot getPrzedmiotByID(
            @Parameter(description = "ID of the przedmiot to be retrieved.", required = true)
            @PathVariable long id) {
        return przedmiotService.getPrzedmiot(id);
    }

    @Operation(summary = "Add a new przedmiot", description = "Add a new przedmiot to the database.")
    @PostMapping
    public boolean addPrzedmiot(@RequestBody Przedmiot przedmiot) {
        return przedmiotService.addPrzedmiot(przedmiot);
    }

    @Operation(summary = "Update przedmiot by ID", description = "Update an existing przedmiot in the database.")
    @PatchMapping("/update/{id}")
    public boolean updatePrzedmiot(
            @RequestBody Przedmiot przedmiot,
            @Parameter(description = "ID of the przedmiot to be updated.", required = true)
            @PathVariable long id) {
        return przedmiotService.editPrzedmiot(przedmiot, id);
    }

    @Operation(summary = "Delete przedmiot by ID", description = "Delete a przedmiot from the database by its ID.")
    @DeleteMapping("/delete/{id}")
    public boolean deletePrzedmiot(
            @Parameter(description = "ID of the przedmiot to be deleted.", required = true)
            @PathVariable long id) {
        return przedmiotService.deletePrzedmiot(id);
    }
}

