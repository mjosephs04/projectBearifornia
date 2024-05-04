package springboot;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParsing {

    //takes either a zonedDateTime format string or a yyyy-mm-dd format string
    //and returns a LocalDate
    public static LocalDate convertStringToDate(String date){
        LocalDate result = null;
        //first see if its yyyy-mm-dd format
        try {
            // Define the date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            result = LocalDate.parse(date, formatter);
        }
        //if that doesn't work, see if its zonedDateTime format
        catch(DateTimeParseException e) {
            try{
                result = ZonedDateTime.parse(date).toLocalDate();
            }catch(DateTimeParseException x){
                return null;
            }
        }

        return result;
    }
}
