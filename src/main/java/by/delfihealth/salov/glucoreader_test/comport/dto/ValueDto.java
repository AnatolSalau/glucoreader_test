package by.delfihealth.salov.glucoreader_test.comport.dto;

import lombok.Data;

@Data
public class ValueDto {
      private final int id;
      private final String dateTime;
      private final double glucose;
      private final String temperature;
      private final double hematocrit;
      private final int state;
      private final int stateUserMark;
}
