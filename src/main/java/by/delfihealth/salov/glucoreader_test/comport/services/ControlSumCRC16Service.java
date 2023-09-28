package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.utils.CRC16;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
public class ControlSumCRC16Service {
      public Pair<String, String> getHighLowByteOfSum(List<HexByteData> hexByteData) {
            CRC16 checksum = new CRC16();
            hexByteData
                  .stream()
                  .map(HexByteData::getByteValue)
                  .forEach(checksum::update);
            System.out.println(checksum);
            String crcLoHiHex = Integer.toHexString((int) checksum.getValue());
            String crcHiHex = "0x" + crcLoHiHex.substring(0, 2).toUpperCase();
            String crcLoHex = "0x" + crcLoHiHex.substring(2, 4).toUpperCase();
            return new Pair<>(crcHiHex, crcLoHex);
      }
}
