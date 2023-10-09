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
            comPortService.findAllSerialPortsDtoWithoutData();
      }

      @Test
      void getProtocolVersion() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> protocolVersion = comPortService
                  .getProtocolVersion(portByName, 19200, 8, 1, 2);
            System.out.println(protocolVersion);
      }

      @Test
      void getDeviceType() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .getDeviceType(portByName, 19200, 8, 1, 2);
            System.out.println(deviceType);
      }

      @Test
      void getState() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .getState(portByName, 19200, 8, 1, 2);
            System.out.println(deviceType);
      }

      @Test
      void getDateTime() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .getDateTime(portByName, 19200, 8, 1, 2);
            System.out.println(deviceType);
      }

      @Test
      void getConverterType() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .getConverterType(portByName, 19200, 8, 1, 2);
            System.out.println(deviceType);
      }

      @Test
      void getValues() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);
            System.out.println(values);
      }

      @Test
      void setCurrentDateTime() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .setCurrentDateTime(portByName, 19200, 8, 1, 2);
            System.out.println(deviceType);
      }

      @Test
      void setConverterType() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> deviceType = comPortService
                  .setConverterType(portByName, 19200, 8, 1, 2, "0x03",
                        "0xB0", "0xB1", "0xB2","0xB3","0xB4", "0xB5",
                        "0xB6", "0xB7", "0x02","0x00", "0x02");
            System.out.println(deviceType);
      }
}