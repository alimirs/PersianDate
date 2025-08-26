import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Additional utility methods for Persian date operations
 */
public class PersianDateUtils {

    private PersianDateUtils() {
        // Utility class - prevent instantiation
    }

    // Date comparison methods
    public static boolean isAfter(PersianDate date1, PersianDate date2) {
        return date1.toGregorian().isAfter(date2.toGregorian());
    }

    public static boolean isBefore(PersianDate date1, PersianDate date2) {
        return date1.toGregorian().isBefore(date2.toGregorian());
    }

    public static boolean isEqual(PersianDate date1, PersianDate date2) {
        return date1.toGregorian().isEqual(date2.toGregorian());
    }

    // Date difference calculations
    public static long daysBetween(PersianDate start, PersianDate end) {
        return java.time.Duration.between(start.toGregorian(), end.toGregorian()).toDays();
    }

    public static long monthsBetween(PersianDate start, PersianDate end) {
        return (end.getYear() - start.getYear()) * 12 + (end.getMonth() - start.getMonth());
    }

    public static long yearsBetween(PersianDate start, PersianDate end) {
        return end.getYear() - start.getYear();
    }

    // Holiday checking
    public static boolean isHoliday(PersianDate date) {
        int dayOfWeek = date.getDayOfWeek();
        // Friday is holiday in Iran
        if (dayOfWeek == 6) { // 6 = Friday
            return true;
        }

        // Check for Iranian official holidays
        int month = date.getMonth();
        int day = date.getDay();

        // Nowruz (Persian New Year) - 1st to 4th of Farvardin
        if (month == 1 && day <= 4) {
            return true;
        }

        // Other major Iranian holidays
        return (month == 1 && day == 12) || // Nature Day
               (month == 1 && day == 13) || // Sizdah Bedar
               (month == 3 && day == 14) || // Khordad Uprising
               (month == 11 && day == 22) || // Islamic Revolution Day
               (month == 12 && day == 29); // Oil Nationalization Day
    }

    // Business day calculation
    public static PersianDate addBusinessDays(PersianDate startDate, int businessDays) {
        PersianDate result = startDate;
        int direction = businessDays > 0 ? 1 : -1;
        int daysToAdd = Math.abs(businessDays);

        while (daysToAdd > 0) {
            result = result.addDays(direction);
            if (!isHoliday(result) && result.getDayOfWeek() != 6) { // Not holiday and not Friday
                daysToAdd--;
            }
        }

        return result;
    }

    // Season detection
    public static String getSeason(PersianDate date) {
        int month = date.getMonth();
        if (month >= 1 && month <= 3) {
            return "بهار";
        } else if (month >= 4 && month <= 6) {
            return "تابستان";
        } else if (month >= 7 && month <= 9) {
            return "پاییز";
        } else {
            return "زمستان";
        }
    }

    // Age calculation
    public static int calculateAge(PersianDate birthDate) {
        PersianDate now = PersianDate.now();
        int age = now.getYear() - birthDate.getYear();
        
        if (now.getMonth() < birthDate.getMonth() || 
            (now.getMonth() == birthDate.getMonth() && now.getDay() < birthDate.getDay())) {
            age--;
        }
        
        return age;
    }

    // Date range generation
    public static List<PersianDate> getDateRange(PersianDate start, PersianDate end) {
        List<PersianDate> dates = new ArrayList<>();
        PersianDate current = start;
        
        while (!PersianDateUtils.isAfter(current, end)) {
            dates.add(current);
            current = current.addDays(1);
        }
        
        return dates;
    }

    // First/last day of month
    public static PersianDate getFirstDayOfMonth(PersianDate date) {
        return new PersianDate(date.getYear(), date.getMonth(), 1);
    }

    public static PersianDate getLastDayOfMonth(PersianDate date) {
        int daysInMonth = date.getDaysInMonth();
        return new PersianDate(date.getYear(), date.getMonth(), daysInMonth);
    }

    // Persian number conversion
    public static String toPersianNumbers(String input) {
        return input.replace('0', '۰')
                   .replace('1', '۱')
                   .replace('2', '۲')
                   .replace('3', '۳')
                   .replace('4', '۴')
                   .replace('5', '۵')
                   .replace('6', '۶')
                   .replace('7', '۷')
                   .replace('8', '۸')
                   .replace('9', '۹');
    }

    public static String toEnglishNumbers(String input) {
        return input.replace('۰', '0')
                   .replace('۱', '1')
                   .replace('۲', '2')
                   .replace('۳', '3')
                   .replace('۴', '4')
                   .replace('۵', '5')
                   .replace('۶', '6')
                   .replace('۷', '7')
                   .replace('۸', '8')
                   .replace('۹', '9');
    }
}
