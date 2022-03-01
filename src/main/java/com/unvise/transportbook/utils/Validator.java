package com.unvise.transportbook.utils;

import com.unvise.transportbook.entity.Transport;
import com.unvise.transportbook.repository.TransportRepository;

import java.util.Arrays;

public class Validator {

    public static boolean validateNoEmpty(String value) {
        return !value.isEmpty();
    }

    public static boolean validateCategory(String value) {
        return Arrays.toString(Transport.Category.values()).contains(value);
    }

    public static boolean validateLicensePlate(String value, TransportRepository transportRepository) {
        return transportRepository.findAllByLicensePlate(value).isEmpty()
                && Constants.LICENSE_PLATE_PATTERN_RU.matcher(value.trim()).matches();
    }

    public static boolean validateType(String value) {
        return Arrays.toString(Transport.Type.values()).contains(value);
    }

    public static boolean validateDate(String value) {
        return Constants.DATE_PATTERN.matcher(value.trim()).matches();
    }

    public static boolean validateTrailer(String string) {
        return string.trim().equalsIgnoreCase("true")
                || string.trim().equalsIgnoreCase("false");
    }

    public static boolean validateTrailerAvailability(String string) {
        return string.trim().equalsIgnoreCase("yes")
                || string.trim().equalsIgnoreCase("no");
    }

}
