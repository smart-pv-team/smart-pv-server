package server.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class Dates {

  public static final ZoneId WARSAW_ZONE_ID = ZoneId.of("Europe/Warsaw");
  public static final ZoneId REFERENCE_ZONE_ID = ZoneId.of("Z");

  private Dates() {
  }

  public static LocalDateTime dateToLocalDateTime(Date date) {
    return date == null ? null : date.toInstant().atZone(WARSAW_ZONE_ID).toLocalDateTime();
  }

  public static Date localDateTimeToDate(LocalDateTime date) {
    return Date.from(date.atZone(WARSAW_ZONE_ID).toInstant());
  }

  public static Date endOfDay(Date date) {
    return localDateTimeToDate(LocalDateTime.now().with(LocalTime.MAX));
  }

}
