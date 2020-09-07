

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
    private boolean isPrescencial;    
    private Persona persona;
    private EdicionConferencia edicion;
    private EntidadTrabajo entidad;
    public Inscripcion() {
    }

    public Inscripcion(boolean isExpositor, boolean isPrescencial, Persona persona, 
            EdicionConferencia edicion, EntidadTrabajo entidad) {
        this.fechaInscripcion = LocalDate.now();
        this.isExpositor = isExpositor;
        this.isPrescencial = isPrescencial;
        this.entidad = entidad;
        this.edicion = edicion;
        this.persona = persona;
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

    public boolean isPrescencial() {
        return isPrescencial;
    }

    public void setIsPrescencial(boolean isPrescencial) {
        this.isPrescencial = isPrescencial;
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
}
