package by.delfihealth.salov.glucoreader_test.comport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComPortServiceTest {
      @Autowired
      private ComPortService comPortService;

      @Test
      void openComPort() {
            boolean com2IsOpen = comPortService.openComPort("COM2", 19200, 8,
                  1, 2);
            Assertions.assertEquals(true, com2IsOpen);
      }

      @Test
      void closeComport() {
      }
}