package hu.petrik.etlap.controllers;


import hu.petrik.etlap.Etlap;
import hu.petrik.etlap.db.Etlapdb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class EtlapController {

    @FXML
    private TableView<Etlap> dbTabla;
    @FXML
    private TableColumn<Etlap, String> nevOszlop;
    @FXML
    private TableColumn<Etlap, String> kategoriaOszlop;
    @FXML
    private TableColumn<Etlap, Integer> arOszlop;
    @FXML
    private TextArea leiras;

    private Etlapdb db;

    public EtlapController() {
        nevOszlop.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaOszlop.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arOszlop.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new Etlapdb();
            List<Etlap> menusor = db.getEtlap();

            for (Etlap elem: menusor) {
                dbTabla.getItems().add(elem);
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void etelHozzaadasButton(ActionEvent actionEvent) {
    }

    public void etelTorlesButton(ActionEvent actionEvent) {
    }

    public void szazalekkalEmeles(ActionEvent actionEvent) {
    }

    public void osszegelEmeles(ActionEvent actionEvent) {
    }

    public void etelAdat (MouseEvent mouseEvent) {
        Etlap ennivalo = dbTabla.getSelectionModel().getSelectedItem();
        leiras.setText(ennivalo.getDescription());
    }
}