package com.places.GUI;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class SearchForm extends GridPane {

    @Autowired
    SearchResult searchResult;

    TextField latitudeFld;
    TextField longitudeFld;
    final Text actionTarget;
    Label latitudeLbl;
    Label longitudeLbl;

    public SearchForm() {

        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(column1, column2);

        longitudeLbl = new Label("Longitude: ");
        longitudeFld = new TextField();

        latitudeLbl = new Label("Latitude: ");

        latitudeFld = new TextField();
        Button findNearbyButt = new Button("Find nearby places");


        actionTarget = new Text();
        add(actionTarget, 1, 6);

        setHalignment(latitudeLbl, HPos.RIGHT);
        add(latitudeLbl, 0, 0);

        setHalignment(longitudeLbl, HPos.RIGHT);
        add(longitudeLbl, 0, 1);

        setHalignment(latitudeFld, HPos.LEFT);
        add(latitudeFld, 1, 0);

        setHalignment(longitudeFld, HPos.LEFT);
        add(longitudeFld, 1, 1);

        setHalignment(findNearbyButt, HPos.RIGHT);
        add(findNearbyButt, 1, 2);


         findNearbyButt.setOnAction(e -> {
            if (isInputValid())
                searchResult.findNearbyPlaces(latitudeFld.getText() , longitudeFld.getText());
            else
            {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Enter input in numeric");
            }
        });

    }

    private boolean isInputValid() {
        actionTarget.setText("");
        return !(longitudeFld.getText() == null) && !(longitudeFld.getText().length() == 0) && !(latitudeFld.getText() == null) &&
                !(latitudeFld.getText().length() == 0) && longitudeFld.getText().matches("-?\\d+(\\.\\d+)?") && latitudeFld.getText().matches("-?\\d+(\\.\\d+)?");

    }
}