package com.unvise.transportbook.javafx.controller;

import com.unvise.transportbook.entity.Transport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public abstract class AbstractWindowController {

    private final ApplicationContext applicationContext;

    protected final ObservableList<Transport.Category> categoryObservableList = FXCollections.observableList(
            Arrays.asList(Transport.Category.values()));

    protected final ObservableList<Transport.Type> typeObservableList = FXCollections.observableList(
            Arrays.asList(Transport.Type.values()));

    public AbstractWindowController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
