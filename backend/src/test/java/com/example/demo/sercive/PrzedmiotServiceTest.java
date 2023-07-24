package com.example.demo.sercive;

import com.example.demo.model.Przedmiot;
import com.example.demo.repository.PrzedmiotRepository;
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

public class PrzedmiotServiceTest {

    @Mock
    private PrzedmiotRepository przedmiotRepository;

    @InjectMocks
    private PrzedmiotService przedmiotService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPrzedmiot() {
        List<Przedmiot> przedmioty = new ArrayList<>();
        przedmioty.add(new Przedmiot(1L, "Matematyka", null));
        przedmioty.add(new Przedmiot(2L, "Fizyka", null));

        when(przedmiotRepository.findAll()).thenReturn(przedmioty);
        List<Przedmiot> result = przedmiotService.getAllPrzedmiot();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetPrzedmiot_ExistingId_ShouldReturnPrzedmiot() {
        long przedmiotId = 1L;
        Przedmiot przedmiot = new Przedmiot(przedmiotId, "Matematyka", null);

        when(przedmiotRepository.findById(przedmiotId)).thenReturn(Optional.of(przedmiot));
        Przedmiot result = przedmiotService.getPrzedmiot(przedmiotId);

        assertNotNull(result);
        assertEquals(przedmiotId, result.getID());
    }

    @Test
    public void testGetPrzedmiot_NonExistingId_ShouldThrowEntityNotFoundException() {
        long przedmiotId = 1L;

        when(przedmiotRepository.findById(przedmiotId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> przedmiotService.getPrzedmiot(przedmiotId));
    }


    @Test
    public void testEditPrzedmiot_ExistingId_ShouldSaveAndReturnTrue() {
        long przedmiotId = 1L;
        Przedmiot existingPrzedmiot = new Przedmiot(przedmiotId, "Matematyka", null);
        Przedmiot updatedPrzedmiot = new Przedmiot(przedmiotId, "Fizyka", null);

        when(przedmiotRepository.findById(przedmiotId)).thenReturn(Optional.of(existingPrzedmiot));
        when(przedmiotRepository.save(any(Przedmiot.class))).thenReturn(updatedPrzedmiot);
        boolean result = przedmiotService.editPrzedmiot(updatedPrzedmiot, przedmiotId);

        assertTrue(result);
    }

    @Test
    public void testEditPrzedmiot_NonExistingId_ShouldThrowEntityNotFoundException() {
        long przedmiotId = 1L;
        Przedmiot updatedPrzedmiot = new Przedmiot(przedmiotId, "Fizyka", null);

        when(przedmiotRepository.findById(przedmiotId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> przedmiotService.editPrzedmiot(updatedPrzedmiot, przedmiotId));
    }

    @Test
    public void testDeletePrzedmiot_ExistingId_ShouldDeleteAndReturnTrue() {
        long przedmiotId = 1L;

        when(przedmiotRepository.findById(przedmiotId)).thenReturn(Optional.of(new Przedmiot()));
        boolean result = przedmiotService.deletePrzedmiot(przedmiotId);

        assertTrue(result);
        verify(przedmiotRepository, times(1)).deleteById(przedmiotId);
    }

    @Test
    public void testDeletePrzedmiot_NonExistingId_ShouldReturnFalse() {
        long przedmiotId = 1L;

        when(przedmiotRepository.findById(przedmiotId)).thenReturn(Optional.empty());
        boolean result = przedmiotService.deletePrzedmiot(przedmiotId);

        assertFalse(result);
        verify(przedmiotRepository, never()).deleteById(przedmiotId);
    }
}
