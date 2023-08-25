package by.delfihealth.salov.glucoreader_test.comport;

import com.fazecast.jSerialComm.SerialPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class ComPortService {
      
      List<SerialPort> serialPorts;
      private SerialPort currentCommPort;

      public ComPortService() {
            this.serialPorts = findAllComPorts();
      }

      private List<SerialPort> findAllComPorts() {
            SerialPort[] commPorts = SerialPort.getCommPorts();
            List<SerialPort> serialPorts = Arrays.asList(commPorts);
            return serialPorts;
      }

      public boolean openComPort(String portSystemName, int baudRate, int dataBits,
                                 int stopBits, int parity) {
            this.currentCommPort = SerialPort.getCommPort(portSystemName);
            this.currentCommPort.openPort();
            this.currentCommPort.setComPortParameters(baudRate,dataBits,
                  stopBits,parity);
            boolean isOpen = currentCommPort.isOpen();
            return isOpen;
      }



      public boolean closeComport() {
            if (currentCommPort.isOpen())
                  return currentCommPort.closePort();
            return false;
      }
}
