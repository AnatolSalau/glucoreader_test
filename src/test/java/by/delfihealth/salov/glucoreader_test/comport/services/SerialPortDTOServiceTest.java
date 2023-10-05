package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import com.fazecast.jSerialComm.SerialPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SerialPortDTOServiceTest {

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private SerialPortDTOService serialPortDTOService;

      @Test
      void convertSerialPortToJson() {
      }

      @Test
      void convertSerialPortDtoToDeviceDataDto() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");
            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);
            System.out.println(values);
      }

      @Test
      void convertValuesToDeviceValueDto() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");
            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);
            System.out.println(values);
            //serialPortDTOService.convertValuesToDeviceValueDto()
      }
}