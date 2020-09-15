/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.modelo.EdicionConferencia;
import edu.unam.modelo.EntidadTrabajo;
import edu.unam.modelo.Inscripcion;
import edu.unam.modelo.Persona;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Ominuka Mauro
 */
public class InscripcionesVentanaController implements Initializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    Repositorio em = new Repositorio(emf);
    Servicio servicio = new Servicio(em);
    @FXML
    private TableView<Inscripcion> tableInscripciones;
    @FXML
    private TableColumn<Inscripcion, Long> columnID;
    @FXML
    private TableColumn<Inscripcion, EdicionConferencia> columnEdicion;
    @FXML
    private TableColumn<Inscripcion, Persona> columnPersona;
    @FXML
    private TableColumn<Inscripcion, String> columnExpositor;
    @FXML
    private TableColumn<Inscripcion, String> columnPresencial;
    @FXML
    private TableColumn<Inscripcion, LocalDate> columnFechaInscripcion;
    @FXML
    private TableColumn<Inscripcion, EntidadTrabajo> columnEntidadTrabajo;
    @FXML
    private Button buttonAtras;
    @FXML
    private Button buttonAgregar;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Button buttonEliminar;
    @FXML
    private ComboBox<EdicionConferencia> comboEdicion;
    @FXML
    private ComboBox<Persona> comboPersona;
    @FXML
    private ComboBox<String> comboExpositor;
    @FXML
    private ComboBox<String> comboModoAsistencia;

    private FXMLLoader cargador;
    private Scene scene;
    private Stage stage;
    private String textoAlerta;
    private ObservableList<String> expositor = FXCollections.observableArrayList();
    private ObservableList<Persona> personas = FXCollections.observableArrayList();
    private ObservableList<EdicionConferencia> ediciones = FXCollections.observableArrayList();
    private ObservableList<String> asistencia = FXCollections.observableArrayList();
    private ObservableList<Inscripcion> inscripciones = FXCollections.observableArrayList();

    public InscripcionesVentanaController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarModoPrescencia();
        agregarModoAsistencia();
        agregarPersonas();
        agregarEdiciones();
        setPropertyTabla();
        agregarDatosTabla();
        //deshabilitar el button de agregar cuando se selecciona un item de la tabla
        this.buttonAgregar.disableProperty().bind(
                this.tableInscripciones.getSelectionModel().selectedItemProperty().isNotNull());
        this.buttonActualizar.disableProperty().bind(
                this.tableInscripciones.getSelectionModel().selectedItemProperty().isNull());
        this.buttonEliminar.disableProperty().bind(
                this.tableInscripciones.getSelectionModel().selectedItemProperty().isNull());
        this.tableInscripciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Inscripcion>() {
            @Override
            public void changed(ObservableValue<? extends Inscripcion> ov, Inscripcion viejo, Inscripcion valorNuevo) {
                comboEdicion.setValue(valorNuevo.getEdicion());
                comboEdicion.setDisable(true);
                comboPersona.setValue(valorNuevo.getPersona());
                comboPersona.setDisable(true);
                comboExpositor.setValue(setExpositor(valorNuevo.isExpositor()));
                comboModoAsistencia.setValue(setAsistencia(valorNuevo.isPresencial()));
            }
        }
        );
    }

    @FXML
    private void handleAtras(ActionEvent event) {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/EdicionesVentana.fxml"));
                EdicionesVentanaController controlador = cargador.getController();
                scene = new Scene(cargador.load());
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Ediciones");
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
            if (!(comboEdicion.getSelectionModel().isEmpty()
                    || comboPersona.getSelectionModel().isEmpty()
                    || comboModoAsistencia.getSelectionModel().isEmpty()
                    || comboExpositor.getSelectionModel().isEmpty())) {
                EdicionConferencia edicion = comboEdicion.getSelectionModel().getSelectedItem();
                Persona persona = comboPersona.getSelectionModel().getSelectedItem();
                boolean modoAsistencia = obtenerModoAsistencia(comboModoAsistencia.getSelectionModel().getSelectedItem());
                boolean isExpositor = obtenerExpositor(comboExpositor.getSelectionModel().getSelectedItem());
                servicio.agregarInscripcion(isExpositor, modoAsistencia, persona, edicion);
                limpiar();
                textoAlerta = "Se agrego con exito";
            } else {
                textoAlerta = "Complete todos los campos.";
            }

        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
        agregarDatosTabla();
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        try {
            long id = this.tableInscripciones.getSelectionModel().getSelectedItem().getId();
            EdicionConferencia edicion = comboEdicion.getSelectionModel().getSelectedItem();
            Persona persona = comboPersona.getSelectionModel().getSelectedItem();
            boolean modoAsistencia = obtenerModoAsistencia(comboModoAsistencia.getSelectionModel().getSelectedItem());
            boolean isExpositor = obtenerExpositor(comboExpositor.getSelectionModel().getSelectedItem());
            servicio.actualizarInscripcion(id, isExpositor, modoAsistencia, persona, edicion);
            textoAlerta = "Se actualizo con exito.";
            limpiar();
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
        agregarDatosTabla();
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        try {
            long id = this.tableInscripciones.getSelectionModel().getSelectedItem().getId();
            boolean eliminar = this.servicio.eliminarInscripcion(id);
            if (eliminar == true) {
                textoAlerta = "Se elimino con exito";
            } else {
                textoAlerta = "No se puede eliminar";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
        limpiar();
        agregarDatosTabla();
    }

    public void limpiar() {
        this.comboExpositor.getSelectionModel().clearSelection();
        this.comboEdicion.setDisable(false);
        this.comboModoAsistencia.getSelectionModel().clearSelection();
        this.comboPersona.getSelectionModel().clearSelection();
        this.comboPersona.setDisable(false);
        this.comboEdicion.getSelectionModel().clearSelection();
        this.tableInscripciones.getSelectionModel().clearSelection();
    }

    public void mostrarAlerta(String textoAlerta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informacion");
        alerta.setHeaderText(textoAlerta);
        alerta.show();
    }

    public void agregarDatosTabla() {
        this.tableInscripciones.getItems().clear();
        this.inscripciones.addAll(servicio.listarInscripciones());
           
        this.tableInscripciones.setItems(inscripciones);
    }

    public void setPropertyTabla() {
        this.columnID.setCellValueFactory(new PropertyValueFactory("id"));
        this.columnEdicion.setCellValueFactory(new PropertyValueFactory("edicion"));
        this.columnPersona.setCellValueFactory(new PropertyValueFactory("persona"));
        this.columnExpositor.setCellValueFactory(new PropertyValueFactory("isExpositor"));
        this.columnPresencial.setCellValueFactory(new PropertyValueFactory("isPresencial"));
        this.columnFechaInscripcion.setCellValueFactory(new PropertyValueFactory("fechaInscripcion"));
        this.columnEntidadTrabajo.setCellValueFactory(new PropertyValueFactory("entidad"));
    
        
    }

    public void agregarModoPrescencia() {
        expositor.addAll("Expositor", "Oyente");
        this.comboExpositor.setItems(expositor);
    }

    public boolean obtenerExpositor(String isExpositor) {
        if (isExpositor.equals("Expositor")) {
            return true;
        }
        return false;
    }

    public String setExpositor(boolean exp) {
        if (exp == true) {
            return "Expositor";
        }
        return "Oyente";
    }

    public boolean obtenerModoAsistencia(String modo) {
        if (modo.equals("Presencial")) {
            return true;
        }
        return false;
    }

    public String setAsistencia(boolean asistencia) {
        if (asistencia == true) {
            return "Presencial";
        }
        return "Online";
    }

    public void agregarModoAsistencia() {
        asistencia.addAll("Presencial", "Online");
        this.comboModoAsistencia.setItems(asistencia);
    }

    public void agregarPersonas() {
        personas.addAll(servicio.listarPersonas());
        this.comboPersona.setItems(personas);
    }

    public void agregarEdiciones() {
        ediciones.addAll(servicio.listarEdiciones());
        this.comboEdicion.setItems(ediciones);
    }
}
