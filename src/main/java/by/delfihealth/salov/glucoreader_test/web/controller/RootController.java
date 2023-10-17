package by.delfihealth.salov.glucoreader_test.web.controller;

import by.delfihealth.salov.glucoreader_test.comport.dto.DeviceDto;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import by.delfihealth.salov.glucoreader_test.comport.services.DeviceDTOService;
import com.fazecast.jSerialComm.SerialPort;
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
      private DeviceDTOService deviceDTOService;

      @GetMapping()
      public  String getAllData() {
            List<SerialPort> serialPorts = comPortService.findAllComPortsByDescriptionStartWith("ELTIMA");

            List<DeviceDto> deviceDtoListFromComportList = deviceDTOService.getDeviceDtoListFromComportList(
                  serialPorts, 19200, 8, 1, 2
            );

            String json = deviceDTOService.convertDeviceDtoToJson(deviceDtoListFromComportList);
            return json;
      }
}
