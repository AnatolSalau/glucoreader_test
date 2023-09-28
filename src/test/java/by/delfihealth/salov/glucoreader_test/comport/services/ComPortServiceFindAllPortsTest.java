package by.delfihealth.salov.glucoreader_test.comport.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComPortServiceFindAllPortsTest {

      @Autowired
      private ComPortServiceFindAllPorts comPortServiceFindAllPorts;

      @Test
      void findAllSerialPortsDTO() {
            comPortServiceFindAllPorts.findAllSerialPortsDTO();
      }

      @Test
      void getProtocolVersion() {
            comPortServiceFindAllPorts.findSerialPortByName()
      }
}