/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.modelo.Conferencia;
import edu.unam.modelo.Conferencia;
import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Binding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class ConferenciasVentanaController implements Initializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    Repositorio em = new Repositorio(emf);
    Servicio servicio = new Servicio(em);
    @FXML
    private TableView<Conferencia> tableConferencia;
    @FXML
    private TableColumn<Conferencia, String> columnId;
    @FXML
    private TableColumn<Conferencia, String> columnNombre;
    @FXML
    private TableColumn<Conferencia, String> coolumnTema;
    @FXML
    private Button buttonAtras;
    @FXML
    private Button buttonAgregar;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Button buttonEliminar;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldTema;
    private FXMLLoader cargador;
    private Scene scene;
    private Stage stage;
    private ObservableList<Conferencia> conferencias = FXCollections.observableArrayList();
    private String textoAlerta;

    public ConferenciasVentanaController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumnasTabla();
        agregarDatosTabla();
        buttonEliminar.disableProperty().bind(
                tableConferencia.getSelectionModel().selectedItemProperty().isNull());
        buttonActualizar.disableProperty().bind(
                tableConferencia.getSelectionModel().selectedItemProperty().isNull());
        buttonAgregar.disableProperty().bind(tableConferencia.getSelectionModel().selectedItemProperty().isNotNull());
        tableConferencia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (tableConferencia.getSelectionModel().getSelectedItem() != null) {
                    textFieldNombre.setText(tableConferencia.getSelectionModel().getSelectedItem().getNombre());
                    textFieldTema.setText(tableConferencia.getSelectionModel().getSelectedItem().getTemaDebate());
                }
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
                textoAlerta = ex.toString();
            }
        }
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        try {
            if (!(textFieldNombre.getText().isEmpty()
                    && textFieldTema.getText().isEmpty())) {
                String nombre = this.textFieldNombre.getText();
                String temaDebate = this.textFieldTema.getText();
                servicio.agregarConferencia(nombre, temaDebate);
                agregarDatosTabla();
                limpiar();
                textoAlerta = "Se agrego con exito.";
            } else {
                textoAlerta = "Complete los campos";
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
                    && textFieldTema.getText().isEmpty())) {
                String id = this.tableConferencia.getSelectionModel().getSelectedItem().getId();
                String nombre = textFieldNombre.getText();
                String temaDebate = textFieldTema.getText();
                boolean actualizar = servicio.actualizarConferencia(id, nombre, temaDebate);
                limpiar();
                if (actualizar == true) {
                    textoAlerta = "Se actualizo con exito";
                }
                agregarDatosTabla();

            } else {
                textoAlerta = "Seleccione un item y complete los campos";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        try {
            String id = this.tableConferencia.getSelectionModel().getSelectedItem().getId();
            boolean eliminar = this.servicio.eliminarConferencia(id);
            limpiar();
            if (eliminar == true) {
                textoAlerta = "Se elimino con exito";
                agregarDatosTabla();
            } else {
                textoAlerta = "No se puede eliminar";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    public void limpiar() {
        this.textFieldNombre.setText("");
        this.textFieldTema.setText("");
        this.tableConferencia.getSelectionModel().clearSelection();
    }

    public void mostrarAlerta(String textoAlerta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informacion");
        alerta.setHeaderText(textoAlerta);
        alerta.show();
    }

    public void setColumnasTabla() {
        this.columnId.setCellValueFactory(new PropertyValueFactory("id"));
        this.columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.coolumnTema.setCellValueFactory(new PropertyValueFactory("temaDebate"));
    }

    public void agregarDatosTabla() {
        this.tableConferencia.getItems().clear();
        this.conferencias.addAll(servicio.listarConferencias());
        this.tableConferencia.setItems(conferencias);
    }
}
