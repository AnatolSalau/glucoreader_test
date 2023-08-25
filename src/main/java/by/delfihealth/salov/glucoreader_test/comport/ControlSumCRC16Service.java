package by.delfihealth.salov.glucoreader_test.comport;

import javafx.util.Pair;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
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
