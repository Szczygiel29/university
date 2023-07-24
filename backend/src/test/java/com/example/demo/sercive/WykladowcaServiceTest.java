package com.example.demo.sercive;

import com.example.demo.model.Wykladowca;
import com.example.demo.repository.WykladowcaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WykladowcaServiceTest {

    @Mock
    private WykladowcaRepository wykladowcaRepository;

    @InjectMocks
    private WykladowcaService wykladowcaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllWykladowca() {
        List<Wykladowca> wykladowcy = new ArrayList<>();
        wykladowcy.add(new Wykladowca(1L, "Jan", "Kowalski", "Profesor", null));
        wykladowcy.add(new Wykladowca(2L, "Anna", "Nowak", "Doktor", null));

        when(wykladowcaRepository.findAll()).thenReturn(wykladowcy);
        List<Wykladowca> result = wykladowcaService.getAllWykladowca();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetWykladowca_ExistingId_ShouldReturnWykladowca() {
        long wykladowcaId = 1L;
        Wykladowca wykladowca = new Wykladowca(wykladowcaId, "Jan", "Kowalski", "Profesor", null);

        when(wykladowcaRepository.findById(wykladowcaId)).thenReturn(Optional.of(wykladowca));
        Wykladowca result = wykladowcaService.getWykladowca(wykladowcaId);

        assertNotNull(result);
        assertEquals(wykladowcaId, result.getID());
    }

    @Test
    public void testGetWykladowca_NonExistingId_ShouldThrowEntityNotFoundException() {
        long wykladowcaId = 1L;

        when(wykladowcaRepository.findById(wykladowcaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> wykladowcaService.getWykladowca(wykladowcaId));
    }


    @Test
    public void testEditWykladowca_ExistingId_ShouldSaveAndReturnTrue() {
        long wykladowcaId = 1L;
        Wykladowca existingWykladowca = new Wykladowca(wykladowcaId, "Jan", "Kowalski", "Profesor", null);
        Wykladowca updatedWykladowca = new Wykladowca(wykladowcaId, "Anna", "Nowak", "Doktor", null);

        when(wykladowcaRepository.findById(wykladowcaId)).thenReturn(Optional.of(existingWykladowca));
        when(wykladowcaRepository.save(any(Wykladowca.class))).thenReturn(updatedWykladowca);
        boolean result = wykladowcaService.editWykladowca(updatedWykladowca, wykladowcaId);

        assertTrue(result);
    }

    @Test
    public void testEditWykladowca_NonExistingId_ShouldThrowEntityNotFoundException() {
        long wykladowcaId = 1L;
        Wykladowca updatedWykladowca = new Wykladowca(wykladowcaId, "Anna", "Nowak", "Doktor", null);

        when(wykladowcaRepository.findById(wykladowcaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> wykladowcaService.editWykladowca(updatedWykladowca, wykladowcaId));
    }

    @Test
    public void testDeleteWykladowca_ExistingId_ShouldDeleteAndReturnTrue() {
        long wykladowcaId = 1L;

        when(wykladowcaRepository.findById(wykladowcaId)).thenReturn(Optional.of(new Wykladowca()));
        boolean result = wykladowcaService.deleteWykladowca(wykladowcaId);

        assertTrue(result);
        verify(wykladowcaRepository, times(1)).deleteById(wykladowcaId);
    }

    @Test
    public void testDeleteWykladowca_NonExistingId_ShouldReturnFalse() {
        long wykladowcaId = 1L;

        when(wykladowcaRepository.findById(wykladowcaId)).thenReturn(Optional.empty());
        boolean result = wykladowcaService.deleteWykladowca(wykladowcaId);

        assertFalse(result);
        verify(wykladowcaRepository, never()).deleteById(wykladowcaId);
    }
}
