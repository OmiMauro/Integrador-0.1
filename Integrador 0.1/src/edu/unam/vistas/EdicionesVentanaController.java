/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.modelo.Conferencia;
import edu.unam.modelo.EdicionConferencia;
import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
public class EdicionesVentanaController implements Initializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    Repositorio em = new Repositorio(emf);
    Servicio servicio = new Servicio(em);
    @FXML
    private Button buttonInicio;
    @FXML
    private Button buttonInscripciones;
    @FXML
    private TableView<EdicionConferencia> tableEdiciones;
    @FXML
    private TableColumn<EdicionConferencia, Long> columnId;
    @FXML
    private TableColumn<EdicionConferencia, String> columnConferencia;
    @FXML
    private TableColumn<EdicionConferencia, LocalDate> columnInicio;
    @FXML
    private TableColumn<EdicionConferencia, LocalDate> columnFin;
    @FXML
    private TableColumn<EdicionConferencia, String> columnUbicacion;
    @FXML
    private Button buttonAtras;
    @FXML
    private Button buttonAgregar;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Button buttonEliminar;
    @FXML
    private ComboBox<Conferencia> comboConferencia;
    @FXML
    private DatePicker datePickerInicio;
    @FXML
    private DatePicker datePickerFinal;
    @FXML
    private TextField textFieldDireccion;
    private FXMLLoader cargador;
    private Scene scene;
    private Stage stage;
    ObservableList<EdicionConferencia> ediciones = FXCollections.observableArrayList();
    ObservableList<Conferencia> conferencias = FXCollections.observableArrayList();
    private String textoAlerta;

    public EdicionesVentanaController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        establecerFechas();
        comboConferencia.setItems(conferencias);
        this.buttonAgregar.disableProperty().bind(
                this.tableEdiciones.getSelectionModel().selectedItemProperty().isNotNull());
        this.buttonActualizar.disableProperty().bind(
                this.tableEdiciones.getSelectionModel().selectedItemProperty().isNull());
        this.buttonEliminar.disableProperty().bind(
                this.tableEdiciones.getSelectionModel().selectedItemProperty().isNull());
        this.tableEdiciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EdicionConferencia>() {
            @Override
            public void changed(ObservableValue<? extends EdicionConferencia> ov,
                    EdicionConferencia valorViejo, EdicionConferencia valorNuevo) {
                comboConferencia.setValue(valorNuevo.getConferencia());
                datePickerInicio.setValue(valorNuevo.getFechaInicio());
                datePickerFinal.setValue(valorNuevo.getFechaFin());
                textFieldDireccion.setText(valorNuevo.getDireccion());
            }
        });
    }

    @FXML
    private void handleInscripciones(ActionEvent event) {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/InscripcionesVentana.fxml"));
                InscripcionesVentanaController controlador = cargador.getController();
                scene = new Scene(cargador.load());
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Inscripciones");
                stage.setResizable(false);
                stage.show();
                Stage nuevaScena = (Stage) this.buttonInscripciones.getScene().getWindow();
                nuevaScena.close();
            } catch (IOException ex) {
                System.out.println("problemas");
            }
        }
    }

    @FXML
    private void buttonAtras(ActionEvent event) {
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
                System.out.println("problemas");
            }
        }
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        try {
            if (!(this.comboConferencia.getSelectionModel().isEmpty()
                    || this.textFieldDireccion.getText().isEmpty())) {
                Conferencia conf = this.comboConferencia.getSelectionModel().getSelectedItem();
                String direccion = this.textFieldDireccion.getText();
                LocalDate fechaInicio = this.datePickerInicio.getValue();
                LocalDate fechaFin = this.datePickerFinal.getValue();
                this.servicio.agregarEdicion(fechaInicio, fechaInicio, conf, direccion);
                textoAlerta = "Se actualizo con exito.";
            }else{textoAlerta = "Complete todos los campos.";}
            

        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    @FXML
    private void handleAtualizar(ActionEvent event) {
        try {
            long id = this.tableEdiciones.getSelectionModel().getSelectedItem().getId();
            if(!(this.comboConferencia.getSelectionModel().isEmpty()
                    || this.textFieldDireccion.getText().isEmpty())) {
                Conferencia conferencia = this.comboConferencia.getSelectionModel().getSelectedItem();
                String direccion = this.textFieldDireccion.getText();
                LocalDate fechaInicio = this.datePickerInicio.getValue();
                LocalDate fechaFin = this.datePickerFinal.getValue();
                
                boolean actualizar = servicio.actualizarEdicion(id,
                        fechaInicio, fechaFin, conferencia, direccion);
                if (actualizar == true){
                    textoAlerta = "Se actualizo con exito";
                }else{textoAlerta = "No se puede actualizar. Complete todos los campos";
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        try {
            long id = this.tableEdiciones.getSelectionModel().getSelectedItem().getId();
            boolean eliminar = servicio.eliminarEdicion(id);
            if (eliminar == true) {
                textoAlerta = "Se elimino con exito";
                agregarEdicionesTabla();
            } else {
                textoAlerta = "No se puede eliminar la edicion";
            }

        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        limpiar();
        mostrarAlerta(textoAlerta);
    }

    public void limpiar() {
        comboConferencia.getSelectionModel().clearSelection();
        textFieldDireccion.setText("");
        tableEdiciones.getSelectionModel().clearSelection();
        establecerFechas();
    }

    public void establecerFechas() {
        datePickerInicio.setValue(LocalDate.now());
        datePickerFinal.setValue(LocalDate.now());
    }

    public void mostrarAlerta(String textoAlerta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informacion");
        alerta.setHeaderText(textoAlerta);
        alerta.show();
    }

    public void agregarEdicionesTabla() {
        this.tableEdiciones.getItems().clear();
        ediciones.addAll(servicio.listarEdiciones());
        tableEdiciones.setItems(ediciones);
    }

    public void setColumns() {
        this.columnConferencia.setCellValueFactory(new PropertyValueFactory("conferencia"));
        this.columnId.setCellValueFactory(new PropertyValueFactory("id"));
        this.columnInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        this.columnFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        this.columnUbicacion.setCellValueFactory(new PropertyValueFactory("direccion"));
    }
}
