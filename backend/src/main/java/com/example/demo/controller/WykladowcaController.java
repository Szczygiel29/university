package com.example.demo.controller;

import com.example.demo.model.Wykladowca;
import com.example.demo.sercive.WykladowcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing Wykladowca (lecturer) entities.
 * This class defines endpoints for retrieving, adding, updating, and deleting wykladowcy.
 */
@RestController
@RequestMapping("/wykladowca")
public class WykladowcaController {
    private final WykladowcaService wykladowcaService;
    /**
     * Constructor for WykladowcaController.
     *
     * @param wykladowcaService The service responsible for handling Wykladowca-related operations.
     */

    public WykladowcaController(WykladowcaService wykladowcaService) {
        this.wykladowcaService = wykladowcaService;
    }


    /**
     * Endpoint for retrieving a list of all wykladowcy from the database.
     *
     * @return List of Wykladowca objects representing all wykladowcy in the database.
     */
    @Operation(summary = "Get all wykladowcy", description = "Retrieve a list of all wykladowcy from the database.")
    @GetMapping
    public List<Wykladowca> getAllWykladowca() {
        return wykladowcaService.getAllWykladowca();
    }

    /**
     * Endpoint for retrieving a wykladowca from the database by their ID.
     *
     * @param id The ID of the wykladowca to be retrieved.
     * @return Wykladowca object representing the wykladowca with the specified ID.
     */
    @Operation(summary = "Get wykladowca by ID", description = "Retrieve a wykladowca from the database by their ID.")
    @GetMapping("/{id}")
    public Wykladowca getWykladowcaByID(
            @Parameter(description = "ID of the wykladowca to be retrieved.", required = true)
            @PathVariable long id) {
        return wykladowcaService.getWykladowca(id);
    }

    /**
     * Endpoint for adding a new wykladowca to the database.
     *
     * @param wykladowca The Wykladowca object to be added.
     * @return true if the wykladowca was added successfully, false otherwise.
     */
    @Operation(summary = "Add a new wykladowca", description = "Add a new wykladowca to the database.")
    @PostMapping
    public boolean addWykladowca(@RequestBody Wykladowca wykladowca) {
        return wykladowcaService.addWykladowca(wykladowca);
    }

    /**
     * Endpoint for updating an existing wykladowca in the database.
     *
     * @param wykladowca The updated Wykladowca object.
     * @param id         The ID of the wykladowca to be updated.
     * @return true if the wykladowca was updated successfully, false otherwise.
     */
    @Operation(summary = "Update wykladowca by ID", description = "Update an existing wykladowca in the database.")
    @PatchMapping("/update/{id}")
    public boolean updateWykladowca(
            @RequestBody Wykladowca wykladowca,
            @Parameter(description = "ID of the wykladowca to be updated.", required = true)
            @PathVariable long id) {
        return wykladowcaService.editWykladowca(wykladowca, id);
    }

    /**
     * Endpoint for deleting a wykladowca from the database by their ID.
     *
     * @param id The ID of the wykladowca to be deleted.
     * @return true if the wykladowca was deleted successfully, false otherwise.
     */
    @Operation(summary = "Delete wykladowca by ID", description = "Delete a wykladowca from the database by their ID.")
    @DeleteMapping("/delete/{id}")
    public boolean deleteWykladowca(
            @Parameter(description = "ID of the wykladowca to be deleted.", required = true)
            @PathVariable long id) {
        return wykladowcaService.deleteWykladowca(id);
    }
}

