package by.delfihealth.salov.glucoreader_test.web;

import by.delfihealth.salov.glucoreader_test.comport.ComPortService;
import by.delfihealth.salov.glucoreader_test.comport.HexByteData;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class RootController {
      @Autowired
      private ComPortService comPortService;

      @GetMapping("getallportnames")
      public ResponseEntity<List<String>> getAllComPortsNames() {
            List<SerialPort> serialPorts = comPortService.getSerialPorts();
            List<String> serialPortNames = serialPorts
                  .stream()
                  .map(SerialPort::getSystemPortName)
                  .collect(Collectors.toList());
            return ResponseEntity
                        .status(200)
                  .body(serialPortNames);
      }
}
