package by.delfihealth.salov.glucoreader_test.db.repository;

import by.delfihealth.salov.glucoreader_test.db.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Integer> {
      @Override
      <S extends Patient> S save(S entity) ;

      @Override
      List<Patient> findAll() ;
      List<Patient> findAllByDeletedEquals(boolean isDeleted) ;

      void removePatientById(Integer id) ;

      void removePatientByLogin(String login) ;
}
