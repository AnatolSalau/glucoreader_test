package by.delfihealth.salov.glucoreader_test.comport.dto;

import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data

@AllArgsConstructor
public class SerialPortDTO {
      private  final Integer id;
      private final String name;
      private final String description;
      private final List<HexByteData> protocolVersion;
      private final List<HexByteData> deviceType;
      private final List<HexByteData> state;
      private final List<HexByteData> dateTime;
      private final List<HexByteData> converterType;
      private final List<HexByteData> values;

      public SerialPortDTO(Integer id, String systemPortName, String portDescription) {
            this.id = id;
            this.name = systemPortName;
            this.description = portDescription;
            this.protocolVersion = null;
            this.deviceType = null;
            this.state = null;
            this.dateTime = null;
            this.converterType = null;
            this.values = null;
      }

}
