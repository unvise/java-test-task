package com.unvise.transportbook;

import com.unvise.transportbook.javafx.FxApplication;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TransportBookApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportBookApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting the application...");
        Application.launch(FxApplication.class, args);
        LOGGER.info("App is closed");
    }

}
