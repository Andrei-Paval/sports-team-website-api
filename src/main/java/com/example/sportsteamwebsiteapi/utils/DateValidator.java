package com.example.sportsteamwebsiteapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator {
    public static boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}