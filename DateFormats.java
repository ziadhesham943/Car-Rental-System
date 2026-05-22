package oop.project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateFormats {

    private static final DateTimeFormatter RENT = DateTimeFormatter.ofPattern("d-M-yyyy");
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

    private DateFormats() {
    }

    public static String isoToRent(String isoDate) {
        LocalDate d = LocalDate.parse(isoDate.trim(), ISO);
        return d.format(RENT);
    }
}
