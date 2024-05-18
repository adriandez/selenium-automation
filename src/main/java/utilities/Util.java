package utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import static utilities.Constants.ONE_SECOND;

public class Util {

    private static final String[] LANGUAGES = {"English", "Spanish", "German", "Russian", "Chinese (Mandarin)", "French", "Italian"};
    private static final Random RANDOM = new Random();

    public static String getRandomLanguage(){
        int n = RANDOM.nextInt(LANGUAGES.length);
        return LANGUAGES[n];
    }

    public static int getRandomBetween(int min, int max){
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static String getDateLog() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return now.format(formatter);
    }

    public static String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public static int getMonthIndex(String month) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(month)) {
                return i;
            }
        }
        return -1;
    }

    public static String getCurrentMinutes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m");
        return LocalDate.now().format(formatter);
    }

    public static String getWeekToCheckString() {
        LocalDate currentDate = LocalDate.now();
        LocalDate endOfWeek = currentDate.plusDays(6);
        DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MMM d");
        DateTimeFormatter endFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        DateTimeFormatter endFormatterNoMonth = DateTimeFormatter.ofPattern("d, yyyy");
        String startOfWeekFormatted = currentDate.format(startFormatter);
        String endOfWeekFormatted = endOfWeek.format(endOfWeek.getMonth() == currentDate.getMonth() ? endFormatterNoMonth : endFormatter);
        return startOfWeekFormatted + " – " + endOfWeekFormatted;
    }

    public static String getModeratedStudyCalendarWeek() {
        LocalDate currentDate = LocalDate.now();
        LocalDate endOfWeek = currentDate.plusDays(6);
        boolean isEndOfWeekInNextMonth = endOfWeek.getMonthValue() > currentDate.getMonthValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String startOfWeekFormatted = currentDate.format(formatter);
        String endOfWeekFormatted = endOfWeek.format(formatter);
        String week = startOfWeekFormatted + " – " + endOfWeekFormatted;
        week += isEndOfWeekInNextMonth ? ".true" : ".false";
        return week;
    }

    public static String addDaysToDate(int num) {
        LocalDate futureDate = LocalDate.now().plusDays(num);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM.d");
        return futureDate.format(formatter);
    }

    public static String getDateNoFormat() {
        return LocalDate.now().toString();
    }

    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String cleanAndSplitString(String input) {
        String[] parts;
        if (input.contains("Proxy element")) {
            parts = input.split("'");
        } else {
            String poppedInput = input.substring(1, input.length() - 1);
            parts = poppedInput.split(">");
        }
        if (parts.length > 1) {
            return parts[1].trim();
        } else {
            return parts[0].trim();
        }
    }

    public static int getSpeechTime(String text) {
        int wordCount = text.split("\\s+").length;
        float timePerWord = 60f / 175f;
        return Math.round(timePerWord * 1000f * wordCount) + ONE_SECOND;
    }

    public static int getSpeechTime(String text, int extraTime) {
        int wordCount = text.split("\\s+").length;
        float timePerWord = 60f / 175f;
        return Math.round(timePerWord * 1000f * wordCount) + extraTime + ONE_SECOND;
    }
}
