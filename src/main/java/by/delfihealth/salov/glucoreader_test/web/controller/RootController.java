package by.delfihealth.salov.glucoreader_test.web.controller;

import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import by.delfihealth.salov.glucoreader_test.comport.services.DeviceDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
      @Autowired
      private ComPortService comPortService;

      @Autowired
      private DeviceDTOService deviceDTOService;

      @GetMapping("ports")
      public /*ResponseEntity<List<SerialPortDTO>>*/ String getAllComPorts() {
/*            //List<DataSerialPortDto> serialPorts = comPortService.findAllSerialPortsDtoWithDataByName("COM2",19200,8,1,2);
//            List<SerialPortDTO> serialPorts = comPortService.findAllSerialPortsDtoWithoutData();
            String s = dataDeviceDTOService.convertSerialPortToJson(serialPorts);*/
            return null;
      }
}
