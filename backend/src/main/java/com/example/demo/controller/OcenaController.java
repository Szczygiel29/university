package com.example.demo.controller;

import com.example.demo.model.Ocena;
import com.example.demo.sercive.OcenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocena")
public class OcenaController {
    private final OcenaService ocenaService;

    public OcenaController(OcenaService ocenaService) {
        this.ocenaService = ocenaService;
    }

    @Operation(summary = "Get all oceny", description = "Retrieve a list of all oceny from the database.")
    @GetMapping
    public List<Ocena> getAllOcena() {
        return ocenaService.getAllOcena();
    }

    @Operation(summary = "Get ocena by ID", description = "Retrieve an ocena from the database by its ID.")
    @GetMapping("/{id}")
    public Ocena getOcenaByID(
            @Parameter(description = "ID of the ocena to be retrieved.", required = true)
            @PathVariable long id) {
        return ocenaService.getOcena(id);
    }

    @Operation(summary = "Add a new ocena", description = "Add a new ocena to the database.")
    @PostMapping
    public boolean addOcena(@RequestBody Ocena ocena) {
        return ocenaService.addOcena(ocena);
    }

    @Operation(summary = "Update ocena by ID", description = "Update an existing ocena in the database.")
    @PatchMapping("/update/{id}")
    public boolean updateOcena(
            @RequestBody Ocena ocena,
            @Parameter(description = "ID of the ocena to be updated.", required = true)
            @PathVariable long id) {
        return ocenaService.editOcena(ocena, id);
    }

    @Operation(summary = "Delete ocena by ID", description = "Delete an ocena from the database by its ID.")
    @DeleteMapping("/delete/{id}")
    public boolean deleteOcena(
            @Parameter(description = "ID of the ocena to be deleted.", required = true)
            @PathVariable long id) {
        return ocenaService.deleteOcena(id);
    }
}
