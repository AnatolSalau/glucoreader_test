package by.delfihealth.salov.glucoreader_test.comport.dto;

import lombok.Data;

@Data
public class StateDto {
      private final int errorCode;
      private final String temperature;
      private final int battery;
}
