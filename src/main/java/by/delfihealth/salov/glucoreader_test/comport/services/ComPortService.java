package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteType;
import com.fazecast.jSerialComm.SerialPort;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ComPortService {

      @Autowired
      ControlSumCRC16Service controlSumCRC16Service;

      public List<SerialPortDTO> findAllSerialPortsDTO() {
            System.out.println("/--------------------------------------------------------");
            System.out.println("ComPortServiceFindAllPorts start");

            List<SerialPort> allComPorts = findAllComPorts();
            System.out.println(allComPorts);

            System.out.println("ComPortServiceFindAllPorts end");
            System.out.println("/--------------------------------------------------------");

            return null;
      }

      private List<SerialPort> findAllComPorts() {
            SerialPort[] commPorts = SerialPort.getCommPorts();
            List<SerialPort> serialPorts = Arrays.asList(commPorts);
            return serialPorts;
      }

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
            return  getDateTimeResponse;
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
            List<HexByteData> getConverterTypeResponse = convertToGetDeviceType(
                  comPortRead(serialPort, 18, 15, 150)
            );
            /**
             * -------------------------------------------------------------
             */
            closeComport(serialPort);
            return  getConverterTypeResponse;
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
                        TimeUnit.MILLISECONDS.sleep(minTimeout);
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
}
