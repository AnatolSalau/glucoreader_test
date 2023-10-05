package by.delfihealth.salov.glucoreader_test.comport.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeviceValuesDtoTest {
      @Test
      void getDeviceValuesDtoTest() {
            System.out.println(new ValueDto(
                  1, "DateTime", 4.5, 40, 1, 1
            ));
      }
}