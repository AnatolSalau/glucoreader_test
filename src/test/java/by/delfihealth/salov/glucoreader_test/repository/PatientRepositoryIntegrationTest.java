package by.delfihealth.salov.glucoreader_test.repository;

import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PatientRepositoryIntegrationTest {
      private static Logger logger = LoggerFactory.getLogger(PatientRepositoryIntegrationTest.class);

      @Test
      void saveIntegrationTest() {
            logger.info("Test: PatientRepository");
      }

      @Test
      void findAll() {
      }

      @Test
      void findAllByDeletedEquals() {
      }

      @Test
      void removePatientById() {
      }

      @Test
      void removePatientByLogin() {
      }
}