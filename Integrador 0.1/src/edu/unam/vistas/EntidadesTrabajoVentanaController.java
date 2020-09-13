/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.modelo.EntidadTrabajo;
import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Ominuka Mauro
 */
public class EntidadesTrabajoVentanaController implements Initializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    Repositorio em = new Repositorio(emf);
    Servicio servicio = new Servicio(em);
    @FXML
    private TableView<EntidadTrabajo> tableEntidades;
    @FXML
    private TableColumn<EntidadTrabajo, String> columnCUIT;
    @FXML
    private TableColumn<EntidadTrabajo, String> columnNombre;
    @FXML
    private TableColumn<EntidadTrabajo, Boolean> columnPrivadaPublic;
    @FXML
    private Button buttonAtras;
    @FXML
    private Button buttonAgregar;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Button buttonEliminar;
    @FXML
    private Button buttonLimpiar;
    @FXML
    private TextField textFieldCUIT;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private ComboBox<Boolean> comboSector;
    private FXMLLoader cargador;
    private Scene scene;
    private Stage stage;

    private ObservableList<EntidadTrabajo> entidades = FXCollections.observableArrayList();
    private ObservableList<Boolean> sectores = FXCollections.observableArrayList();
    private String textoAlerta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSector();
        setColumnTables();
        addEntidadesTabla();
        this.buttonEliminar.disableProperty().bind(
                this.tableEntidades.getSelectionModel().selectedItemProperty().isNull());
        this.buttonActualizar.disableProperty().bind(
                this.tableEntidades.getSelectionModel().selectedItemProperty().isNull());
        this.buttonAgregar.disableProperty().bind(
                this.tableEntidades.getSelectionModel().selectedItemProperty().isNotNull());
        /*Esto causa una excepcion del tipo nullPointerException, ya que al tener un 
        item de la tabla seleccionado, se actualiza o elimina, entonces deja estar seleccionado.*/
        this.tableEntidades.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<EntidadTrabajo>() {
            @Override
            public void changed(ObservableValue<? extends EntidadTrabajo> ov, EntidadTrabajo valorPrevio,
                    EntidadTrabajo valorNuevo) {
                textFieldCUIT.setText(valorNuevo.getCUIT());
                textFieldNombre.setText(valorNuevo.getNombre());
                comboSector.setValue(valorNuevo.isIsPublica());
            }
        });
    }

    @FXML
    private void handleAtras(ActionEvent event) {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/InicioVentana.fxml"));
                InicioVentanaController controlador = cargador.getController();
                scene = new Scene(cargador.load());
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Conferencias");
                stage.setResizable(false);
                stage.show();
                Stage nuevaScena = (Stage) this.buttonAtras.getScene().getWindow();
                nuevaScena.close();
            } catch (IOException ex) {
                textoAlerta = "Error";
                mostrarAlerta(textoAlerta);
            }
        }
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        try {
            if (!(textFieldNombre.getText().isEmpty()
                    || textFieldCUIT.getText().isEmpty()
                    || comboSector.getSelectionModel().isEmpty())) {
                String nombre = textFieldNombre.getText();
                String CUIT = textFieldCUIT.getText();
                Boolean sector = comboSector.getSelectionModel().getSelectedItem();
                servicio.agregarEntidad(CUIT, nombre, sector);
                addEntidadesTabla();
                limpiar();
                textoAlerta = "Se agrego con exito";
            } else {
                textoAlerta = "Complete todos los campos";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        try {
            if (!(textFieldNombre.getText().isEmpty()
                    || textFieldCUIT.getText().isEmpty()
                    || comboSector.getSelectionModel().isEmpty())) {
                String nombre = textFieldNombre.getText();
                String CUIT = textFieldCUIT.getText();
                Boolean sector = comboSector.getSelectionModel().getSelectedItem();
                EntidadTrabajo entidad = servicio.buscarEntidad(tableEntidades.getSelectionModel().getSelectedItem().getId());
                limpiar();
                servicio.actualizarEntidad(CUIT, nombre, sector, entidad);
                addEntidadesTabla();
                textoAlerta = "Se actualizo con exito";
            } else {
                textoAlerta = "Complete todos los campos";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        try {
            boolean eliminar = servicio.eliminarEntidad(
                    this.tableEntidades.getSelectionModel().getSelectedItem().getId());
            limpiar();
            if (eliminar == true) {
                textoAlerta = "Se elimino con exito.";
                addEntidadesTabla();               
            } else {
                textoAlerta = "No se puedde eliminar la Entidad.";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    public void cargarSector() {
        sectores.addAll(true, false);
        comboSector.setItems(sectores);
    }

    public void addEntidadesTabla() {
        tableEntidades.getItems().clear();
        entidades.addAll(servicio.listarEntidades());
        tableEntidades.setItems(entidades);
    }

    public void setColumnTables() {
        this.columnCUIT.setCellValueFactory(new PropertyValueFactory("CUIT"));
        this.columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.columnPrivadaPublic.setCellValueFactory(new PropertyValueFactory("isPublica"));
    }

    public void mostrarAlerta(String textoAlerta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informacion");
        alerta.setContentText(textoAlerta);
        alerta.show();
    }

    public void limpiar() {
        this.textFieldNombre.setText("");
        this.textFieldCUIT.setText("");
        this.comboSector.getSelectionModel().clearSelection();
        this.tableEntidades.getSelectionModel().clearSelection();
    }
}
