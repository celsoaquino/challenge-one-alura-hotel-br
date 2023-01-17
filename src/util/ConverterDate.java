package util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class ConverterDate {
	
     public static final String PATTERN_DATE = "dd-MM-yyyy";
	
	 public static LocalDate dateToLocalDate(Date dateToConverter) {
		return LocalDate.ofInstant(dateToConverter.toInstant(), ZoneId.systemDefault());
	}	
}

