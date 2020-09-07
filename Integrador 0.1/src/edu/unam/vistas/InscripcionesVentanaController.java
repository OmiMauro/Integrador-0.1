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
    private TableColumn<Inscripcion, String> columnID;
    @FXML
    private TableColumn<Inscripcion, EdicionConferencia> columnEdicion;
    @FXML
    private TableColumn<Inscripcion, Persona> columnPersona;
    @FXML
    private TableColumn<Inscripcion, String> columnExpositor;
    @FXML
    private TableColumn<Inscripcion, String> columnPrescencial;
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
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    private ObservableList<String> expositor= FXCollections.observableArrayList();
    private ObservableList<Persona> personas= FXCollections.observableArrayList();
    private ObservableList<EdicionConferencia> ediciones= FXCollections.observableArrayList();
    private ObservableList<String> asistencia= FXCollections.observableArrayList();
    private ObservableList<Inscripcion> inscripciones = FXCollections.observableArrayList();
    
    
    public InscripcionesVentanaController( ){
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
                System.out.println("problemas");
            }
        }
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        
        
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
    }
    
    public void limpiar(){
        this.comboExpositor.getSelectionModel().clearSelection();
        this.comboModoAsistencia.getSelectionModel().clearSelection();
        this.comboPersona.getSelectionModel().clearSelection();
        this.comboEdicion.getSelectionModel().clearSelection();
    }
    public void mostrarAlerta(String textoAlerta){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);      
        alerta.setTitle(textoAlerta);
        alerta.show();      
    }
    public void agregarDatosTabla(){
        this.tableInscripciones.getItems().clear();
        this.inscripciones.addAll(servicio.listarInscripciones());
        this.tableInscripciones.setItems(inscripciones);
    }
    public void setPropertyTabla(){
        this.columnID.setCellValueFactory(new PropertyValueFactory("id"));
        this.columnEdicion.setCellValueFactory(new PropertyValueFactory("edicion"));
        this.columnPersona.setCellValueFactory(new PropertyValueFactory("persona"));
        this.columnExpositor.setCellValueFactory(new PropertyValueFactory("isExpositor"));
        this.columnPrescencial.setCellValueFactory(new PropertyValueFactory("isPrescencial"));
        this.columnFechaInscripcion.setCellValueFactory(new PropertyValueFactory("fechaInscripcion"));
        this.columnEntidadTrabajo.setCellValueFactory(new PropertyValueFactory("entidad"));
    }
    public void agregarModoPrescencia(){
        expositor.addAll("Expositor","Oyente");
        this.comboExpositor.setItems(expositor);
    }
    public void agregarModoAsistencia(){
        asistencia.addAll("Prescencial","Online");
        this.comboModoAsistencia.setItems(asistencia);
    }
    public void agregarPersonas(){
        personas.addAll(servicio.listarPersonas());
        this.comboPersona.setItems(personas);
    }
    public void agregarEdiciones(){
        ediciones.addAll(servicio.listarEdiciones());
        this.comboEdicion.setItems(ediciones);
    }
}
