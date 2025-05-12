package GUI.giaodienadmin.QuanLyDeThi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;

public class ExamDialogUtils {
    public static LocalTime parseDuration(String durationStr) {
        if (durationStr == null || durationStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Duration cannot be empty. Please provide a valid time in HH:mm:ss or H:mm format.");
        }
        try {
            DateTimeFormatter strictFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return LocalTime.parse(durationStr, strictFormatter);
        } catch (Exception e1) {
            try {
                DateTimeFormatter flexibleFormatter = DateTimeFormatter.ofPattern("[H][:mm][:ss]");
                return LocalTime.parse(durationStr, flexibleFormatter);
            } catch (Exception e2) {
                throw new IllegalArgumentException("Invalid time format. Please use HH:mm:ss or H:mm.");
            }
        }
    }

    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().isEmpty()) {
                throw new ParseException("Date input cannot be empty. Please use the format: " + datePattern, 0);
            }
            try {
                return dateFormatter.parse(text);
            } catch (ParseException ex) {
                throw new ParseException("Invalid date format. Please use " + datePattern, ex.getErrorOffset());
            }
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                } else if (value instanceof Date) {
                    return dateFormatter.format(value);
                }
            }
            return "";
        }
    }
}