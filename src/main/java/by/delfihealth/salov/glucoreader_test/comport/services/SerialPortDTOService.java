package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialPortDTOService {
      public String convertSerialPortToJson (List<SerialPortDTO> serialPortDTOList) {
            JSONArray jsonArray = new JSONArray(serialPortDTOList);
            JSONObject jsonComPorts = new JSONObject();
            jsonComPorts.put("comPortList", jsonArray);
            JSONObject jsonData = new JSONObject();
            jsonData.put("data", jsonComPorts);
            String jsonDataStr = jsonData.toString();
            return jsonDataStr;
      }
}
