package by.delfihealth.salov.glucoreader_test.comport.dto;

import lombok.Data;

@Data
public class DeviceTypeDto {
      private final int deviceType;
      private final String serialId;
      private final int hwVersion;
      private final int swVersionLow;
      private final int swVersionHigh;
}
