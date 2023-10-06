package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteType;
import com.fazecast.jSerialComm.SerialPort;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SerialPortDTOServiceTest {

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private SerialPortDTOService serialPortDTOService;

      @Test
      void convertSerialPortToJson() {
      }

      @Test
      void convertSerialPortDtoToDeviceDataDto() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");
            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);
            System.out.println(values);
      }

      @Test
      void convertValuesToDeviceValueDto() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");
            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);
            System.out.println(values);
            //serialPortDTOService.convertValuesToDeviceValueDto()
      }

      @Test
      void convertHexByteDataValueToValueDto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            int value = 999;
            System.out.println(value);
            byte low = (byte) value;

            System.out.println("low: " + low);
            printBynary(low);
            byte high = (byte)(value >> 8);
            System.out.println("high: " + high);
            printBynary(high);


            Method getIdFromLowHiByte = serialPortDTOService.getClass()
                  .getDeclaredMethod("getIdFromLowHiByte", HexByteData.class, HexByteData.class);
            getIdFromLowHiByte.setAccessible(true);
            //999
            //00000011 1110 0111
            // 0x3
            HexByteData hexByteDataLo = new HexByteData(4, low, HexByteType.INDEX_LO);
            System.out.println("hexByteDataLo : " + hexByteDataLo);
            printBynary(hexByteDataLo.getByteValue());
            HexByteData hexByteDataHi = new HexByteData(4, high, HexByteType.INDEX_HI);
            System.out.println("hexByteDataHi : " + hexByteDataHi);
            printBynary(hexByteDataHi.getByteValue());
            int result = (int) getIdFromLowHiByte.invoke(serialPortDTOService,
                  hexByteDataLo,
                  hexByteDataHi);
            System.out.println("RESULT : " + result);
            //Assertions.assertEquals(999, result);
      }

      private  void printBynary(byte num)
      {
            int aux = Byte.toUnsignedInt(num);
            String binary = String.format("%8s", Integer.toBinaryString(aux)).replace(' ', '0');
            System.out.println(binary);
      }
}
