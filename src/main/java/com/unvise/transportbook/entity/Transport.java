package com.unvise.transportbook.entity;

import com.unvise.transportbook.utils.Constants;
import com.unvise.transportbook.utils.Parser;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transport_id_seq")
    @SequenceGenerator(name = "transport_id_seq", sequenceName = "transport_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "prod_year", nullable = false)
    private Date prodYear;

    @Column(name = "with_trailer", nullable = false)
    private Boolean withTrailer;

    public Transport() {
    }

    public Transport(
            String brand,
            String model,
            Category category,
            String licensePlate,
            Type type,
            Date prodYear,
            Boolean withTrailer
    ) {
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.licensePlate = licensePlate;
        this.type = type;
        this.prodYear = prodYear;
        this.withTrailer = withTrailer;
    }

    public Transport(
            String brand,
            String model,
            String category,
            String licensePlate,
            String type,
            String prodYear,
            String withTrailer
    ) throws IllegalStateException {
        try {
            this.brand = brand;
            this.model = model;
            this.category = Category.valueOf(category);
            this.licensePlate = licensePlate;
            this.type = Type.valueOf(type);
            this.prodYear = new SimpleDateFormat(Constants.DATE_FORMAT_STR).parse(prodYear);
            this.withTrailer = Parser.parseByAvailability(withTrailer);
        } catch (ParseException e) {
            throw new IllegalStateException();
        }
    }

    public String getSimpleFormatProdYear() {
        return new SimpleDateFormat(Constants.DATE_FORMAT_STR).format(getProdYear());
    }

    public String getWithTrailerAsString() {
        return getWithTrailer() ? "yes" : "no";
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", transportCategory=" + category +
                ", licensePlate='" + licensePlate + '\'' +
                ", transportType=" + type +
                ", prodYear=" + prodYear +
                ", withTrailer=" + withTrailer +
                '}';
    }

    @Getter
    public enum Category {

        A("A"), A1("A1"),
        B("B"), BE("BE"), B1("B1"),
        C("C1"), CE("CE"), C1("C1E"), C1E("C1E"),
        D("D"), DE("DE"), D1("D1"), D1E("D1E"),
        TM("TM"),
        TB("TB"),
        M("M");

        private final String category;

        Category(String value) {
            this.category = value;
        }
    }

    @Getter
    public enum Type {
        PASSENGER("PASSENGER"), TRUCK("TRUCK"), BUS("BUS");

        private final String type;

        Type(String value) {
            this.type = value;
        }
    }

}