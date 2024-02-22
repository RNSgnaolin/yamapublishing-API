package com.yama.publishing.service.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class DateTimeService {

    private DateTimeService() {

    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

    public static LocalDate format(String data) {
        return LocalDate.parse(data, formatter);
    }
    
}
