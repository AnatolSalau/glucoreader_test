package by.delfihealth.salov.glucoreader_test.comport;

public enum HexByteType {
      STX("Start of data packet"),
      LEN_LO("Packet length, low byte"),
      LEN_HI("Packet length, high byte"),
      CMD("Command code"),
      DATA("Something data"),
      VERSION_LOW("Protocol version low"),
      VERSION_HIGH("Protocol version high"),
      CRC_LO("Control sum, low byte"),
      CRC_HI("Control sum, high byte"),
      DEVICE_TYPE("Type of device"),
      SERIAL_ID_B0("ID of device, byte № 0"),
      SERIAL_ID_B1("ID of device, byte № 1"),
      SERIAL_ID_B2("ID of device, byte № 2"),
      SERIAL_ID_B3("ID of device, byte № 3"),
      SERIAL_ID_B4("ID of device, byte № 4"),
      SERIAL_ID_B5("ID of device, byte № 5"),
      SERIAL_ID_B6("ID of device, byte № 6"),
      SERIAL_ID_B7("ID of device, byte № 7"),
      HW_VERSION("Hardware version"),
      SW_VERSION_LO("Software version, low byte"),
      SW_VERSION_HI("Software version, high byte"),
      ERROR_CODE("Error code"),
      TE_LO("Temperature, low byte, fractional part"),
      TE_HI("Temperature, high byte, integer part"),
      BATTERY("Battery level"),
      DATE_YEAR("Date year"),
      DATE_MONTH("Date month"),
      DATE_DAY("Date day"),
      TIME_HOUR("Time hour"),
      TIME_MINUTE("Time minute"),
      TIME_SEC("Time second"),
      START_LO("Start index, low byte"),
      START_HI("Start index, high byte"),
      STOP_LO("Stop index, low byte"),
      STOP_HI("Stop index, high byte"),
      INDEX_LO("Index number, low byte"),
      INDEX_HI("Index number, high byte"),
      GLUCOSE_HI("Glucose value, high byte, integer part"),
      GLUCOSE_LO("Glucose value, low byte, fractional part"),
      HEMATOCRIT_HI("Glucose value, high byte, integer part"),
      HEMATOCRIT_LO("Glucose value, low byte, fractional part"),
      STATE("State")
      ;

      public final String textDescription;

      HexByteType(String textDescription) {
            this.textDescription = textDescription;
      }
}
