package by.delfihealth.salov.glucoreader_test.comport.dto;

import lombok.Data;

@Data
public class SerialPortDto {
      private  final Integer id;
      private final String name;
      private final String description;


      public SerialPortDto(Integer id, String systemPortName, String portDescription) {
            this.id = id;
            this.name = systemPortName;
            this.description = portDescription;
      }

}
