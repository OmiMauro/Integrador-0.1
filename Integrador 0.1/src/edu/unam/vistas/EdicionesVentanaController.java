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
    ObservableList<Conferencia> conferencias  = FXCollections.observableArrayList();
   
    
    public EdicionesVentanaController(){
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
       comboConferencia.setItems(conferencias);
       this.buttonAgregar.disableProperty().bind(
               this.tableEdiciones.getSelectionModel().selectedItemProperty().isNotNull());
       this.buttonActualizar.disableProperty().bind(
               this.tableEdiciones.getSelectionModel().selectedItemProperty().isNull());
       this.buttonEliminar.disableProperty().bind(
               this.tableEdiciones.getSelectionModel().selectedItemProperty().isNull());
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
        if(comboConferencia.getSelectionModel().isEmpty())
            {
//           /|| datePickerInicio.getText().isEmpty() 
            
        }else{
            String textoAlerta = "Complete todos los campos";
            mostrarAlerta(textoAlerta);
        }
    }

    @FXML
    private void handleAtualizar(ActionEvent event) {
        try{     
        }
        catch (Exception e ){
           
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
    }
        
    public void limpiar(){       
        comboConferencia.getSelectionModel().clearSelection();
        textFieldDireccion.setText("");
        datePickerInicio.setValue(LocalDate.now());
        datePickerFinal.setValue(LocalDate.now());
        tableEdiciones.getSelectionModel().clearSelection();
    }
    public void mostrarAlerta(String textoAlerta){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);      
        alerta.setTitle(textoAlerta);
        alerta.show();      
    }
    public void agregarEdicionesTabla(){
        this.tableEdiciones.getItems().clear();
        ediciones.addAll(servicio.listarEdiciones());
        tableEdiciones.setItems(ediciones);
    }
    public void setColumns(){
        this.columnConferencia.setCellValueFactory(new PropertyValueFactory("conferencia"));
        this.columnId.setCellValueFactory(new PropertyValueFactory("id"));
        this.columnInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        this.columnFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        this.columnUbicacion.setCellValueFactory(new PropertyValueFactory("direccion"));
    }
} 