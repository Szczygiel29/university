package com.example.demo.sercive;

import com.example.demo.model.Student;
import com.example.demo.repository.StudnetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudnetRepository studnetRepository;

    public List<Student> getAllStudents() {
        return studnetRepository.findAll();
    }

    public Student getStudent(long ID) {
        return studnetRepository.findById(ID).orElseThrow(() ->
                new EntityNotFoundException("Student o ID " + ID + " nie został znaleziony"));
    }

    public boolean addStudent(Student student) {
        Student studentAdded = studnetRepository.save(student);
        return studnetRepository.findById(studentAdded.getID()).isPresent();
    }

    public boolean editStudent(Student student, long ID) {
        try {
            Student studentEdited = getStudent(ID);

            studentEdited.setImie(student.getImie());
            studentEdited.setNazwisko(student.getNazwisko());
            studentEdited.setOceny(student.getOceny());
            studentEdited.setNrIndeksu(student.getNrIndeksu());

            Student studnetSaved = studnetRepository.save(studentEdited);
            return studnetRepository.findById(studnetSaved.getID()).isPresent();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Wystąpił błąd podczas edycji studenta");
        }
    }

    public boolean deleteStudent(long ID) {
        try {
            if (studnetRepository.findById(ID).isPresent()) {
                studnetRepository.deleteById(ID);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Wystąpił błąd podczas usuwania studenta");
        }
    }
}

