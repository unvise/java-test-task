package com.unvise.transportbook.repository;

import com.unvise.transportbook.entity.Transport;
import com.unvise.transportbook.utils.Constants;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Needs refactoring later
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransportRepositoryTest {

    @Autowired
    private TransportRepository repository;

    private final List<Transport> transports = new ArrayList<>();

    @BeforeEach
    @Transactional
    void setup() {
        addInitialDataToTransportsList();
        repository.saveAll(transports);
    }


    @Test
    void findAllTransport() {
        List<Transport> transportsFromRepository = repository.findAllTransport();

        assertTrue(getDifferenceBetween(transportsFromRepository, transports).isEmpty());
    }

    @Test
    void findAllByBrand() {
        List<Transport> actual = repository.findAllByBrand("LADA");
        List<Transport> expected = List.of(
                transports.get(0), transports.get(5), transports.get(8), transports.get(9), transports.get(10));

        assertTrue(getDifferenceBetween(expected, actual).isEmpty());
    }

    @Test
    void findAllByModel() {
        List<Transport> actual = repository.findAllByModel("GRANTA");
        List<Transport> expected = List.of(
                transports.get(0), transports.get(8), transports.get(10));

        assertTrue(getDifferenceBetween(expected, actual).isEmpty());
    }

    @Test
    void findAllByCategory() {
        List<Transport> actual = repository.findAllByCategory(Transport.Category.C);
        List<Transport> expected = List.of(
                transports.get(1), transports.get(3), transports.get(7));

        assertTrue(getDifferenceBetween(expected, actual).isEmpty());
    }

    @Test
    void findAllByLicensePlate() {
        List<Transport> actual = repository.findAllByLicensePlate("K111TT_58RUS");
        List<Transport> expected = List.of(transports.get(2));

        assertTrue(getDifferenceBetween(expected, actual).isEmpty());
    }

    @Test
    void findAllByTransportType() {
        List<Transport> actual = repository.findAllByTransportType(Transport.Type.TRUCK);
        List<Transport> expected = List.of(transports.get(1), transports.get(9));

        assertTrue(getDifferenceBetween(expected, actual).isEmpty());
    }

    @Test
    void findAllByProdYear() throws ParseException {
        List<Transport> actual = repository.findAllByProdYear(new SimpleDateFormat(Constants.DATE_FORMAT_STR)
                .parse("2002-10-11"));
        List<Transport> expected = List.of(transports.get(9), transports.get(10));

        assertTrue(getDifferenceBetween(actual, expected).isEmpty());
    }

    @Test
    void findAllByTrailer() {
        List<Transport> expected = repository.findAllByTrailer(true);
        List<Transport> actual = List.of(transports.get(2), transports.get(6), transports.get(10));

        assertTrue(getDifferenceBetween(actual, expected).isEmpty());

    }

    private <T> List<T> getDifferenceBetween(List<T> list1, List<T> list2) {
        return list1.stream().filter(e -> !list2.contains(e)).toList();
    }

    @SneakyThrows
    private void addInitialDataToTransportsList() {
        transports.add(new Transport("LADA", "GRANTA", Transport.Category.A, "K111KK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2001-09-28"), false));
        transports.add(new Transport("BMW", "ADJ-1", Transport.Category.C, "T111KK_58RUS", Transport.Type.TRUCK, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-09-28"), false));
        transports.add(new Transport("AUDI", "TO", Transport.Category.A, "K111TT_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-09-28"), true));
        transports.add(new Transport("VOLVO", "XC90", Transport.Category.C, "K123XK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-02"), false));
        transports.add(new Transport("HYUNDAI", "VERNA", Transport.Category.A, "K183KK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-02"), false));
        transports.add(new Transport("LADA", "VESTA", Transport.Category.A1, "K171KK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-02"), false));
        transports.add(new Transport("MERCEDES", "GLC SUV", Transport.Category.B, "K813KK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-05"), true));
        transports.add(new Transport("MERCEDES", "GLB SUV", Transport.Category.C, "K138YT_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-05"), false));
        transports.add(new Transport("LADA", "GRANTA", Transport.Category.A, "K231KK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-05"), false));
        transports.add(new Transport("LADA", "KALINA", Transport.Category.D, "K319KK_58RUS", Transport.Type.TRUCK, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-11"), false));
        transports.add(new Transport("LADA", "GRANTA", Transport.Category.A, "K211KK_58RUS", Transport.Type.PASSENGER, new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse("2002-10-11"), true));
    }
}