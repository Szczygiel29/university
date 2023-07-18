package com.example.demo.sercive;

import com.example.demo.model.Ocena;
import com.example.demo.repository.OcenaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcenaService {
    private final OcenaRepository ocenaRepository;

    public List<Ocena> getAllOcena() {
        return ocenaRepository.findAll();
    }

    public Ocena getOcena(long ID) {
        return ocenaRepository.findById(ID).orElseThrow(() ->
                new EntityNotFoundException("Ocena o ID " + ID + " nie została znaleziona"));
    }

    public boolean addOcena(Ocena ocena) {
        Ocena ocenaSaved = ocenaRepository.save(ocena);
        return ocenaRepository.findById(ocenaSaved.getID()).isPresent();
    }

    public boolean editOcena(Ocena ocena, long ID) {
        try {
            Ocena ocenaEdited = getOcena(ID);

            ocenaEdited.setOcena(ocena.getOcena());
            ocenaEdited.setPrzedmiot(ocena.getPrzedmiot());
            ocenaEdited.setStudent(ocena.getStudent());
            ocenaEdited.setWykladowca(ocena.getWykladowca());

            Ocena ocenaSaved = ocenaRepository.save(ocenaEdited);
            return ocenaRepository.findById(ocenaSaved.getID()).isPresent();

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Wystapił bład podczas edycji oceny");
        }
    }

    public boolean deleteOcena(long ID) {
        try {
            if (ocenaRepository.findById(ID).isPresent()) {
                ocenaRepository.deleteById(ID);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Wystąpił bład podczas usuwania oceny");
        }
    }
}
