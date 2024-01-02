package catalogoLibri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField firstDateTfd;

    @FXML
    private TextField secondDateTfd;

    @FXML
    private TextField limitTfd;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button updateButton;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem saveButton;

    @FXML
    private TableView<Libro> tableView;

    @FXML
    private TableColumn<Libro, String> ISBNColumn;

    @FXML
    private TableColumn<Libro, String> codVolumeColumn;

    @FXML
    private TableColumn<Libro, String> titleColumn;

    @FXML
    private TableColumn<Libro, Integer> yearColumn;

    @FXML
    private TableColumn<Libro, Float> priceColumn;

    @FXML
    private TableColumn<Libro, Integer> weightColumn;

    @FXML
    private TableColumn<Libro, Integer> pagesColumn;

    @FXML
    private ProgressIndicator progInd;

    private ObservableList<Libro> list;

    private CaricaCatalogoService service;

    @FXML
    void updateEvent(ActionEvent event) {
        try {
            service = new CaricaCatalogoService(
                    new URL("http://193.205.163.165/oopdata/Cat_Zani_ext.csv"), comboBox.getValue(),
                    Integer.parseInt(firstDateTfd.getText()), Integer.parseInt(secondDateTfd.getText()),
                    Integer.parseInt(limitTfd.getText()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        progInd.visibleProperty().bind(service.runningProperty());
        progInd.progressProperty().bind(service.progressProperty());
        tableView.itemsProperty().bind(service.valueProperty());

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                firstDateTfd.clear();
                secondDateTfd.clear();
                limitTfd.clear();
                list.clear();
                list.addAll(service.getValue());
            }
        });
    }

    @FXML
    void comboEvent(ActionEvent event) {
        System.out.println(comboBox.getValue());
    }

    @FXML
    void saveEvent(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File file = fc.showSaveDialog(contextMenu);
        ObservableList<Libro> saveList = FXCollections.observableArrayList();

        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.append("ISBN;TITOLO;ANNO;PREZZO\n");
            saveList = tableView.getSelectionModel().getSelectedItems();
            for (Libro l : saveList) {
                br.append(l.getIsbn() + ";");
                br.append(l.getTitolo() + ";");
                br.append(l.getAnno().toString() + ";");
                br.append(l.getPrezzo().toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll("0-Scolastico", "1-Dizionari", "2-Vario", "3-Giuridico",
                "4-Universitario");

        list = FXCollections.observableArrayList();
        tableView.setItems(list);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        codVolumeColumn.setCellValueFactory(new PropertyValueFactory<>("codVol"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("anno"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pagine"));

        progInd.setVisible(false);
        saveButton.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
        apriFile();
    }

    private void apriFile() {
        try (Scanner sc = new Scanner(new BufferedReader(
                new InputStreamReader(new URL("http://193.205.163.165/oopdata/Cat_Zani_ext.csv").openStream())))) {
            sc.useDelimiter("[;\n]");
            sc.useLocale(Locale.US);

            if (sc.hasNext())
                sc.nextLine();

            while (sc.hasNext()) {
                String tipoVol = sc.next();
                String gred = sc.next();
                String isbn = sc.next();
                String codVol = sc.next();
                String titolo = sc.next();
                int anno = sc.nextInt();
                float prezzo = sc.nextFloat();
                int peso = sc.nextInt();
                int pagine = sc.nextInt();

                Libro libro = new Libro(tipoVol, gred, isbn, codVol, titolo, anno, prezzo, peso, pagine);
                list.add(libro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
