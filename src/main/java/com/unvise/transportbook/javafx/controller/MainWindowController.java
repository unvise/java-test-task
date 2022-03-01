package com.unvise.transportbook.javafx.controller;


import com.unvise.transportbook.entity.Transport;
import com.unvise.transportbook.repository.TransportRepository;
import com.unvise.transportbook.repository.specification.SearchCriteria;
import com.unvise.transportbook.repository.specification.SearchOperation;
import com.unvise.transportbook.repository.specification.TransportSpecification;
import com.unvise.transportbook.repository.specification.Transport_;
import com.unvise.transportbook.utils.Constants;
import com.unvise.transportbook.utils.Parser;
import com.unvise.transportbook.utils.Validator;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MainWindowController extends AbstractWindowController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowController.class);

    @Value("classpath:/fxml/EditWindow.fxml")
    private Resource fxml;

    @Value("${application.ui.add-window.title}")
    private String secondWindowTitle;

    @FXML
    private TableView<Transport> transportTable;

    @FXML
    private TableColumn<Transport, String> brandCol;

    @FXML
    private TableColumn<Transport, String> modelCol;

    @FXML
    private TableColumn<Transport, String> transportCategoryCol;

    @FXML
    private TableColumn<Transport, String> licensePlateCol;

    @FXML
    private TableColumn<Transport, Integer> transportTypeCol;

    @FXML
    private TableColumn<Transport, String> prodYearCol;

    @FXML
    private TableColumn<Transport, String> withTrailerCol;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField licensePlateTextField;

    @FXML
    private TextField prodYearTextField;

    @FXML
    private TextField withTrailerTextField;

    @FXML
    private ChoiceBox<Transport.Category> categoryChoiceBox;

    @FXML
    private Button clearCategoryChoiceBoxButton;

    @FXML
    private ChoiceBox<Transport.Type> typeChoiceBox;

    @FXML
    private Button clearTypeChoiceBoxButton;

    @FXML
    private Button findByButton;

    @FXML
    private Button addAndUpdateButton;

    @FXML
    private Button refreshButton;

    private ObservableList<Transport> transportObservableList;

    private final TransportRepository transportRepository;

    public MainWindowController(ApplicationContext applicationContext, TransportRepository transportRepository) {
        super(applicationContext);
        this.transportRepository = transportRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColumns();


        transportObservableList = FXCollections.observableList(transportRepository.findAllTransport());
        transportTable.setItems(transportObservableList);

        categoryChoiceBox.setItems(categoryObservableList);
        typeChoiceBox.setItems(typeObservableList);

        clearCategoryChoiceBoxButton.setOnAction(actionEvent -> handleClearChoiceBox(categoryChoiceBox));
        clearTypeChoiceBoxButton.setOnAction(actionEvent -> handleClearChoiceBox(typeChoiceBox));
        findByButton.setOnAction(this::handleFindByButton);
        addAndUpdateButton.setOnAction(this::handleAddAndUpdateButton);
        refreshButton.setOnAction(this::handleResetButton);
    }

    public void initTableColumns() {
        brandCol.setCellValueFactory(new PropertyValueFactory<>(Transport_.BRAND));
        modelCol.setCellValueFactory(new PropertyValueFactory<>(Transport_.MODEL));
        transportCategoryCol.setCellValueFactory(new PropertyValueFactory<>(Transport_.CATEGORY));
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>(Transport_.LICENSE_PLATE));
        transportTypeCol.setCellValueFactory(new PropertyValueFactory<>(Transport_.TYPE));
        prodYearCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getSimpleFormatProdYear()));
        withTrailerCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getWithTrailerAsString().toUpperCase()));
    }

    public void refreshTransportTable(List<Transport> values) {
        transportObservableList = FXCollections.observableList(values);
        transportTable.setItems(transportObservableList);
    }

    public void handleFindByButton(ActionEvent actionEvent) {

        TransportSpecification spec = new TransportSpecification();

        if (Validator.validateNoEmpty(brandTextField.getText())) {
            spec.add(new SearchCriteria(Transport_.BRAND, brandTextField.getText().trim().toUpperCase(), SearchOperation.MATCH));
        }
        if (Validator.validateNoEmpty(modelTextField.getText())) {
            spec.add(new SearchCriteria(Transport_.MODEL, modelTextField.getText().trim().toUpperCase(), SearchOperation.MATCH));
        }
        if (Validator.validateNoEmpty(licensePlateTextField.getText())
                || Validator.validateLicensePlate(licensePlateTextField.getText(), transportRepository)) {
            spec.add(new SearchCriteria(Transport_.LICENSE_PLATE, licensePlateTextField.getText().trim().toUpperCase(), SearchOperation.MATCH));
        }
        if (Validator.validateTrailerAvailability(withTrailerTextField.getText())) {
            spec.add(new SearchCriteria(Transport_.WITH_TRAILER, Parser.parseByAvailability(withTrailerTextField.getText()), SearchOperation.MATCH));
        }
        try {
            if (Validator.validateCategory(categoryChoiceBox.getValue().getCategory())) {
                spec.add(new SearchCriteria(Transport_.CATEGORY, categoryChoiceBox.getValue(), SearchOperation.EQUAL));
            }
        } catch (NullPointerException e) {
            LOGGER.warn("Category ChoiceBox in main window is null");
        }
        try {
            if (Validator.validateType(typeChoiceBox.getValue().getType())) {
                spec.add(new SearchCriteria(Transport_.TYPE, typeChoiceBox.getValue(), SearchOperation.EQUAL));
            }
        } catch (NullPointerException e) {
            LOGGER.warn("Type ChoiceBox in main window is null");
        }
        try {
            if (Validator.validateDate(prodYearTextField.getText())) {
                spec.add(new SearchCriteria(Transport_.PROD_YEAR,
                        new SimpleDateFormat(Constants.DATE_FORMAT_STR)
                                .parse(prodYearTextField.getText().trim()), SearchOperation.MATCH));
            }
        } catch (ParseException e) {
            LOGGER.warn("Can't parse a date from prodYearTextField in main window");
        }
        if (!spec.getList().isEmpty()) {
            refreshTransportTable(transportRepository.findAll(spec));
        }
    }

    public <T extends ChoiceBox<?>> void handleClearChoiceBox(T choiceBox) {
        choiceBox.setValue(null);
    }

    public void handleResetButton(ActionEvent actionEvent) {
        refreshTransportTable(transportRepository.findAllTransport());
    }

    public void handleAddAndUpdateButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxml.getURL());
            fxmlLoader.setControllerFactory(super.getApplicationContext()::getBean);

            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMinHeight(root.minHeight(-1));
            stage.setMinWidth(root.minWidth(-1));
            stage.setMaxHeight(root.maxHeight(-1));
            stage.setMaxWidth(root.maxWidth(-1));
            stage.setTitle(secondWindowTitle);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTransportTable(transportRepository.findAllTransport());
        } catch (IOException | IllegalStateException e) {
            LOGGER.error("Fatal error while opening fxml for EditWindow. Exiting application...", e);
            Platform.exit();
        }
    }

}