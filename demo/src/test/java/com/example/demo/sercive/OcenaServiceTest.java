package com.example.demo.sercive;

import com.example.demo.model.Ocena;
import com.example.demo.repository.OcenaRepository;
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

public class OcenaServiceTest {

    @Mock
    private OcenaRepository ocenaRepository;

    @InjectMocks
    private OcenaService ocenaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOcena() {
        List<Ocena> oceny = new ArrayList<>();
        oceny.add(new Ocena(1L, 5, null, null, null));
        oceny.add(new Ocena(2L, 4, null, null, null));

        when(ocenaRepository.findAll()).thenReturn(oceny);
        List<Ocena> result = ocenaService.getAllOcena();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetOcena_ExistingId_ShouldReturnOcena() {
        long ocenaId = 1L;
        Ocena ocena = new Ocena(ocenaId, 5, null, null, null);

        when(ocenaRepository.findById(ocenaId)).thenReturn(Optional.of(ocena));
        Ocena result = ocenaService.getOcena(ocenaId);

        assertNotNull(result);
        assertEquals(ocenaId, result.getID());
    }

    @Test
    public void testGetOcena_NonExistingId_ShouldThrowEntityNotFoundException() {
        long ocenaId = 1L;

        when(ocenaRepository.findById(ocenaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ocenaService.getOcena(ocenaId));
    }


    @Test
    public void testEditOcena_ExistingId_ShouldSaveAndReturnTrue() {
        long ocenaId = 1L;
        Ocena existingOcena = new Ocena(ocenaId, 5, null, null, null);
        Ocena updatedOcena = new Ocena(ocenaId, 4, null, null, null);

        when(ocenaRepository.findById(ocenaId)).thenReturn(Optional.of(existingOcena));
        when(ocenaRepository.save(any(Ocena.class))).thenReturn(updatedOcena);
        boolean result = ocenaService.editOcena(updatedOcena, ocenaId);

        assertTrue(result);
    }

    @Test
    public void testEditOcena_NonExistingId_ShouldThrowEntityNotFoundException() {
        long ocenaId = 1L;
        Ocena updatedOcena = new Ocena(ocenaId, 4, null, null, null);

        when(ocenaRepository.findById(ocenaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ocenaService.editOcena(updatedOcena, ocenaId));
    }

    @Test
    public void testDeleteOcena_ExistingId_ShouldDeleteAndReturnTrue() {
        long ocenaId = 1L;

        when(ocenaRepository.findById(ocenaId)).thenReturn(Optional.of(new Ocena()));
        boolean result = ocenaService.deleteOcena(ocenaId);

        assertTrue(result);
        verify(ocenaRepository, times(1)).deleteById(ocenaId);
    }

    @Test
    public void testDeleteOcena_NonExistingId_ShouldReturnFalse() {
        long ocenaId = 1L;

        when(ocenaRepository.findById(ocenaId)).thenReturn(Optional.empty());
        boolean result = ocenaService.deleteOcena(ocenaId);

        assertFalse(result);
        verify(ocenaRepository, never()).deleteById(ocenaId);
    }
}
