package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;
import hu.petrik.etlap.Etlapdb;
import hu.petrik.etlap.Kategoria;
import hu.petrik.etlap.Kategoriadb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EtlapController extends Controller {

    @FXML
    public Spinner<Integer> forintEmelesSpinner;
    @FXML
    public Spinner<Integer> szazalekEmelesSpinner;
    @FXML
    public ChoiceBox<String> szuresChoiceBox;
    @FXML
    private TableView<Etlap> dbTabla;
    @FXML
    private TableColumn<Etlap, Integer> arOszlop;
    @FXML
    private TableColumn<Etlap, String> nevOszlop;
    @FXML
    private TableColumn<Etlap, String> kategoriaOszlop;
    @FXML
    private TextArea elemLeirasArea;
    @FXML
    public TableColumn<Kategoria, String> kategoriaTableCol;
    @FXML
    public TableView<Kategoria> kategoriaTableView;

    private Etlapdb db;
    private Kategoriadb kdb;
    private List<Kategoria> kategoriaLista;

    public void initialize() {
        szuresChoiceBox.getItems().add("összes");
        try {
            kdb = new Kategoriadb();
            kategoriaLista = kdb.getKategoria();
            for (Kategoria k : kategoriaLista) {
                szuresChoiceBox.getItems().add(k.getNev());
            }
        } catch (SQLException e) {
            kiir(e);
        }

        db = new Etlapdb();
        kdb = new Kategoriadb();

        nevOszlop.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaOszlop.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arOszlop.setCellValueFactory(new PropertyValueFactory<>("ar"));

        kategoriaTableCol.setCellValueFactory(new PropertyValueFactory<>("nev"));

        etlapListaUjratolt();
        kategoriaListaUjratolt();
        szures();
    }

    @FXML
    public void torlesButton(ActionEvent actionEvent) {
        int selectedIndex = dbTabla.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }

        if (!confirm("Biztosan törölni szeretné az ételt?")) {
            return;
        }

        Etlap torlendoEtel = dbTabla.getSelectionModel().getSelectedItem();
        try {
            db.deleteEtel(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapListaUjratolt();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void etelFelvetelButton(ActionEvent actionEvent) {
        try {
            Controller felvetel = valtas("hozzaad-view.fxml", "Étel hozzáadása", 300, 400);
            felvetel.getStage().setOnCloseRequest(event -> etlapListaUjratolt());
            felvetel.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void szazalekEmelesButton(ActionEvent actionEvent) {
        int emeles = szazalekEmelesSpinner.getValue();

        int selectedIndex = dbTabla.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = dbTabla.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos emelni szeretné az összes étel árát " + emeles + "%-kal?")) {
                return;
            }
            try {
                db.emelSzazalekOsszes(emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos emelni szeretné " + emeles + "%-kal az étel árát?")) {
                return;
            }
            try {
                db.emelSzazalek(emelesEtel.getId(), emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void forintEmelesButton(ActionEvent actionEvent) {

        int emeles = forintEmelesSpinner.getValue();

        int selectedIndex = dbTabla.getSelectionModel().getSelectedIndex();
        Etlap emelEtel = dbTabla.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos emelni szeretné az összes étel árát " + emeles + " forinttal?")) {
                return;
            }
            try {
                db.emelForintOsszes(emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos emelni szeretné " + emeles + " forinttal az étel árát?")) {
                return;
            }
            try {
                db.emelForint(emelEtel.getId(), emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void etelClick(MouseEvent mouseEvent) {
        Etlap etel = dbTabla.getSelectionModel().getSelectedItem();
        elemLeirasArea.setText(etel.getDescription());
    }

    private void etlapListaUjratolt() {
        try {
            List<Etlap> etlapLista = db.getEtlap();
            dbTabla.getItems().clear();

            for (Etlap e : etlapLista) {
                dbTabla.getItems().add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void kategoriaListaUjratolt() {
        try {
            List<Kategoria> kategoriaLista = kdb.getKategoria();
            kategoriaTableView.getItems().clear();

            for (Kategoria k : kategoriaLista) {
                kategoriaTableView.getItems().add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kategoriaHozzaadasButton(ActionEvent actionEvent) {
        try {
            Controller kategoriaHozzadas = valtas("kategoria-hozzaad-view.fxml", "Kategória hozzáadása", 250, 150);
            kategoriaHozzadas.getStage().setOnCloseRequest(event -> kategoriaListaUjratolt());
            kategoriaHozzadas.getStage().show();
        } catch (Exception e) {
            kiir(e);
        }
    }

    public void kategoriaTorlesButton(ActionEvent actionEvent) {
        int selectedIndex = kategoriaTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez válasszon ki egy elemet a táblázatból!");
            return;
        }
        Kategoria torlendoKategoria = kategoriaTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan törölni szeretnéd a kategóriát?")) {
            return;
        }
        try {
            kdb.kategoriaTorles(torlendoKategoria.getId());
            alert("Sikeres törlés!");
            kategoriaListaUjratolt();
        } catch (SQLException e) {
            kiir(e);
        }
    }

    public void szures() {
        szuresChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            try {
                if (newValue.equals("összes")) {
                    etlapListaUjratolt();
                } else {
                    List<Etlap> szurtEtlapLista = db.getSzurtEtlap(newValue);
                    dbTabla.getItems().clear();
                    for (Etlap etlap : szurtEtlapLista) {
                        dbTabla.getItems().add(etlap);
                    }
                }
            } catch (SQLException e) {
                kiir(e);
            }
        });
    }
}