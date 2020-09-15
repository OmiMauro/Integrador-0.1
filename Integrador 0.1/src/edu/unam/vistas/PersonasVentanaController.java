/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.modelo.EntidadTrabajo;
import edu.unam.modelo.Persona;
import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
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
public class PersonasVentanaController implements Initializable {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    final Repositorio em = new Repositorio(emf);
    final Servicio servicio = new Servicio(em);
    @FXML
    private TableView<Persona> tablePersona;
    @FXML
    private TableColumn<Persona, String> columnDNI;
    @FXML
    private TableColumn<Persona, String> columnNombre;
    @FXML
    private TableColumn<Persona, String> columnApellido;
    @FXML
    private TableColumn<Persona, LocalDate> columnFechaNacimiento;
    @FXML
    private TableColumn<Persona, EntidadTrabajo> columnEntidad;
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
    private TextField textFieldApellido;
    @FXML
    private TextField textFieldDNI;
    @FXML
    private DatePicker datePickerFechaNac;
    @FXML
    private ComboBox<EntidadTrabajo> comboEntidad;
    private FXMLLoader cargador;
    private Scene scene;
    private Stage stage;
    private String textoAlerta;
    private ObservableList<Persona> personas = FXCollections.observableArrayList();
    private ObservableList<EntidadTrabajo> entidades = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumnTableProperty();
        addPersonas();
        agregarEntidad();
        this.buttonActualizar.disableProperty().bind(this.tablePersona.getSelectionModel().selectedItemProperty().isNull());
        this.buttonEliminar.disableProperty().bind(this.tablePersona.getSelectionModel().selectedItemProperty().isNull());
        this.buttonAgregar.disableProperty().bind(this.tablePersona.getSelectionModel().selectedItemProperty().isNotNull());

        this.tablePersona.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Persona>() {
            @Override
            public void changed(ObservableValue<? extends Persona> ov, Persona datosAnt, Persona perNueva) {
                textFieldApellido.setText(perNueva.getApellido());
                textFieldNombre.setText(perNueva.getNombre());
                textFieldDNI.setText(perNueva.getDNI());
                comboEntidad.setValue(perNueva.getEntidadTrabajo());
                textFieldDNI.setDisable(true);
                datePickerFechaNac.setValue(perNueva.getFechaNacimiento());
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
    private void handleAgregarPersona(ActionEvent event) {
        try {
            if (!(textFieldDNI.getText().isEmpty()
                    || textFieldApellido.getText().isEmpty()
                    || textFieldNombre.getText().isEmpty()
                    || comboEntidad.getSelectionModel().isEmpty())) {
                String nombre = this.textFieldNombre.getText();
                String apellido = this.textFieldApellido.getText();
                String dni = this.textFieldDNI.getText();
                LocalDate fechaNac = this.datePickerFechaNac.getValue();
                EntidadTrabajo entidad = this.comboEntidad.getSelectionModel().getSelectedItem();
                servicio.agregarPersona(nombre, apellido, dni, fechaNac, entidad);
                addPersonas();
                textoAlerta = "Se agrego con exito";
                limpiar();
            } else {
                textoAlerta = "Complete todos los campos";
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        mostrarAlerta(textoAlerta);
    }

    @FXML
    private void handleActualizarPersona(ActionEvent event) {
        try {
            if (!(textFieldApellido.getText().isEmpty()
                    || textFieldNombre.getText().isEmpty()
                    || comboEntidad.getSelectionModel().isEmpty())) {
                String nombre = this.textFieldNombre.getText();
                String apellido = this.textFieldApellido.getText();
                LocalDate fechaNac = this.datePickerFechaNac.getValue();
                EntidadTrabajo entidad = this.comboEntidad.getValue();
                String DNI = this.tablePersona.getSelectionModel().getSelectedItem().getDNI();
                boolean actualizar = servicio.actualizarPersona(DNI, nombre, apellido, fechaNac, entidad);
                if (actualizar == true) {
                    addPersonas();
                    textoAlerta = "Se actualizo con exito";
                } else {
                    textoAlerta = "No se puede actualizar";
                }
            }
        } catch (Exception e) {
            textoAlerta = e.toString();
        }
        limpiar();
        mostrarAlerta(textoAlerta);
    }

    @FXML
    private void handleEliminarPersona(ActionEvent event) {
        try {
            String DNI = this.tablePersona.getSelectionModel().getSelectedItem().getDNI();
            boolean eliminar = servicio.eliminarPersona(DNI);
            limpiar();
            if (eliminar == true) {
                textoAlerta = "Se elimino con exito";
                addPersonas();
            } else {
                textoAlerta = "No se puede eliminar a la persona";
            }
        } catch (Exception ex) {
            textoAlerta = ex.toString();
        }
        mostrarAlerta(textoAlerta);
        limpiar();
    }

    public void limpiar() {
        this.textFieldNombre.setText("");
        this.textFieldApellido.setText("");
        this.textFieldDNI.setText("");
        this.comboEntidad.getSelectionModel().clearSelection();
        this.datePickerFechaNac.setValue(LocalDate.now());
        this.tablePersona.getSelectionModel().clearSelection();
    }

    public void mostrarAlerta(String textoAlerta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informacion");
        alerta.setHeaderText(textoAlerta);
        alerta.show();
    }

    public void setColumnTableProperty() {
        this.columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.columnApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.columnDNI.setCellValueFactory(new PropertyValueFactory("DNI"));
        this.columnFechaNacimiento.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
        this.columnEntidad.setCellValueFactory(new PropertyValueFactory("entidadTrabajo"));
    }

    public void addPersonas() {
        this.tablePersona.getItems().clear();
        personas.addAll(servicio.listarPersonas());
        this.tablePersona.setItems(personas);
    }

    public void agregarEntidad() {
        entidades.addAll(servicio.listarEntidades());
        this.comboEntidad.setItems(entidades);
    }
}
