package by.delfihealth.salov.glucoreader_test.comport.dto;

import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import lombok.Data;

import java.util.List;

@Data
public class SerialPortDTO {
      private  final Integer id;
      private final String name;
      private final String description;
      private final List<HexByteData> protocolVersion;

      public SerialPortDTO(Integer id, String systemPortName, String portDescription) {
            this.id = id;
            this.name = systemPortName;
            this.description = portDescription;
            this.protocolVersion = null;
      }

      public SerialPortDTO(Integer id, String systemPortName, String portDescription, List<HexByteData> protocolVersion) {
            this.id = id;
            this.name = systemPortName;
            this.description = portDescription;
            this.protocolVersion = protocolVersion;
      }
}
