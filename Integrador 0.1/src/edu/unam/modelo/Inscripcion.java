

package edu.unam.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/** 
    @author Ominuka Mauro
*/
@Entity(name = "inscripciones")
public class Inscripcion implements Serializable {
    @Id
   // @SequenceGenerator(name = "Seq_Inscripcion")   
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;    
    private LocalDate fechaInscripcion;
    private boolean isExpositor;
    private boolean isPresencial;    
    private Persona persona;
    private EdicionConferencia edicion;
    private EntidadTrabajo entidad;
    public Inscripcion() {
    }

    public Inscripcion(boolean isExpositor, boolean isPresencial, Persona persona, 
            EdicionConferencia edicion) {
        this.fechaInscripcion = LocalDate.now();
        this.isExpositor = isExpositor;
        this.isPresencial = isPresencial;
        this.entidad = persona.getEntidadTrabajo();
        this.edicion = edicion;
        this.persona = persona;
        persona.agregarInscripcion(this);
    }
    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean isExpositor() {
        return isExpositor;
    }

    public void setIsExpositor(boolean isExpositor) {
        this.isExpositor = isExpositor;
    }

    public boolean isPresencial() {
        return isPresencial;
    }

    public void setIsPresencial(boolean isPresencial) {
        this.isPresencial = isPresencial;
    }
    
    public Persona getPersona(){
        return this.persona;
    }
    public void setPersona(Persona persona){
        this.persona = persona;
        persona.agregarInscripcion(this);
    }
    
    public long getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public EdicionConferencia getEdicion() {
        return this.edicion;
    }

    public void setEdicion(EdicionConferencia edicion) {
        this.edicion = edicion;
    }

    public EntidadTrabajo getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadTrabajo entidad) {
        this.entidad = entidad;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIsExpositor() {
        return isExpositor;
    }

    public boolean isIsPresencial() {
        return isPresencial;
    }
    
}
