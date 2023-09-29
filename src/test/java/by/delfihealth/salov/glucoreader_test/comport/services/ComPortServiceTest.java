package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import com.fazecast.jSerialComm.SerialPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ComPortServiceTest {

      @Autowired
      private ComPortService comPortService;

      @Test
      void findAllSerialPortsDTO() {
            comPortService.findAllSerialPortsDTO();
      }

      @Test
      void getProtocolVersion() {
            SerialPort com2 = comPortService.findSerialPortByName("COM2");

            List<HexByteData> protocolVersion = comPortService
                  .getProtocolVersion(com2, 19200, 8, 1, 2);
            System.out.println(protocolVersion);
      }

      @Test
      void getDeviceType() {
            SerialPort com2 = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .getDeviceType(com2, 19200, 8, 1, 2);
            System.out.println(deviceType);
      }
}