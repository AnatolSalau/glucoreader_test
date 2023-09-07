package by.delfihealth.salov.glucoreader_test.web;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
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

      @GetMapping("ports")
      public ResponseEntity<List<SerialPortDTO>> getAllComPorts() {
            List<SerialPortDTO> serialPorts = comPortService.findAllSerialPortsDTO();
            return ResponseEntity
                        .status(200)
                  .body(serialPorts);
      }
}
