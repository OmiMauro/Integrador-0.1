/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Ominuka Mauro
 */
public class InicioVentanaController implements Initializable {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    Repositorio em = new Repositorio(emf);
    Servicio servicio = new Servicio(em);      
    @FXML
    private Button buttonConferencias;   
    @FXML
    private Button buttonEdiciones;
    @FXML
    private Button buttonEntidades;
    @FXML
    private Button buttonEstadisticas;
    @FXML
    private Button buttonPersonas;

    private FXMLLoader cargador;
    private Parent root;
    private Scene scene;
    private Stage stage;  
     public InicioVentanaController(){
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    @FXML
    private void handleConferencias(ActionEvent event)  {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/ConferenciasVentana.fxml"));               
                ConferenciasVentanaController controladorConferencias = cargador.getController(); 
                scene = new Scene(cargador.load());
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Conferencias");
                stage.setResizable(false);
                stage.show();              
                Stage nuevaScena = (Stage) this.buttonConferencias.getScene().getWindow();
                nuevaScena.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    @FXML
    private void handleEdiciones(ActionEvent event)  {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/EdicionesVentana.fxml"));
                root = cargador.load();
                EdicionesVentanaController controlador =  cargador.getController();
                scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Ediciones");
                stage.setResizable(false);
                stage.show();               
                Stage nuevaScena = (Stage) this.buttonPersonas.getScene().getWindow();
                nuevaScena.close();
            } catch (IOException ex) {
                System.out.println("problemas");
            }

        }
    }
    @FXML
    private void handleEntidades(ActionEvent event)  {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/EntidadesTrabajoVentana.fxml"));
                root = cargador.load();
                EntidadesTrabajoVentanaController controlador = cargador.getController();                
                scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Entidades");
                stage.setResizable(false);
                stage.show();
                
                Stage nuevaScena = (Stage) this.buttonEntidades.getScene().getWindow();
                nuevaScena.close();

            } catch (IOException ex) {
                System.out.println("problemas");
            }

        }
    }

    @FXML
    private void handlePersonas(ActionEvent event) {
        {
            try {
                cargador = new FXMLLoader(getClass().getResource("/edu/unam/vistas/PersonasVentana.fxml"));
                root = cargador.load();
                PersonasVentanaController controlador = cargador.getController();
                scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Personas");
                stage.setResizable(false);
                stage.show();
                
                Stage nuevaScena = (Stage) this.buttonPersonas.getScene().getWindow();
                nuevaScena.close();

            } catch (IOException ex) {
                System.out.println("problemas");
            }

        }
    }

    public void cerrarVentana() {
    }    

   
}
