/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unam.vistas;

import edu.unam.repositorio.Repositorio;
import edu.unam.servicios.Servicio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**

 */
public class EstadisticasVentanaController implements Initializable {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegradorPU");
    final Repositorio em = new Repositorio(emf);
    final Servicio servicio = new Servicio(em);
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series<Object,Object> serie = new XYChart.Series<>();
        serie.getData().addAll(servicio.listarEdiciones());
        
    }    
    
}
