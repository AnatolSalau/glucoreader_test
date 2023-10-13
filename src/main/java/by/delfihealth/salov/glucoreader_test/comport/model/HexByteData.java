package by.delfihealth.salov.glucoreader_test.comport.model;

import lombok.EqualsAndHashCode;

import java.util.HexFormat;

@EqualsAndHashCode
public class HexByteData {
      private final int number;
      private final String value;
      private final HexByteType type;

      public HexByteData(int number, String value, HexByteType type) {
            this.number = number;
            this.value = value;
            this.type = type;
      }

      public HexByteData (int number, byte dataByte, HexByteType type) {
            this.number = number;
            this.value = getHexStrFrom(dataByte);
            this.type = type;
      }

      public HexByteData (int number, int dataInt, HexByteType type) {
            this.number = number;
            this.value = getHexStrFrom(dataInt);
            this.type = type;
      }

      public HexByteData (int number, char[] dataChars, HexByteType type) {
            this.number = number;
            this.value = getHexStrFrom(dataChars);
            this.type = type;
      }

      public byte getByteValue() {
            String substringValue = value.substring(2);
            byte[] bytes = HexFormat.of().parseHex(substringValue);
            byte resultByte = bytes[0];
            return resultByte;
      }

      public static byte getByteFrom(String hexByteStr) {
            String substringValue = hexByteStr.substring(2);
            byte[] bytes = HexFormat.of().parseHex(substringValue);
            byte resultByte = bytes[0];
            return resultByte;
      }

      public static String getHexStrFrom(byte data) {
            String string = Integer.toHexString(data);
            if (string.length() == 1) {
                  return "0x" + "0" + Integer.toHexString(data).toUpperCase();
            }
            if (string.startsWith("f")) {
                  return "0x" + Integer.toHexString(data)
                        .substring(string.length()-2)
                        .toUpperCase();
            }
            return "0x" + Integer.toHexString(data).toUpperCase();
      }

      public static String getHexStrFrom(int data) {
            String string = Integer.toHexString(data);
            if (string.length() == 1) {
                  return "0x" + "0" + Integer.toHexString(data).toUpperCase();
            }
            if (string.startsWith("f")) {
                  return "0x" + Integer.toHexString(data)
                        .substring(string.length()-2)
                        .toUpperCase();
            }
            return "0x" + Integer.toHexString(data).toUpperCase();
      }

      public static String getHexStrFrom(char[] chars) {
            byte parseByte = Byte.parseByte(new String(chars));
            String hexStr = getHexStrFrom(parseByte);
            return hexStr;
      }

      @Override
      public String toString() {
            return "\n HexByteData{" +
                  "\'number='" + number + '\'' +
                  ", value='" + value  +
                  "\', type=" + "\'" + type + "\'" +
                  "}";
      }
}
