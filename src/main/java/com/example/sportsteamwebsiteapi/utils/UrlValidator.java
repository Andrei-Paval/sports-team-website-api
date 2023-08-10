package com.example.sportsteamwebsiteapi.utils;

import java.net.URL;

public class UrlValidator {
    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
