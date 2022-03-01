package com.unvise.transportbook.repository.specification;

import com.unvise.transportbook.entity.Transport;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transport.class)
public class Transport_ {

    public static volatile SingularAttribute<Transport, Long> id;
    public static volatile SingularAttribute<Transport, String> brand;
    public static volatile SingularAttribute<Transport, String> model;
    public static volatile SingularAttribute<Transport, String> category;
    public static volatile SingularAttribute<Transport, String> licensePlate;
    public static volatile SingularAttribute<Transport, Transport.Type> type;
    public static volatile SingularAttribute<Transport, Date> prodYear;
    public static volatile SingularAttribute<Transport, Boolean> trailer;

    public static final String ID = "id";
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String CATEGORY = "category";
    public static final String LICENSE_PLATE = "licensePlate";
    public static final String TYPE = "type";
    public static final String PROD_YEAR= "prodYear";
    public static final String WITH_TRAILER = "withTrailer";

}
