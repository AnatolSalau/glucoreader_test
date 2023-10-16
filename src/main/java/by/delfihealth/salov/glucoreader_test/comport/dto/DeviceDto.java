package by.delfihealth.salov.glucoreader_test.comport.dto;

import lombok.Data;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

@Data
public class DeviceDto {
      private final ComPortDto comPortDto;
      private final ProtocolVersionDto protocolVersionDto;
      private final DeviceTypeDto deviceTypeDto;
      private final StateDto stateDto;
      private final  String dateTime;
      private final DeviceTypeDto converterType;
      private final List<ValueDto> valueDtoList;
}
