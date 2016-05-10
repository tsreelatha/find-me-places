package com.places.GUI;

import com.places.application.FindMePlacesService;
import com.places.domain.Place;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Lazy
@Component
public class SearchResult extends GridPane {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FindMePlacesService findMePlacesService;

    private final ObservableList<Place> data =
            FXCollections.observableArrayList();

    private final TableView<Place> table = new TableView<>();

    Alert alert;


    public SearchResult() {

        table.setMinHeight(750);
        table.setMinWidth(900);
        TableColumn firstNameCol = new TableColumn("Place Name");
        firstNameCol.setMinWidth(200);

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn lastNameCol = new TableColumn("vicinity");
        lastNameCol.setMinWidth(250);
        lastNameCol.setResizable(true);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("vicinity"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(350);
        typeCol.setResizable(true);
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("types"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, typeCol);

        getChildren().add(table);
    }

    public void findNearbyPlaces(String latitude, String longitude) {
        try {
            Optional<List<Place>> places = findMePlacesService.getPlaces(Double.parseDouble(latitude), Double.parseDouble(longitude));
            if (places.isPresent()) {
                System.out.print(Arrays.toString(places.get().toArray()));
                table.getItems().clear();
                places.get().forEach(data::add);
            } else {
                table.getItems().clear();
                table.setPlaceholder(new Label("No nearby places found. "));
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No nearby places found. ");
                alert.show();

            }
        }catch(Exception e){
            logger.error("Got exception while getting nearby places,{}", e.getMessage());
            table.getItems().clear();
            table.setPlaceholder(new Label("No nearby places found. "));
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No nearby places found. ");
            alert.show();
        }

    }
}