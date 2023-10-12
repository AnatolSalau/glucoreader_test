package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.ConverterTypeDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.DataValueDto;
import by.delfihealth.salov.glucoreader_test.comport.dto.DataSerialPortDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DataDTOService {
      public String convertSerialPortToJson(List<DataSerialPortDto> dataSerialPortDTOList) {
            JSONArray jsonArray = new JSONArray(dataSerialPortDTOList);
            JSONObject jsonComPorts = new JSONObject();
            jsonComPorts.put("comPortList", jsonArray);
            JSONObject jsonData = new JSONObject();
            jsonData.put("data", jsonComPorts);
            String jsonDataStr = jsonData.toString();
            return jsonDataStr;
      }

      /**
       *
       * @param converterType - raw data bytes
       * @return ConverterTypeDto - type of converter with addition information like serial id and etc
       */
      public ConverterTypeDto convertConverterTypeRawToConverterTypeDto(List<HexByteData> converterType) {
            int deviceType = converterType.get(4).getByteValue();
            byte idB0 = converterType.get(5).getByteValue();
            byte idB1 = converterType.get(6).getByteValue();
            byte idB2 = converterType.get(7).getByteValue();
            byte idB3 = converterType.get(8).getByteValue();
            byte idB4 = converterType.get(9).getByteValue();
            return null;
      }

      /**
       * @param valuesRaw - raw data bytes
       * @return List<ValueDto> - list from it we will create json
       */
      public List<DataValueDto> convertValuesRawToValuesDto(List<HexByteData> valuesRaw) {
            List<HexByteData> subValuesList = valuesRaw.subList(4, valuesRaw.size() - 2);
            final int oneValueLength = 15;
            if (subValuesList.size() % oneValueLength == 0) {
                  List<List<HexByteData>> values = IntStream.range(0, subValuesList.size())
                        .filter(i -> i % oneValueLength == 0)
                        .mapToObj(i -> subValuesList.subList(i, Math.min(i + oneValueLength, subValuesList.size())))
                        .toList();

                  List<DataValueDto> result = new ArrayList<>();

                  for (List<HexByteData> value : values) {
                        int id = getNumberFromLowAndHighBytes(value.get(0), value.get(1));
                        String dateTime = getDateTimeFromBytes(
                              value.get(2), value.get(3), value.get(4),
                              value.get(5), value.get(6), value.get(7)
                        );
                        double glucose = getNumberFromWholeAndFractionalPart(value.get(8), value.get(9));
                        String temperature = getTemperatureFromWholeAndFractionalPart(value.get(10), value.get(11));
                        double hematocrit = getNumberFromWholeAndFractionalPart(value.get(12), value.get(13));
                        int state = getStateFromByte(value.get(14));
                        int stateUserMark = getStateUserMarkFromByte(value.get(14));
                        result.add(new DataValueDto(id, dateTime, glucose, temperature, hematocrit, state, stateUserMark));
                  }
                  return result;
            }
            return null;
      }

      private String getSerialNumberFromBytes(
            HexByteData id0, HexByteData id1, HexByteData id2, HexByteData id3,
            HexByteData id4, HexByteData id5, HexByteData id6, HexByteData id7
      ) throws UnsupportedEncodingException {
            byte idB0 = id0.getByteValue();
            byte idB1 = id1.getByteValue();
            byte idB2 = id2.getByteValue();
            byte idB3 = id3.getByteValue();
            byte idB4 = id4.getByteValue();
            byte idB5 = id5.getByteValue();
            byte idB6 = id6.getByteValue();
            byte idB7 = id7.getByteValue();
            int intB0 = Byte.toUnsignedInt(idB0);
            int intB1 = Byte.toUnsignedInt(idB1);
            int intB2 = Byte.toUnsignedInt(idB2);
            int intB3 = Byte.toUnsignedInt(idB3);
            int intB4 = Byte.toUnsignedInt(idB4);
            int intB5 = Byte.toUnsignedInt(idB5);
            int intB6 = Byte.toUnsignedInt(idB6);
            int intB7 = Byte.toUnsignedInt(idB7);
            Charset charset = Charset.forName("windows-1251");
            CharsetDecoder decoder = charset.newDecoder();

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
            String result = String.format("%d-%d-%dT%d:%d:%d", year, month, day, hour, min, sec);
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

      private int getStateFromByte(HexByteData state) {
            String binaryRepresentation = convertByteToStringBinaryRepresentation(state.getByteValue());
            String stateCode = binaryRepresentation.substring(
                  binaryRepresentation.length() - 2);
            return Integer.parseInt(stateCode);
      }

      private int getStateUserMarkFromByte(HexByteData state) {
            String binaryRepresentation = convertByteToStringBinaryRepresentation(state.getByteValue());
            String stateCode = binaryRepresentation.substring(
                  binaryRepresentation.length() - 3, binaryRepresentation.length() - 2);
            return Integer.parseInt(stateCode);
      }

      private String getTemperatureFromWholeAndFractionalPart(HexByteData temperatureHi, HexByteData temperatureLo) {
            byte byteHi = temperatureHi.getByteValue();
            byte byteLo = temperatureLo.getByteValue();

            String charsHi = convertByteToStringBinaryRepresentation(byteHi);
            if (charsHi.charAt(0) == '1') {
                  int val = Integer.parseInt(charsHi.replaceFirst("1", "0"), 2);
                  byte newByteHi = (byte) val;
                  double result = getNumberFromWholeAndFractionalPart(newByteHi, byteLo);
                  return Double.toString(result);
            }
            double result = getNumberFromWholeAndFractionalPart(byteHi, byteLo);
            return Double.toString(result);
      }

      private double getNumberFromWholeAndFractionalPart(HexByteData wholePart, HexByteData fractionalPart) {
            double wholePartDouble = wholePart.getByteValue();
            double fractionalPartDouble = fractionalPart.getByteValue();
            fractionalPartDouble = fractionalPartDouble / 100;
            double result = wholePartDouble + fractionalPartDouble;
            return result;
      }

      private double getNumberFromWholeAndFractionalPart(byte wholePartByte, byte fractionalPartByte) {
            double wholePartDouble = wholePartByte;
            double fractionalPartDouble = fractionalPartByte;
            fractionalPartDouble = fractionalPartDouble / 100;
            double result = wholePartDouble + fractionalPartDouble;
            return result;
      }

      private String convertByteToStringBinaryRepresentation(byte num) {
            int aux = Byte.toUnsignedInt(num);
            String binaryRepresentation = String.format("%8s", Integer.toBinaryString(aux)).replace(' ', '0');
            return binaryRepresentation;
      }
}
