package by.delfihealth.salov.glucoreader_test.comport;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

      @Test
      void getProtocolVersion() {
            comPortService.openComPort("COM2", 19200, 8,
                  1, 2);
            List<HexByteData> protocolVersion = comPortService.getProtocolVersion();
            System.out.println(protocolVersion);
      }

      @Test
      void findAllPortsDTO() {
            List<SerialPortDTO> allPortsDTO = comPortService.findAllSerialPortsDTO();
            System.out.println(allPortsDTO);
      }

      @Test
      void convertSerialPortNameToID() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            ComPortService comPortService = new ComPortService();

            Method[] declaredMethods = ComPortService.class.getDeclaredMethods();
            System.out.println(declaredMethods);
            Method convertSerialPortNameToID = Arrays.stream(declaredMethods)
                  .filter(method -> method.getName().equals("convertSerialPortNameToID"))
                  .filter(method -> method.getReturnType().equals(Integer.class))
                  .findFirst().orElseGet(null);
            convertSerialPortNameToID.setAccessible(true);

            Long id = (Long) convertSerialPortNameToID.invoke(comPortService, "COM1");
            System.out.println(id);
      }

}