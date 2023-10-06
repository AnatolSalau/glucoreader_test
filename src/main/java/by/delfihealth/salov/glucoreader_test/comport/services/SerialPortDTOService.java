package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.DataDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.ValueDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

      public DataDto convertSerialPortDtoToDataDto(SerialPortDto serialPortDto) {
            int idRaw = serialPortDto.getId().intValue();
            String nameRaw = serialPortDto.getName();
            String descriptionRaw = serialPortDto.getDescription();
            List<HexByteData> protocolVersionHex = serialPortDto.getProtocolVersion();
            List<HexByteData> values = serialPortDto.getValues();
            return null;
      }

      /**
       *
       * @param valuesRaw - raw data bytes
       * @return List<ValueDto> - list from it we will create json
       */
      public List<ValueDto> convertHexByteDataValueToValueDto(List<HexByteData> valuesRaw, int capacityOfList) {
            List<ValueDto> resultValueDtoList = new ArrayList<>(capacityOfList);
            for (int i = 0; i < capacityOfList; i++) {
                  HexByteData indexLo = valuesRaw.get(4);
                  HexByteData indexHi = valuesRaw.get(5);
            }
            return null;
      }

      private int getIdFromLowHiByte(HexByteData indexLo, HexByteData indexHi) {
            return ((int) indexLo.getByteValue() & 0xff) | (((int) indexHi.getByteValue() & 0xff) << 8);
      }
}
