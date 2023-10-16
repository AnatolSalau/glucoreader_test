package by.delfihealth.salov.glucoreader_test.comport.dto;

import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DataSerialPortDto {
      private  final Integer id;
      private final String name;
      private final String description;


      public DataSerialPortDto(Integer id, String systemPortName, String portDescription) {
            this.id = id;
            this.name = systemPortName;
            this.description = portDescription;
      }

}
