package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.*;
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
class DeviceDTOServiceTest {

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private DeviceDTOService deviceDTOService;

      @Autowired
      private ControlSumCRC16Service controlSumCRC16Service;


      @Test
      void convertValueRawToValueDtoTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            List<HexByteData> values = comPortService
                  .getValues(portByName, 19200, 8, 1, 2);

            Method convertValuesRawToValuesDto = deviceDTOService.getClass()
                  .getDeclaredMethod("convertValuesRawToValuesDto", List.class);
            convertValuesRawToValuesDto.setAccessible(true);

            List<ValueDto> result = (List<ValueDto>) convertValuesRawToValuesDto.invoke(deviceDTOService,
                  values);
            System.out.println(result);
      }

      @Test
      void getIdFromLowHiBytetestTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            int value = 999;

            byte low = (byte) value;
            printBinary(low);

            byte high = (byte) (value >> 8);
            printBinary(high);

            Method getIdFromLowHiByte = deviceDTOService.getClass()
                  .getDeclaredMethod("getNumberFromLowAndHighBytes", HexByteData.class, HexByteData.class);
            getIdFromLowHiByte.setAccessible(true);

            HexByteData hexByteDataLo = new HexByteData(4, low, HexByteType.INDEX_LO);
            printBinary(hexByteDataLo.getByteValue());

            HexByteData hexByteDataHi = new HexByteData(4, high, HexByteType.INDEX_HI);
            printBinary(hexByteDataHi.getByteValue());

            int result = (int) getIdFromLowHiByte.invoke(deviceDTOService,
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

            Method getIdFromLowHiByte = deviceDTOService.getClass()
                  .getDeclaredMethod("getDateTimeFromBytes", List.class);
            getIdFromLowHiByte.setAccessible(true);
            getIdFromLowHiByte.invoke(deviceDTOService, dateTimeRaw);
      }

      @Test
      void convertByteToCharArrTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData temperatureHi = new HexByteData(8, "0x87", HexByteType.TE_HI);
            Method convertByteToStringBinaryRepresentation = deviceDTOService.getClass()
                  .getDeclaredMethod("convertByteToStringBinaryRepresentation", byte.class);
            convertByteToStringBinaryRepresentation.setAccessible(true);

            String result = (String) convertByteToStringBinaryRepresentation.invoke(deviceDTOService,
                  temperatureHi.getByteValue());
            System.out.println(result);

      }

      @Test
      void getTemperatureFromWholeAndFractionalPartTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData wholePart = new HexByteData(4, "0x17", HexByteType.INDEX_LO);
            HexByteData fractionalPart = new HexByteData(4, "0x2D", HexByteType.INDEX_HI);

            Method getTemperatureFromWholeAndFractionalPart = deviceDTOService.getClass()
                  .getDeclaredMethod("getTemperatureFromWholeAndFractionalPart", HexByteData.class, HexByteData.class);
            getTemperatureFromWholeAndFractionalPart.setAccessible(true);
            getTemperatureFromWholeAndFractionalPart.invoke(deviceDTOService, wholePart, fractionalPart);
      }

      @Test
      void getStateFromByteTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData state = new HexByteData(4, "0x03", HexByteType.STATE);

            Method getStateFromByte = deviceDTOService.getClass()
                  .getDeclaredMethod("getStateFromByte", HexByteData.class);
            getStateFromByte.setAccessible(true);

            int result = (int) getStateFromByte.invoke(deviceDTOService, state);
            Assertions.assertEquals(11, result);
      }

      @Test
      void getStateUserMarkFromByteTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData state = new HexByteData(4, "0x04", HexByteType.STATE);

            Method getUserStateFromByte = deviceDTOService.getClass()
                  .getDeclaredMethod("getStateUserMarkFromByte", HexByteData.class);
            getUserStateFromByte.setAccessible(true);

            int result = (int) getUserStateFromByte.invoke(deviceDTOService, state);
            Assertions.assertEquals(1, result);
      }

      @Test
      void getSerialNumberFromBytesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            HexByteData id0 = new HexByteData(0, "0x41", HexByteType.SERIAL_ID_B0);
            HexByteData id1 = new HexByteData(1, "0x42", HexByteType.SERIAL_ID_B1);
            HexByteData id2 = new HexByteData(2, "0x43", HexByteType.SERIAL_ID_B2);
            HexByteData id3 = new HexByteData(3, "0x44", HexByteType.SERIAL_ID_B3);
            HexByteData id4 = new HexByteData(4, "0x45", HexByteType.SERIAL_ID_B4);
            HexByteData id5 = new HexByteData(5, "0x46", HexByteType.SERIAL_ID_B5);
            HexByteData id6 = new HexByteData(6, "0x47", HexByteType.SERIAL_ID_B6);
            HexByteData id7 = new HexByteData(7, "0x48", HexByteType.SERIAL_ID_B7);

            Method getSerialNumberFromBytes = deviceDTOService.getClass()
                  .getDeclaredMethod("getSerialNumberFromBytes",
                        HexByteData.class, HexByteData.class, HexByteData.class, HexByteData.class,
                        HexByteData.class, HexByteData.class, HexByteData.class, HexByteData.class);
            getSerialNumberFromBytes.setAccessible(true);

            String result = (String)getSerialNumberFromBytes.invoke(deviceDTOService, id0, id1, id2, id3, id4, id5, id6, id7);

            Assertions.assertEquals("ABCDEFGH", result);
      }

      @Test
      void convertDeviceTypeRawToDeviceTypeDto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            List<HexByteData> deviceTypeResponse = new ArrayList<>();
            deviceTypeResponse.add(new HexByteData(0, "0x02", HexByteType.STX));
            deviceTypeResponse.add(new HexByteData(1, "0x12", HexByteType.LEN_LO));
            deviceTypeResponse.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            deviceTypeResponse.add(new HexByteData(3, "0x31", HexByteType.CMD));
            deviceTypeResponse.add(new HexByteData(4, "0x02", HexByteType.DEVICE_TYPE));
            deviceTypeResponse.add(new HexByteData(5, "0x41", HexByteType.SERIAL_ID_B0));
            deviceTypeResponse.add(new HexByteData(6, "0x41", HexByteType.SERIAL_ID_B1));
            deviceTypeResponse.add(new HexByteData(7, "0x30", HexByteType.SERIAL_ID_B2));
            deviceTypeResponse.add(new HexByteData(8, "0x30", HexByteType.SERIAL_ID_B3));
            deviceTypeResponse.add(new HexByteData(9, "0x30", HexByteType.SERIAL_ID_B4));
            deviceTypeResponse.add(new HexByteData(10, "0x30", HexByteType.SERIAL_ID_B5));
            deviceTypeResponse.add(new HexByteData(11, "0x30", HexByteType.SERIAL_ID_B6));
            deviceTypeResponse.add(new HexByteData(12, "0x31", HexByteType.SERIAL_ID_B7));
            deviceTypeResponse.add(new HexByteData(13, "0x01", HexByteType.HW_VERSION));
            deviceTypeResponse.add(new HexByteData(14, "0x00", HexByteType.SW_VERSION_LO));
            deviceTypeResponse.add(new HexByteData(14, "0x01", HexByteType.SW_VERSION_HI));
            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(deviceTypeResponse);
            deviceTypeResponse.add(new HexByteData(15, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            deviceTypeResponse.add(new HexByteData(16, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            DeviceTypeDto deviceTypeDtoExpected = new DeviceTypeDto(
                  2, "AA000001", 1, 0, 1
            );

            Method convertDeviceTypeRawToDeviceTypeDto = deviceDTOService.getClass()
                  .getDeclaredMethod("convertDeviceTypeRawToDeviceTypeDto",
                        List.class);
            convertDeviceTypeRawToDeviceTypeDto.setAccessible(true);

            DeviceTypeDto deviceTypeDtoResult = (DeviceTypeDto)convertDeviceTypeRawToDeviceTypeDto.invoke(deviceDTOService, deviceTypeResponse);

            Assertions.assertEquals(deviceTypeDtoExpected, deviceTypeDtoResult);
      }

      @Test
      void convertStateDtoRawToStateDtoTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            List<HexByteData> stateResponse = new ArrayList<>();
            stateResponse.add(new HexByteData(0, "0x02", HexByteType.STX));
            stateResponse.add(new HexByteData(1, "0x0A", HexByteType.LEN_LO));
            stateResponse.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            stateResponse.add(new HexByteData(3, "0x03", HexByteType.CMD));
            stateResponse.add(new HexByteData(4, "0x00", HexByteType.ERROR_CODE));
            stateResponse.add(new HexByteData(5, "0x87", HexByteType.TE_HI));
            stateResponse.add(new HexByteData(6, "0x11", HexByteType.TE_LO));
            stateResponse.add(new HexByteData(7, "0x01", HexByteType.BATTERY));
            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(stateResponse);
            stateResponse.add(new HexByteData(8, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            stateResponse.add(new HexByteData(9, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            StateDto stateDtoExpected = new StateDto(0, "-7.17", 1);

            Method convertStateDtoRawToStateDto = deviceDTOService.getClass()
                  .getDeclaredMethod("convertStateDtoRawToStateDto",
                        List.class);
            convertStateDtoRawToStateDto.setAccessible(true);

            StateDto stateDtoResult = (StateDto)convertStateDtoRawToStateDto.invoke(deviceDTOService, stateResponse);

            Assertions.assertEquals(stateDtoExpected, stateDtoResult);
      }

      @Test
      void convertProtocolVersionRawToProtocolVersionDtoTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            List<HexByteData> protocolVersionResponse =  new ArrayList<>();
            protocolVersionResponse.add(new HexByteData(0, "0x02", HexByteType.STX));
            protocolVersionResponse.add(new HexByteData(1, "0x08", HexByteType.LEN_LO));
            protocolVersionResponse.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            protocolVersionResponse.add(new HexByteData(3, "0x01", HexByteType.CMD));
            protocolVersionResponse.add(new HexByteData(4, "0x00", HexByteType.VERSION_LOW));
            protocolVersionResponse.add(new HexByteData(5, "0x01", HexByteType.VERSION_HIGH));
            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(protocolVersionResponse);
            protocolVersionResponse.add(new HexByteData(6, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            protocolVersionResponse.add(new HexByteData(7, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            ProtocolVersionDto expected = new ProtocolVersionDto(0, 1);

            Method convertProtocolVersionRawToProtocolVersionDto = deviceDTOService.getClass()
                  .getDeclaredMethod("convertProtocolVersionRawToProtocolVersionDto",
                        List.class);
            convertProtocolVersionRawToProtocolVersionDto.setAccessible(true);

            ProtocolVersionDto result = (ProtocolVersionDto)convertProtocolVersionRawToProtocolVersionDto
                  .invoke(deviceDTOService, protocolVersionResponse);

            Assertions.assertEquals(expected, result);
      }

      @Test
      void getDeviceDtoFromComportTest() {
            SerialPort portByName = comPortService.findSerialPortByName("COM2");

            DeviceDto deviceDtoFromComport = deviceDTOService
                  .getDeviceDtoFromComport(portByName, 19200, 8, 1, 2);

            System.out.println(deviceDtoFromComport);

      }

      private void printBinary(byte num) {
            int aux = Byte.toUnsignedInt(num);
            String binary = String.format("%8s", Integer.toBinaryString(aux)).replace(' ', '0');
            System.out.println(binary);
      }

}
