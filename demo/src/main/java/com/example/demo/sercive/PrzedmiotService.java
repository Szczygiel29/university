package com.example.demo.sercive;

import com.example.demo.model.Przedmiot;
import com.example.demo.repository.PrzedmiotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrzedmiotService {
    private final PrzedmiotRepository przedmiotRepository;

    public List<Przedmiot> getAllPrzedmiot() {
        return przedmiotRepository.findAll();
    }

    public Przedmiot getPrzedmiot(long ID) {
        return przedmiotRepository.findById(ID).orElseThrow(() ->
                new EntityNotFoundException("Przedmiot o ID " + ID + " nie został znaleziony"));
    }

    public boolean addPrzedmiot(Przedmiot przedmiot) {
        Przedmiot przedmiotAdded = przedmiotRepository.save(przedmiot);
        return przedmiotRepository.findById(przedmiotAdded.getID()).isPresent();
    }

    public boolean editPrzedmiot(Przedmiot przedmiot, long ID) {
        try {
            Przedmiot przedmiotEdited = getPrzedmiot(ID);

            przedmiotEdited.setNazwa(przedmiot.getNazwa());
            przedmiotEdited.setOceny(przedmiot.getOceny());

            Przedmiot przedmiotSaved = przedmiotRepository.save(przedmiotEdited);
            return przedmiotRepository.findById(przedmiotSaved.getID()).isPresent();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Wystąpił błąd podczas edycji przedmiotu");
        }
    }

    public boolean deletePrzedmiot(long ID) {
        try {
            if (przedmiotRepository.findById(ID).isPresent()) {
                przedmiotRepository.deleteById(ID);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Wystąpił błąd podczas usuwania przedmiotu");
        }
    }
}
