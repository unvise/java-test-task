package com.unvise.transportbook.javafx;

import com.unvise.transportbook.TransportBookApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class FxApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private ConfigurableApplicationContext ctx;

    @Override
    public void init() {

        LOGGER.debug("Creating Spring context...");
        ctx = new SpringApplicationBuilder(TransportBookApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        ctx.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        LOGGER.debug("Closing Spring context...");
        ctx.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {

        public StageReadyEvent(Object source) {
            super(source);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }

}