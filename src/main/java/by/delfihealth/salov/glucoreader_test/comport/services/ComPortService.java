package by.delfihealth.salov.glucoreader_test.comport.services;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteType;
import com.fazecast.jSerialComm.SerialPort;
import javafx.util.Pair;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter

@Service
public class ComPortService {
      
      private List<SerialPort> serialPorts;
      private SerialPort currentCommPort;

      public ComPortService() {
            this.serialPorts = findAllComPorts();
      }

      public boolean openComPort(String portSystemName, int baudRate, int dataBits,
                                 int stopBits, int parity) {
            this.currentCommPort = SerialPort.getCommPort(portSystemName);
            this.currentCommPort.openPort();
            this.currentCommPort.setComPortParameters(baudRate,dataBits,
                  stopBits,parity);
            boolean isOpen = currentCommPort.isOpen();
            return isOpen;
      }

      public List<HexByteData> getProtocolVersion() {
            List<HexByteData> getProtocolVersionDateRequest = new ArrayList<>();

            getProtocolVersionDateRequest.add(new HexByteData(0 ,"0x02" , HexByteType.STX));
            getProtocolVersionDateRequest.add(new HexByteData(1, "0x06" , HexByteType.LEN_LO));
            getProtocolVersionDateRequest.add(new HexByteData(2, "0x00" , HexByteType.LEN_HI));
            getProtocolVersionDateRequest.add(new HexByteData(3, "0x01" , HexByteType.CMD));

            ControlSumCRC16Service controlSumCRC16Service = new ControlSumCRC16Service();
            Pair<String, String> highLowByteOfSum = controlSumCRC16Service
                  .getHighLowByteOfSum(getProtocolVersionDateRequest);

            getProtocolVersionDateRequest.add(new HexByteData(4, highLowByteOfSum.getValue() , HexByteType.CRC_LO));
            getProtocolVersionDateRequest.add(new HexByteData(5, highLowByteOfSum.getKey() , HexByteType.CRC_HI));

            byte[]requestGetProtocolVersion = convertRequestToByteArr(getProtocolVersionDateRequest);

            if (currentCommPort.isOpen()) {
                  currentCommPort.writeBytes(requestGetProtocolVersion,getProtocolVersionDateRequest.size());
            }

            byte[] responseGetProtocolVersion = new byte[8];

            for (int i = 0; i < 10; i++) {
                  try {
                        TimeUnit.MILLISECONDS.sleep(15);
                  } catch (InterruptedException e) {
                        e.printStackTrace();
                  }
                  int wasRead = currentCommPort.readBytes(responseGetProtocolVersion, responseGetProtocolVersion.length);
                  if (wasRead > 0 ) {
                        break;
                  }
            }
            List<HexByteData> getProtocolVersionDateResponse = convertByteArrToGetProtocolVersion(responseGetProtocolVersion);
            return getProtocolVersionDateResponse;
      }

      public boolean closeComport() {
            if (currentCommPort.isOpen())
                  return currentCommPort.closePort();
            return false;
      }

      public List<SerialPortDTO> findAllSerialPortsDTO() {
            SerialPort[] commPorts = SerialPort.getCommPorts();
            List<SerialPortDTO> serialPortDTOS = Arrays
                  .stream(commPorts)
                  .map((serialPort) -> new SerialPortDTO(
                              convertSerialPortNameToID(serialPort.getSystemPortName()),
                        serialPort.getSystemPortName(),
                        serialPort.getPortDescription()
                        )
                  ).toList();
            return serialPortDTOS;
      }

      private List<SerialPort> findAllComPorts() {
            SerialPort[] commPorts = SerialPort.getCommPorts();
            List<SerialPort> serialPorts = Arrays.asList(commPorts);
            return serialPorts;
      }

      private byte [] convertRequestToByteArr(List<HexByteData> dataList) {
            byte[] result = new byte[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                  result[i] = dataList.get(i).getByteValue();
            }
            return result;
      }

      private List<HexByteData> convertByteArrToGetProtocolVersion(byte[] data) {
            List<HexByteData> dataList = new ArrayList<>();

            HexByteData dataStx = new HexByteData(0,data[0], HexByteType.STX);
            dataList.add(dataStx);
            HexByteData dataLenLo = new HexByteData(1, data[1], HexByteType.LEN_LO);
            dataList.add(dataLenLo);
            HexByteData dataLenHi = new HexByteData(2, data[2], HexByteType.LEN_HI);
            dataList.add(dataLenHi);
            HexByteData dataCmd = new HexByteData(3, data[3], HexByteType.CMD);
            dataList.add(dataCmd);
            HexByteData dataVersionLo = new HexByteData(4, data[4], HexByteType.VERSION_LOW);
            dataList.add(dataVersionLo);
            HexByteData dataVersionHi = new HexByteData(5, data[5], HexByteType.VERSION_HIGH);
            dataList.add(dataVersionHi);
            HexByteData dataCrcLo = new HexByteData(6, data[6], HexByteType.CRC_LO);
            dataList.add(dataCrcLo);
            HexByteData dataCrcHi = new HexByteData(7, data[7], HexByteType.CRC_HI);
            dataList.add(dataCrcHi);

            return dataList;
      }

      private Integer convertSerialPortNameToID(String systemPortName) {
            String substring = systemPortName
                  .substring(systemPortName.length() - 1, systemPortName.length());
            //System.out.println(substring);
            return Integer.parseInt(substring);
      }
}


