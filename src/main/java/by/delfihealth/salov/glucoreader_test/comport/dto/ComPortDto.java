package by.delfihealth.salov.glucoreader_test.comport.dto;

import lombok.Data;

@Data
public class ComPortDto implements Comparable<ComPortDto> {
      private final String name;
      private final String description;

      @Override
      public int compareTo(ComPortDto other) {
            if (!this.name.equalsIgnoreCase(other.name))
                  return this.name.compareTo(other.name);
            if (!this.description.equalsIgnoreCase(other.description))
                  return this.description.compareTo(other.description);
            return (this.name.length() + this.description.length()) - (other.name.length() + other.description.length());
      }
}
