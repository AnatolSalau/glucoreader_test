package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.ValueDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteType;
import com.fazecast.jSerialComm.SerialPort;
import jakarta.xml.bind.DatatypeConverter;
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
class SerialPortDTOServiceTest {

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private SerialPortDTOService serialPortDTOService;

      @Autowired
      private ControlSumCRC16Service controlSumCRC16Service;


      @Test
      void convertValueRawToValueDto() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);

            List<ValueDto> valueDtoList = serialPortDTOService.convertValueRawToValueDto(values);
      }

      @Test
      void getIdFromLowHiBytetest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
            int value = 999;

            byte low = (byte) value;
            printBynary(low);

            byte high = (byte) (value >> 8);
            printBynary(high);

            Method getIdFromLowHiByte = serialPortDTOService.getClass()
                  .getDeclaredMethod("getNumberFromLowAndHighBytes", HexByteData.class, HexByteData.class);
            getIdFromLowHiByte.setAccessible(true);

            HexByteData hexByteDataLo = new HexByteData(4, low, HexByteType.INDEX_LO);
            printBynary(hexByteDataLo.getByteValue());

            HexByteData hexByteDataHi = new HexByteData(4, high, HexByteType.INDEX_HI);
            printBynary(hexByteDataHi.getByteValue());

            int result = (int) getIdFromLowHiByte.invoke(serialPortDTOService,
                  hexByteDataLo,
                  hexByteDataHi);

            System.out.println("RESULT : " + result);
            Assertions.assertEquals(999, result);
      }

      @Test
      void getDateTimeFromBytes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

            Method getIdFromLowHiByte = serialPortDTOService.getClass()
                  .getDeclaredMethod("getDateTimeFromBytes", List.class);
            getIdFromLowHiByte.setAccessible(true);
            getIdFromLowHiByte.invoke(serialPortDTOService, dateTimeRaw);
      }

      private void printBynary(byte num) {
            int aux = Byte.toUnsignedInt(num);
            String binary = String.format("%8s", Integer.toBinaryString(aux)).replace(' ', '0');
            System.out.println(binary);
      }
}
