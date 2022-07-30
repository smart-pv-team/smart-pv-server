package server.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Dates {
  public static final ZoneId WARSAW_ZONE_ID = ZoneId.of("Europe/Warsaw");

  private Dates() {
  }

  public static LocalDateTime dateToLocalDateTime(Date date){
    return date == null ? null : date.toInstant().atZone(WARSAW_ZONE_ID).toLocalDateTime();
  }
}
