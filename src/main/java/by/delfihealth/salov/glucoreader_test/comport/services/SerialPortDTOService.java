package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.DataDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.ValueDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SerialPortDTOService {
      public String convertSerialPortToJson (List<SerialPortDto> serialPortDTOList) {
            JSONArray jsonArray = new JSONArray(serialPortDTOList);
            JSONObject jsonComPorts = new JSONObject();
            jsonComPorts.put("comPortList", jsonArray);
            JSONObject jsonData = new JSONObject();
            jsonData.put("data", jsonComPorts);
            String jsonDataStr = jsonData.toString();
            return jsonDataStr;
      }

      public DataDto convertSerialPortDtoToDataDto(SerialPortDto serialPortDto) {
            return null;
      }

      /**
       *
       * @param valuesRaw - raw data bytes
       * @return List<ValueDto> - list from it we will create json
       */
      public List<ValueDto> convertValueRawToValueDto(List<HexByteData> valuesRaw) {
            List<HexByteData> subValuesList = valuesRaw.subList(4, valuesRaw.size()-2);
            final int oneValueLength = 15;
            if (subValuesList.size() % oneValueLength == 0) {
                  List<List<HexByteData>> values = IntStream.range(0, subValuesList.size())
                        .filter(i -> i % oneValueLength == 0)
                        .mapToObj(i -> subValuesList.subList(i, Math.min(i + oneValueLength, subValuesList.size())))
                        .collect(Collectors.toList());

                  List<ValueDto> result = new ArrayList<>();

                  for (List<HexByteData> value : values) {
                        int id = getNumberFromLowAndHighBytes(value.get(0), value.get(1));
                        String dateTime = getDateTimeFromBytes(
                              value.get(2), value.get(3), value.get(4),
                              value.get(5), value.get(6), value.get(7)
                        );
                        double glucose = getNumberFromWholeAndFractionalPart(value.get(8), value.get(9));
                        double temperature = getTemperatureFromWholeAndFractionalPart(value.get(10), value.get(11));
                        double hematocrit = getNumberFromWholeAndFractionalPart(value.get(12), value.get(13));
                        result.add(new ValueDto(id,dateTime,glucose,temperature,hematocrit, 0,0));

                  }
                  return result;
            }
            return null;
      }

      private int getNumberFromLowAndHighBytes(HexByteData indexLo, HexByteData indexHi) {
            return ((int) indexLo.getByteValue() & 0xff) | (((int) indexHi.getByteValue() & 0xff) << 8);
      }

      private String getDateTimeFromBytes(List<HexByteData> dateTimeRaw) {
            //'2023-10-05T15:16:17'
            int year = dateTimeRaw.get(4).getByteValue() + 2000;
            int month = dateTimeRaw.get(5).getByteValue();
            int day = dateTimeRaw.get(6).getByteValue();
            int hour = dateTimeRaw.get(7).getByteValue();
            int min = dateTimeRaw.get(8).getByteValue();
            int sec = dateTimeRaw.get(9).getByteValue();
            String result = String.format("%d-%d-%dT%d:%d:%d", year,month,day,hour,min,sec);
            return result;
      }

      private String getDateTimeFromBytes(HexByteData year, HexByteData month, HexByteData day,
                                          HexByteData hour, HexByteData min, HexByteData sec) {
            //'2023-10-05T15:16:17'
            String result = String.format("%d-%d-%dT%d:%d:%d",
                  year.getByteValue() + 2000,
                  month.getByteValue(),
                  day.getByteValue(),
                  hour.getByteValue(),
                  min.getByteValue(),
                  sec.getByteValue());
            return result;
      }

      private double getNumberFromWholeAndFractionalPart(HexByteData wholePart, HexByteData fractionalPart) {
            double glucoseWholePart = wholePart.getByteValue();
            double glucoseFractionalPart = fractionalPart.getByteValue();
            glucoseFractionalPart = glucoseFractionalPart / 100;
            double glucose = glucoseWholePart + glucoseFractionalPart;
            return  glucose;
      }

      private double getTemperatureFromWholeAndFractionalPart(HexByteData temperatureHi, HexByteData temperatureLo) {
            byte byteHi = temperatureHi.getByteValue();
            byte byteLo = temperatureLo.getByteValue();

            char[] charsHi = convertByteToCharArr(byteHi);
            if (charsHi[0] == '1') {
                  charsHi[0] = '0';
                  String hexStrFrom = HexByteData.getHexStrFrom(charsHi);
                  byte byteFrom = HexByteData.getByteFrom(hexStrFrom);

            }
            return 0.0;
      }

      private char[] convertByteToCharArr(byte num) {
            int aux = Byte.toUnsignedInt(num);
            String binary = String.format("%8s", Integer.toBinaryString(aux)).replace(' ', '0');
            char[] chars = binary.toCharArray();
            return chars;
      }
}
