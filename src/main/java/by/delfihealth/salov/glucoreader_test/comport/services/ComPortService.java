package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDto;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteType;
import com.fazecast.jSerialComm.SerialPort;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ComPortService {

      @Autowired
      private ControlSumCRC16Service controlSumCRC16Service;

      public List<SerialPortDto> findAllSerialPortsDtoWithoutData() {
            List<SerialPort> allComPorts = findAllComPorts();
            List<SerialPortDto> serialPortDTOList = IntStream.range(0, allComPorts.size())
                  .mapToObj(i -> new SerialPortDto(i,
                        allComPorts.get(i).getSystemPortName(),
                        allComPorts.get(i).getPortDescription()
                  ))
                  .collect(Collectors.toList());
            return serialPortDTOList;
      }
      /*

       */
      public List<HexByteData> getProtocolVersion(SerialPort serialPort, int baudRate, int dataBits,
                                                  int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> protocolVersionRequest = new ArrayList<>();
            protocolVersionRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            protocolVersionRequest.add(new HexByteData(1, "0x06", HexByteType.LEN_LO));
            protocolVersionRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            protocolVersionRequest.add(new HexByteData(3, "0x01", HexByteType.CMD));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(protocolVersionRequest);

            protocolVersionRequest.add(new HexByteData(4, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            protocolVersionRequest.add(new HexByteData(5, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(protocolVersionRequest));

            List<HexByteData> protocolVersionResponse = convertToGetProtocolVersion(
                  comPortRead(serialPort, 8, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return protocolVersionResponse;
      }

      public List<HexByteData> getDeviceType(SerialPort serialPort, int baudRate, int dataBits,
                                             int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> getDeviceTypeRequest = new ArrayList<>();
            getDeviceTypeRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            getDeviceTypeRequest.add(new HexByteData(1, "0x06", HexByteType.LEN_LO));
            getDeviceTypeRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            getDeviceTypeRequest.add(new HexByteData(3, "0x02", HexByteType.CMD));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(getDeviceTypeRequest);

            getDeviceTypeRequest.add(new HexByteData(4, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            getDeviceTypeRequest.add(new HexByteData(5, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(getDeviceTypeRequest));

            List<HexByteData> deviceTypeResponse = convertToGetDeviceType(
                  comPortRead(serialPort, 18, 15, 150)
            );

            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return deviceTypeResponse;
      }

      public List<HexByteData> getState(SerialPort serialPort, int baudRate, int dataBits,
                                        int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> getStateRequest = new ArrayList<>();
            getStateRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            getStateRequest.add(new HexByteData(1, "0x06", HexByteType.LEN_LO));
            getStateRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            getStateRequest.add(new HexByteData(3, "0x03", HexByteType.CMD));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(getStateRequest);

            getStateRequest.add(new HexByteData(4, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            getStateRequest.add(new HexByteData(5, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(getStateRequest));

            List<HexByteData> getStateResponse = convertToGetState(
                  comPortRead(serialPort, 10, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return getStateResponse;
      }

      public List<HexByteData> getDateTime(SerialPort serialPort, int baudRate, int dataBits,
                                           int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */

            List<HexByteData> getDateRequest = new ArrayList<>();
            getDateRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            getDateRequest.add(new HexByteData(1, "0x06", HexByteType.LEN_LO));
            getDateRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            getDateRequest.add(new HexByteData(3, "0x04", HexByteType.CMD));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(getDateRequest);

            getDateRequest.add(new HexByteData(4, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            getDateRequest.add(new HexByteData(5, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(getDateRequest));
            List<HexByteData> getDateTimeResponse = convertToGetDateTime(
                  comPortRead(serialPort, 12, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return getDateTimeResponse;
      }

      public List<HexByteData> getConverterType(SerialPort serialPort, int baudRate, int dataBits,
                                                int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> getConverterTypeRequest = new ArrayList<>();
            getConverterTypeRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            getConverterTypeRequest.add(new HexByteData(1, "0x06", HexByteType.LEN_LO));
            getConverterTypeRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            getConverterTypeRequest.add(new HexByteData(3, "0x31", HexByteType.CMD));
            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(getConverterTypeRequest);
            getConverterTypeRequest.add(new HexByteData(4, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            getConverterTypeRequest.add(new HexByteData(5, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(getConverterTypeRequest));
            List<HexByteData> getConverterTypeResponse = convertToGetConverterType(
                  comPortRead(serialPort, 18, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return getConverterTypeResponse;
      }

      public List<HexByteData> getValues(SerialPort serialPort, int baudRate, int dataBits,
                                         int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> getValuesRequest = new ArrayList<>();
            getValuesRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            getValuesRequest.add(new HexByteData(1, "0x0A", HexByteType.LEN_LO));
            getValuesRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            getValuesRequest.add(new HexByteData(3, "0x05", HexByteType.CMD));
            getValuesRequest.add(new HexByteData(4, "0x00", HexByteType.START_LO));
            getValuesRequest.add(new HexByteData(5, "0x00", HexByteType.START_HI));
            getValuesRequest.add(new HexByteData(6, "0x3F", HexByteType.STOP_LO));
            getValuesRequest.add(new HexByteData(7, "0x00", HexByteType.STOP_HI));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(getValuesRequest);

            getValuesRequest.add(new HexByteData(8, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            getValuesRequest.add(new HexByteData(9, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(getValuesRequest));

            List<HexByteData> getValuesResponse = convertRequestToGetValues(
                  comPortRead(serialPort, 966, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return getValuesResponse;
      }

      public List<HexByteData> setCurrentDateTime(SerialPort serialPort, int baudRate, int dataBits,
                                                  int stopBits, int parity) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> setDateTimeRequest = new ArrayList<>();
            setDateTimeRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            setDateTimeRequest.add(new HexByteData(1, "0x0C", HexByteType.LEN_LO));
            setDateTimeRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            setDateTimeRequest.add(new HexByteData(3, "0x81", HexByteType.CMD));
            setDateTimeRequest.add(new HexByteData(4, Calendar.getInstance().get(Calendar.YEAR) - 2000, HexByteType.DATE_YEAR));
            setDateTimeRequest.add(new HexByteData(5, Calendar.getInstance().get(Calendar.MONTH) + 1, HexByteType.DATE_MONTH));
            setDateTimeRequest.add(new HexByteData(6, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), HexByteType.DATE_DAY));
            setDateTimeRequest.add(new HexByteData(7, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), HexByteType.TIME_HOUR));
            setDateTimeRequest.add(new HexByteData(8, Calendar.getInstance().get(Calendar.MINUTE), HexByteType.TIME_MINUTE));
            setDateTimeRequest.add(new HexByteData(9, Calendar.getInstance().get(Calendar.SECOND), HexByteType.TIME_SEC));

            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(setDateTimeRequest);

            setDateTimeRequest.add(new HexByteData(10, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            setDateTimeRequest.add(new HexByteData(11, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(setDateTimeRequest));

            List<HexByteData> getValuesResponse = convertRequestToSetDateTime(
                  comPortRead(serialPort, 966, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return getValuesResponse;
      }

      public List<HexByteData> setConverterType(SerialPort serialPort, int baudRate, int dataBits, int stopBits, int parity,
                                                String deviceType, String serialIdB0, String serialIdB1, String serialIdB2,
                                                String serialIdB3, String serialIdB4, String serialIdB5, String serialIdB6,
                                                String serialIdB7, String hwVersion, String swVersionLo, String swVersionHi) {
            openComPort(serialPort, baudRate,
                  dataBits, stopBits, parity);
            /**
             * -------------------------------------------------------------
             */
            List<HexByteData> setConverterTypeRequest = new ArrayList<>();
            setConverterTypeRequest.add(new HexByteData(0, "0x02", HexByteType.STX));
            setConverterTypeRequest.add(new HexByteData(1, "0x12", HexByteType.LEN_LO));
            setConverterTypeRequest.add(new HexByteData(2, "0x00", HexByteType.LEN_HI));
            setConverterTypeRequest.add(new HexByteData(3, "0xB1", HexByteType.CMD));
            setConverterTypeRequest.add(new HexByteData(4, deviceType, HexByteType.DEVICE_TYPE));
            setConverterTypeRequest.add(new HexByteData(5, serialIdB0, HexByteType.SERIAL_ID_B0));
            setConverterTypeRequest.add(new HexByteData(6, serialIdB1, HexByteType.SERIAL_ID_B1));
            setConverterTypeRequest.add(new HexByteData(7, serialIdB2, HexByteType.SERIAL_ID_B2));
            setConverterTypeRequest.add(new HexByteData(8, serialIdB3, HexByteType.SERIAL_ID_B3));
            setConverterTypeRequest.add(new HexByteData(9, serialIdB4, HexByteType.SERIAL_ID_B4));
            setConverterTypeRequest.add(new HexByteData(10, serialIdB5, HexByteType.SERIAL_ID_B5));
            setConverterTypeRequest.add(new HexByteData(11, serialIdB6, HexByteType.SERIAL_ID_B6));
            setConverterTypeRequest.add(new HexByteData(12, serialIdB7, HexByteType.SERIAL_ID_B7));
            setConverterTypeRequest.add(new HexByteData(13, hwVersion, HexByteType.HW_VERSION));
            setConverterTypeRequest.add(new HexByteData(14, swVersionLo, HexByteType.SW_VERSION_LO));
            setConverterTypeRequest.add(new HexByteData(15, swVersionHi, HexByteType.SW_VERSION_HI));
            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(setConverterTypeRequest);

            setConverterTypeRequest.add(new HexByteData(16, highLowByteOfSum.getValue(), HexByteType.CRC_LO));
            setConverterTypeRequest.add(new HexByteData(17, highLowByteOfSum.getKey(), HexByteType.CRC_HI));

            comPortWrite(serialPort, convertRequestToByteArr(setConverterTypeRequest));
            List<HexByteData> setConverterTypeResponse = convertRequestToSetConverterType(
                  comPortRead(serialPort, 7, 15, 150));
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return setConverterTypeResponse;
      }

      public SerialPort findSerialPortByName(String portSystemName) {
            SerialPort commPort = SerialPort.getCommPort(portSystemName);
            return commPort;
      }

      public boolean openComPort(SerialPort serialPort, int baudRate, int dataBits,
                                 int stopBits, int parity) {
            serialPort.openPort();
            serialPort.setComPortParameters(baudRate, dataBits,
                  stopBits, parity);
            boolean isOpen = serialPort.isOpen();
            return isOpen;
      }

      public boolean closeComport(SerialPort serialPort) {
            if (serialPort.isOpen())
                  return serialPort.closePort();
            return false;
      }

      public List<SerialPort> findAllComPortsByDescriptionStartWith(String descriptionStartWith) {
            SerialPort[] commPorts = SerialPort.getCommPorts();

            List<SerialPort> serialPorts = Arrays.stream(commPorts)
                  .filter(serialPort -> {
                        return serialPort.getPortDescription().startsWith(descriptionStartWith);
                  }).toList();
            return serialPorts;
      }

      private List<SerialPort> findAllComPorts() {
            SerialPort[] commPorts = SerialPort.getCommPorts();
            List<SerialPort> serialPorts = Arrays.asList(commPorts);
            return serialPorts;
      }

      private byte[] convertRequestToByteArr(List<HexByteData> dataList) {
            byte[] result = new byte[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                  result[i] = dataList.get(i).getByteValue();
            }
            return result;
      }

      private byte[] comPortRead(SerialPort serialPort, int responseLength, int minTimeout, int maxTimeout) {
            byte[] protocolVersionByteResponse = new byte[responseLength];
            for (int i = 0; i < maxTimeout; i += minTimeout) {
                  try {
                        Thread.sleep(minTimeout);
                  } catch (InterruptedException e) {
                        e.printStackTrace();
                  }
                  int wasRead = serialPort.readBytes(protocolVersionByteResponse, protocolVersionByteResponse.length);
                  if (wasRead > 0) {
                        break;
                  }
            }
            return protocolVersionByteResponse;
      }

      private boolean comPortWrite(SerialPort serialPort, byte[] byteRequest) {
            if (serialPort.isOpen()) {
                  int i = serialPort.writeBytes(byteRequest, byteRequest.length);
                  if (i >= 0) return true;
            }
            return false;
      }

      private List<HexByteData> convertToGetProtocolVersion(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.VERSION_LOW));
            dataList.add(new HexByteData(5, data[5], HexByteType.VERSION_HIGH));
            dataList.add(new HexByteData(6, data[6], HexByteType.CRC_LO));
            dataList.add(new HexByteData(7, data[7], HexByteType.CRC_HI));

            return dataList;
      }

      private List<HexByteData> convertToGetDeviceType(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.DEVICE_TYPE));
            dataList.add(new HexByteData(5, data[5], HexByteType.SERIAL_ID_B0));
            dataList.add(new HexByteData(6, data[6], HexByteType.SERIAL_ID_B1));
            dataList.add(new HexByteData(7, data[7], HexByteType.SERIAL_ID_B2));
            dataList.add(new HexByteData(8, data[8], HexByteType.SERIAL_ID_B3));
            dataList.add(new HexByteData(9, data[9], HexByteType.SERIAL_ID_B4));
            dataList.add(new HexByteData(10, data[10], HexByteType.SERIAL_ID_B5));
            dataList.add(new HexByteData(11, data[11], HexByteType.SERIAL_ID_B6));
            dataList.add(new HexByteData(12, data[12], HexByteType.SERIAL_ID_B7));
            dataList.add(new HexByteData(13, data[13], HexByteType.HW_VERSION));
            dataList.add(new HexByteData(14, data[14], HexByteType.SW_VERSION_LO));
            dataList.add(new HexByteData(15, data[15], HexByteType.SW_VERSION_HI));
            dataList.add(new HexByteData(16, data[16], HexByteType.CRC_LO));
            dataList.add(new HexByteData(17, data[17], HexByteType.CRC_HI));
            return dataList;
      }

      private List<HexByteData> convertToGetState(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.ERROR_CODE));
            dataList.add(new HexByteData(5, data[5], HexByteType.TE_HI));
            dataList.add(new HexByteData(6, data[6], HexByteType.TE_LO));
            dataList.add(new HexByteData(7, data[7], HexByteType.BATTERY));
            dataList.add(new HexByteData(8, data[8], HexByteType.CRC_LO));
            dataList.add(new HexByteData(9, data[9], HexByteType.CRC_HI));
            return dataList;
      }

      private List<HexByteData> convertToGetDateTime(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.DATE_YEAR));
            dataList.add(new HexByteData(5, data[5], HexByteType.DATE_MONTH));
            dataList.add(new HexByteData(6, data[6], HexByteType.DATE_DAY));
            dataList.add(new HexByteData(7, data[7], HexByteType.TIME_HOUR));
            dataList.add(new HexByteData(8, data[8], HexByteType.TIME_MINUTE));
            dataList.add(new HexByteData(9, data[9], HexByteType.TIME_SEC));
            dataList.add(new HexByteData(10, data[10], HexByteType.CRC_LO));
            dataList.add(new HexByteData(11, data[11], HexByteType.CRC_HI));
            return dataList;
      }

      private List<HexByteData> convertToGetConverterType(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.DEVICE_TYPE));
            dataList.add(new HexByteData(5, data[5], HexByteType.SERIAL_ID_B0));
            dataList.add(new HexByteData(6, data[6], HexByteType.SERIAL_ID_B1));
            dataList.add(new HexByteData(7, data[7], HexByteType.SERIAL_ID_B2));
            dataList.add(new HexByteData(8, data[8], HexByteType.SERIAL_ID_B3));
            dataList.add(new HexByteData(9, data[9], HexByteType.SERIAL_ID_B4));
            dataList.add(new HexByteData(10, data[10], HexByteType.SERIAL_ID_B5));
            dataList.add(new HexByteData(11, data[11], HexByteType.SERIAL_ID_B6));
            dataList.add(new HexByteData(12, data[12], HexByteType.SERIAL_ID_B7));
            dataList.add(new HexByteData(13, data[13], HexByteType.HW_VERSION));
            dataList.add(new HexByteData(14, data[14], HexByteType.SW_VERSION_LO));
            dataList.add(new HexByteData(15, data[15], HexByteType.SW_VERSION_HI));
            dataList.add(new HexByteData(16, data[16], HexByteType.CRC_LO));
            dataList.add(new HexByteData(17, data[17], HexByteType.CRC_HI));
            return dataList;
      }

      private List<HexByteData> convertRequestToGetValues(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            for (int i = 4; i < 964; i += 15) {
                  dataList.add(new HexByteData(i, data[i], HexByteType.INDEX_LO));
                  dataList.add(new HexByteData(i + 1, data[i + 1], HexByteType.INDEX_HI));
                  dataList.add(new HexByteData(i + 3, data[i + 2], HexByteType.DATE_YEAR));
                  dataList.add(new HexByteData(i + 4, data[i + 3], HexByteType.DATE_MONTH));
                  dataList.add(new HexByteData(i + 5, data[i + 4], HexByteType.DATE_DAY));
                  dataList.add(new HexByteData(i + 6, data[i + 5], HexByteType.TIME_HOUR));
                  dataList.add(new HexByteData(i + 7, data[i + 6], HexByteType.TIME_MINUTE));
                  dataList.add(new HexByteData(i + 8, data[i + 7], HexByteType.TIME_SEC));
                  dataList.add(new HexByteData(i + 9, data[i + 8], HexByteType.GLUCOSE_HI));
                  dataList.add(new HexByteData(i + 10, data[i + 9], HexByteType.GLUCOSE_LO));
                  dataList.add(new HexByteData(i + 11, data[i + 10], HexByteType.TE_HI));
                  dataList.add(new HexByteData(i + 12, data[i + 11], HexByteType.TE_LO));
                  dataList.add(new HexByteData(i + 13, data[i + 12], HexByteType.HEMATOCRIT_HI));
                  dataList.add(new HexByteData(i + 14, data[i + 13], HexByteType.HEMATOCRIT_LO));
                  dataList.add(new HexByteData(i + 15, data[i + 14], HexByteType.STATE));
            }
            dataList.add(new HexByteData(964, data[964], HexByteType.CRC_LO));
            dataList.add(new HexByteData(965, data[965], HexByteType.CRC_HI));

            return dataList;
      }

      private List<HexByteData> convertRequestToSetDateTime(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.ERROR_CODE));
            dataList.add(new HexByteData(5, data[5], HexByteType.CRC_LO));
            dataList.add(new HexByteData(6, data[6], HexByteType.CRC_HI));
            return dataList;
      }

      private List<HexByteData> convertRequestToSetConverterType(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();
            dataList.add(new HexByteData(0, data[0], HexByteType.STX));
            dataList.add(new HexByteData(1, data[1], HexByteType.LEN_LO));
            dataList.add(new HexByteData(2, data[2], HexByteType.LEN_HI));
            dataList.add(new HexByteData(3, data[3], HexByteType.CMD));
            dataList.add(new HexByteData(4, data[4], HexByteType.ERROR_CODE));
            dataList.add(new HexByteData(5, data[5], HexByteType.CRC_LO));
            dataList.add(new HexByteData(6, data[6], HexByteType.CRC_HI));
            return dataList;
      }
}
