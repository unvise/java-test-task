package com.unvise.transportbook.javafx.controller;

import com.unvise.transportbook.entity.Transport;
import com.unvise.transportbook.repository.TransportRepository;
import com.unvise.transportbook.utils.Constants;
import com.unvise.transportbook.utils.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class EditWindowController extends AbstractWindowController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditWindowController.class);

    @Value("${application.ui.add-window.alert.header-text}")
    private String alertHeaderText;

    @Value("${application.ui.add-window.alert.content-text.license-plate}")
    private String licensePlateAlertHeaderText;

    @Value("${application.ui.add-window.alert.content-text.prod-year}")
    private String prodYearAlertHeaderText;

    @Value("${application.ui.add-window.alert.content-text.successfully-add}")
    private String successfullyAddText;

    @FXML
    public Text brandText;

    @FXML
    public TextField brandTextField;

    @FXML
    public Text modelText;

    @FXML
    public TextField modelTextField;

    @FXML
    public Text categoryText;

    @FXML
    public ChoiceBox<Transport.Category> categoryChoiceBox;

    @FXML
    public Text licensePlateText;

    @FXML
    public TextField licensePlateTextField;

    @FXML
    public Text typeText;

    @FXML
    public ChoiceBox<Transport.Type> typeChoiceBox;

    @FXML
    public Text prodYearText;

    @FXML
    public TextField prodYearTextField;

    @FXML
    public Text trailerText;

    @FXML
    public TextField withTrailerTextField;

    @FXML
    public Button saveButton;

    private final TransportRepository transportRepository;

    public EditWindowController(ApplicationContext applicationContext, TransportRepository transportRepository) {
        super(applicationContext);
        this.transportRepository = transportRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.setItems(categoryObservableList);
        categoryChoiceBox.setValue(categoryObservableList.get(0));

        typeChoiceBox.setItems(typeObservableList);
        typeChoiceBox.setValue(typeObservableList.get(0));

        saveButton.setOnAction(this::handleSaveButton);
    }

    // Cant throw exception because all parameters have been validated before use
    // TODO: Needs refactoring later
    @SneakyThrows
    public void handleSaveButton(ActionEvent actionEvent) {
        Map<String, String> incorrectParamsMap = new TreeMap<>();

        if (!Validator.validateNoEmpty(brandTextField.getText())) {
            incorrectParamsMap.put("Brand", "");
        }
        if (!Validator.validateNoEmpty(modelTextField.getText())) {
            incorrectParamsMap.put("Model", "");
        }
        if (!Validator.validateNoEmpty(categoryChoiceBox.getValue().getCategory())) {
            incorrectParamsMap.put("Category", categoryChoiceBox.getValue().getCategory());
        }
        if (!Validator.validateNoEmpty(licensePlateTextField.getText())
                || !Validator.validateLicensePlate(licensePlateTextField.getText(), transportRepository)) {
            incorrectParamsMap.put("License Plate", licensePlateTextField.getText());
        }
        if (!Validator.validateType(typeChoiceBox.getValue().getType())) {
            incorrectParamsMap.put("Type", typeChoiceBox.getValue().getType());
        }
        if (!Validator.validateDate(prodYearTextField.getText())) {
            incorrectParamsMap.put("Prod Year", prodYearTextField.getText());
        }
        if (!Validator.validateTrailerAvailability(withTrailerTextField.getText())) {
            incorrectParamsMap.put("Trailer", withTrailerTextField.getText());
        }
        if (!incorrectParamsMap.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(alertHeaderText + " " + incorrectParamsMap.keySet());
            if (incorrectParamsMap.size() == 1 && incorrectParamsMap.containsKey("License Plate")) {
                alert.setContentText(licensePlateAlertHeaderText);
            } else if (incorrectParamsMap.size() == 1 && incorrectParamsMap.containsKey("Prod Year")) {
                alert.setContentText(prodYearAlertHeaderText + Constants.EX_DATE_FORMAT);
            }
            LOGGER.info("Showing alert about incorrect values in this window...");
            alert.show();
        } else {
            transportRepository.save(new Transport(
                    brandTextField.getText().trim().toUpperCase(),
                    modelTextField.getText().trim().toUpperCase(),
                    categoryChoiceBox.getValue().getCategory().toUpperCase(),
                    licensePlateTextField.getText().trim().toUpperCase(),
                    typeChoiceBox.getValue().getType().toUpperCase(),
                    prodYearTextField.getText().trim().toUpperCase(),
                    withTrailerTextField.getText().trim().toUpperCase())
            );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(successfullyAddText);

            alert.show();
        }
    }

}
