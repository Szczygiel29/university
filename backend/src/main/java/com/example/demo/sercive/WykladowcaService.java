package com.example.demo.sercive;

import com.example.demo.model.Wykladowca;
import com.example.demo.repository.WykladowcaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WykladowcaService {
    private final WykladowcaRepository wykladowcaRepository;

    public List<Wykladowca> getAllWykladowca(){
        return wykladowcaRepository.findAll();
    }

    public Wykladowca getWykladowca(long ID){
        return wykladowcaRepository.findById(ID).orElseThrow(() ->
                new EntityNotFoundException("Wykładowca o ID " + ID + " nie został znaleziony"));
    }

    public boolean addWykladowca(Wykladowca wykladowca){
        Wykladowca wykladowcaSaved = wykladowcaRepository.save(wykladowca);
        return wykladowcaRepository.findById(wykladowcaSaved.getID()).isPresent();
    }

    public boolean editWykladowca(Wykladowca wykladowca, long ID){
        try {
            Wykladowca wykladowcaEdited = getWykladowca(ID);

            wykladowcaEdited.setNazwisko(wykladowca.getNazwisko());
            wykladowcaEdited.setImie(wykladowca.getImie());
            wykladowcaEdited.setOceny(wykladowca.getOceny());

            Wykladowca wykladowcaSaved = wykladowcaRepository.save(wykladowcaEdited);
            return wykladowcaRepository.findById(wykladowcaSaved.getID()).isPresent();
        }catch (DataAccessException e){
            e.printStackTrace();
            throw new RuntimeException("Wystąpił błąd podczas edycji wykładowcy");
        }
    }

    public boolean deleteWykladowca(long ID){
        try {
            if(wykladowcaRepository.findById(ID).isPresent()){
                wykladowcaRepository.deleteById(ID);
                return true;
            }
            return false;
        }catch (DataAccessException e){
            e.printStackTrace();
            throw new RuntimeException("Wystapił błąd podczas usuwania wykładowyc");
        }
    }
}
