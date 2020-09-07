

package edu.unam.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
/** 
    @author Ominuka Mauro
*/
@Entity(name="ediciones")
public class EdicionConferencia implements Serializable {
    @Id
   // @SequenceGenerator(name = "Seq_Edicion" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  
    private LocalDate fechaInicio;
    private LocalDate fechaFin;    
    private Conferencia conferencia;
    private String direccion;
    @OneToMany(mappedBy = "edicion")
    private List<Inscripcion> inscripciones = new ArrayList<>();
 
    
    public EdicionConferencia() {        
    }
    
    public EdicionConferencia(LocalDate fechaInicio, LocalDate fechaFin, Conferencia conf
                            , String direccion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;        
        this.conferencia = conf;      
        this.direccion = direccion;
    }
    public Conferencia getConferencia() {
        return conferencia;
    }

    public void setConferencia(Conferencia conferencia) {
        this.conferencia = conferencia;
    }
    
    public void agregarInscripcion(Inscripcion inscripcion){
        this.inscripciones.add(inscripcion);
        inscripcion.setEdicion(this);
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
     public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Retorna el/los expositores que se inscribieron a una edicion. Si no encuentra, 
    //retorna un valor null, en ese caso, quien da la conferencia?
    public List<Inscripcion> buscarExpositores(){
        ArrayList<Inscripcion> expositor = new ArrayList<>();
        for(Inscripcion inscripto : inscripciones){
            if (inscripto.isExpositor()){
                expositor.add(inscripto);         
            }
        }
        if (expositor.size() > 0){
            return expositor;
        } else{return null;
            }  
    }
    
    public boolean eliminarInscripto(int id){
        for (Inscripcion temp : inscripciones){
            if( temp.getId() == id){
                inscripciones.remove(temp);
                return true;
            }          
        }
        return false; 
    }
    @Override
    public String toString(){
        return this.id + this.conferencia.getNombre();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    
}
