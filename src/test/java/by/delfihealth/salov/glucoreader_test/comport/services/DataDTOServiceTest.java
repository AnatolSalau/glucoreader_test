package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.DataValueDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteType;
import com.fazecast.jSerialComm.SerialPort;
import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class DataDTOServiceTest {

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private DataDTOService dataDTOService;

      @Autowired
      private ControlSumCRC16Service controlSumCRC16Service;


      @Test
      void convertValueRawToValueDtoTest() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);

            List<DataValueDto> dataValueDtoList = dataDTOService.convertValuesRawToValuesDto(values);
            System.out.println(dataValueDtoList);
      }

      @Test
      void getIdFromLowHiBytetestTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            int value = 999;

            byte low = (byte) value;
            printBynary(low);

            byte high = (byte) (value >> 8);
            printBynary(high);

            Method getIdFromLowHiByte = dataDTOService.getClass()
                  .getDeclaredMethod("getNumberFromLowAndHighBytes", HexByteData.class, HexByteData.class);
            getIdFromLowHiByte.setAccessible(true);

            HexByteData hexByteDataLo = new HexByteData(4, low, HexByteType.INDEX_LO);
            printBynary(hexByteDataLo.getByteValue());

            HexByteData hexByteDataHi = new HexByteData(4, high, HexByteType.INDEX_HI);
            printBynary(hexByteDataHi.getByteValue());

            int result = (int) getIdFromLowHiByte.invoke(dataDTOService,
                  hexByteDataLo,
                  hexByteDataHi);

            System.out.println("RESULT : " + result);
            Assertions.assertEquals(999, result);
      }

      @Test
      void getDateTimeFromBytesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            // HexByteData{'number='7', value='0x17', type='DATE_YEAR'}, -- 17 + 2000 = 2017 year
            // HexByteData{'number='8', value='0x0A', type='DATE_MONTH'},-- 10 month
            // HexByteData{'number='9', value='0x06', type='DATE_DAY'}, -- 6 date

            List<HexByteData> dateTimeRaw = new ArrayList<>(11);
            dateTimeRaw.add(new HexByteData(0, "0x02", HexByteType.STX));
            dateTimeRaw.add(new HexByteData(1, "0x0C", HexByteType.LEN_LO));
            dateTimeRaw.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            dateTimeRaw.add(new HexByteData(3, "0x04", HexByteType.CMD));
            dateTimeRaw.add(new HexByteData(4, "0x17", HexByteType.DATE_YEAR));
            dateTimeRaw.add(new HexByteData(5, "0x0A", HexByteType.DATE_MONTH));
            dateTimeRaw.add(new HexByteData(6, "0x06", HexByteType.DATE_DAY));
            dateTimeRaw.add(new HexByteData(7, "0x10", HexByteType.TIME_HOUR));
            dateTimeRaw.add(new HexByteData(8, "0x22", HexByteType.TIME_MINUTE));
            dateTimeRaw.add(new HexByteData(9, "0x22", HexByteType.TIME_SEC));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(dateTimeRaw);
            dateTimeRaw.add(new HexByteData(10, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            dateTimeRaw.add(new HexByteData(11, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            Method getIdFromLowHiByte = dataDTOService.getClass()
                  .getDeclaredMethod("getDateTimeFromBytes", List.class);
            getIdFromLowHiByte.setAccessible(true);
            getIdFromLowHiByte.invoke(dataDTOService, dateTimeRaw);
      }

      @Test
      void convertByteToCharArrTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData temperatureHi = new HexByteData(8, "0x87", HexByteType.TE_HI);
            Method convertByteToStringBinaryRepresentation = dataDTOService.getClass()
                  .getDeclaredMethod("convertByteToStringBinaryRepresentation", byte.class);
            convertByteToStringBinaryRepresentation.setAccessible(true);

            String result = (String) convertByteToStringBinaryRepresentation.invoke(dataDTOService,
                  temperatureHi.getByteValue());
            System.out.println(result);

      }

      @Test
      void getTemperatureFromWholeAndFractionalPartTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData wholePart = new HexByteData(4, "0x17", HexByteType.INDEX_LO);
            HexByteData fractionalPart = new HexByteData(4, "0x2D", HexByteType.INDEX_HI);

            Method getTemperatureFromWholeAndFractionalPart = dataDTOService.getClass()
                  .getDeclaredMethod("getTemperatureFromWholeAndFractionalPart", HexByteData.class, HexByteData.class);
            getTemperatureFromWholeAndFractionalPart.setAccessible(true);
            getTemperatureFromWholeAndFractionalPart.invoke(dataDTOService, wholePart, fractionalPart);
      }

      @Test
      void getStateFromByteTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData state = new HexByteData(4, "0x03", HexByteType.STATE);

            Method getStateFromByte = dataDTOService.getClass()
                  .getDeclaredMethod("getStateFromByte", HexByteData.class);
            getStateFromByte.setAccessible(true);

            int result = (int) getStateFromByte.invoke(dataDTOService, state);
            Assertions.assertEquals(11, result);
      }

      @Test
      void getStateUserMarkFromByteTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData state = new HexByteData(4, "0x04", HexByteType.STATE);

            Method getUserStateFromByte = dataDTOService.getClass()
                  .getDeclaredMethod("getStateUserMarkFromByte", HexByteData.class);
            getUserStateFromByte.setAccessible(true);

            int result = (int) getUserStateFromByte.invoke(dataDTOService, state);
            Assertions.assertEquals(1, result);
      }

      @Test
      void getSerialNumberFromBytesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData id0 = new HexByteData(0, "0xC0", HexByteType.SERIAL_ID_B0);
            HexByteData id1 = new HexByteData(1, "0xC1", HexByteType.SERIAL_ID_B1);
            HexByteData id2 = new HexByteData(2, "0xC2", HexByteType.SERIAL_ID_B2);
            HexByteData id3 = new HexByteData(3, "0x44", HexByteType.SERIAL_ID_B3);
            HexByteData id4 = new HexByteData(4, "0x45", HexByteType.SERIAL_ID_B4);
            HexByteData id5 = new HexByteData(5, "0x46", HexByteType.SERIAL_ID_B5);
            HexByteData id6 = new HexByteData(6, "0x47", HexByteType.SERIAL_ID_B6);
            HexByteData id7 = new HexByteData(7, "0x48", HexByteType.SERIAL_ID_B7);

            Method getSerialNumberFromBytes = dataDTOService.getClass()
                  .getDeclaredMethod("getSerialNumberFromBytes",
                        HexByteData.class, HexByteData.class, HexByteData.class, HexByteData.class,
                        HexByteData.class, HexByteData.class, HexByteData.class, HexByteData.class);
            getSerialNumberFromBytes.setAccessible(true);

            getSerialNumberFromBytes.invoke(dataDTOService,id0, id1, id2, id3, id4, id5, id6, id7);

      }
      private void printBynary(byte num) {
            int aux = Byte.toUnsignedInt(num);
            String binary = String.format("%8s", Integer.toBinaryString(aux)).replace(' ', '0');
            System.out.println(binary);
      }

}
