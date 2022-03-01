package com.unvise.transportbook.utils;

import com.unvise.transportbook.entity.Transport;
import com.unvise.transportbook.repository.TransportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ValidatorTest {

    @Autowired
    TransportRepository transportRepository;

    @Test
    void validateNoEmptyTest() {
        String[] arrThatReturnTrue = {" 1", "asd@#%A!", " ", "爱情"};
        String[] arrThatReturnFalse = {""};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateNoEmpty(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateNoEmpty(v)));
    }

    @Test
    void validateCategoryTest() {
        String[] arrThatReturnTrue = Arrays.stream(Transport.Category.values())
                .map(Transport.Category::getCategory)
                .toArray(String[]::new);

        String[] arrThatReturnFalse = {"AE", "A1E", "B1E", "TM1", "TB1", "M1"};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateCategory(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateCategory(v)));

    }

    @Test
    void validateLicensePlate() {
        String[] arrThatReturnTrue = {"A205XT_58RUS", "H020MH_23RUS", "A925OA_12RUS", "C125XX_49RUS"};
        String[] arrThatReturnFalse = {"A205HT_58RUS", "H00VK_23RUS", "A925Oa_62RUS", "C123XX_49E", "C123XX_49RUSA"};

        System.out.println(transportRepository.findAllTransport());

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateLicensePlate(v, transportRepository)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateLicensePlate(v, transportRepository)));
    }

    @Test
    void validateType() {
        String[] arrThatReturnTrue = Arrays.stream(Transport.Type.values())
                .map(Transport.Type::getType)
                .toArray(String[]::new);;

        String[] arrThatReturnFalse = {"CARGO"};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateType(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateType(v)));

    }

    @Test
    void validateDate() {
        String[] arrThatReturnTrue = {"2002-12-02", "2301-07-15", "2022-10-03"};
        String[] arrThatReturnFalse = {"123-12-03", "3123-02-29", "2012-11-3", "2012-1-3", "2012-O1-03", "2012-01-3 "};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateDate(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateDate(v)));
    }

    @Test
    void validateTrailer() {
        String[] arrThatReturnTrue = {"true ", "True", "tRue", "  True ", "false", "False", "falSe"};
        String[] arrThatReturnFalse = {"Yes", "No", "12", "0", "1", ""};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateTrailer(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateTrailer(v)));
    }

    @Test
    void validateTrailerAvailability() {
        String[] arrThatReturnTrue = {"Yes", "No", "yes ", "nO", "   no "};
        String[] arrThatReturnFalse = {"true ", "True", "tRue", "0", "1", "faLse"};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Validator.validateTrailerAvailability(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Validator.validateTrailerAvailability(v)));
    }
}