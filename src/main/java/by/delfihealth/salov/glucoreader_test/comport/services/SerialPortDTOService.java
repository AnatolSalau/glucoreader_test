package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.DataDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.ValueDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialPortDTOService {
      public String convertSerialPortToJson (List<SerialPortDto> serialPortDTOList) {
            JSONArray jsonArray = new JSONArray(serialPortDTOList);
            JSONObject jsonComPorts = new JSONObject();
            jsonComPorts.put("comPortList", jsonArray);
            JSONObject jsonData = new JSONObject();
            jsonData.put("data", jsonComPorts);
            String jsonDataStr = jsonData.toString();
            return jsonDataStr;
      }

      public DataDto convertSerialPortDtoToDeviceDataDto(SerialPortDto serialPortDto) {
            int idRaw = serialPortDto.getId().intValue();
            String nameRaw = serialPortDto.getName();
            String descriptionRaw = serialPortDto.getDescription();
            List<HexByteData> protocolVersionHex = serialPortDto.getProtocolVersion();
            List<HexByteData> values = serialPortDto.getValues();
            return null;
      }
      public ValueDto convertValuesToDeviceValueDto(List<HexByteData> valuesRaw) {
            return null;
      }
}
