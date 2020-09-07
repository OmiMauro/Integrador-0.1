
/*Enlace interesante sobre el manejo de fechas y horas con el paquete de librerias
de time
https://www.campusmvp.es/recursos/post/como-manejar-correctamente-fechas-en-java-el-paquete-java-time.aspx
https://sodocumentation.net/es/javafx/topic/1580/fxml-y-controladores
*/
package edu.unam;

import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import edu.unam.vistas.ConferenciasVentanaController;
import edu.unam.vistas.InicioVentanaController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ominuka, Mauro
 */
public class Principal extends Application {
    FXMLLoader cargador;
    Parent root;
    public static void main(String[] args) {     
       /* EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
        Repositorio em = new Repositorio(emf);
        Servicio servicio = new Servicio(em);*/
        launch(args);
    }
    
    @Override
    public void start(Stage stage ) throws Exception {  
        try{  
        Parent root = FXMLLoader.load(getClass().getResource("/edu/unam/vistas/InicioVentana.fxml"));        
        Scene scene = new Scene(root);          
        stage.setScene(scene);
        stage.setTitle("Gestion de Conferencias");
        stage.show();
        }
            catch(IOException e){
        }              
    }
}
  