package by.delfihealth.salov.glucoreader_test.web.controller;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDto;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import by.delfihealth.salov.glucoreader_test.comport.services.SerialPortDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class RootController {
      @Autowired
      private ComPortService comPortService;

      @Autowired
      private SerialPortDTOService serialPortDTOService;

      @GetMapping("ports")
      public /*ResponseEntity<List<SerialPortDTO>>*/ String getAllComPorts() {
            List<SerialPortDto> serialPorts = comPortService.findAllSerialPortsDtoWithDataByName("COM2",19200,8,1,2);
//            List<SerialPortDTO> serialPorts = comPortService.findAllSerialPortsDtoWithoutData();
            String s = serialPortDTOService.convertSerialPortToJson(serialPorts);
            return s;
      }
}
