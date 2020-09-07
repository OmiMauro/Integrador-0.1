

package edu.unam.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/** 
    @author Ominuka Mauro
*/
@Entity(name = "personas")
public class Persona implements Serializable{    
    @Id
    private String DNI;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;  
    private EntidadTrabajo entidadTrabajo;   
    @OneToMany(mappedBy = "persona")
    private List<Inscripcion> inscripciones ;      
    public Persona(){ }
    public Persona(String nombre, String apellido, String DNI, LocalDate fechaNacimiento){
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.inscripciones = new ArrayList<>();
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getDNI(){
        return this.DNI;
    }

    public void setDNI(String DNI){
        this.DNI = DNI;
    }

    public LocalDate getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    public void setfechaNacimiento(LocalDate fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setEntidadTrabajo(EntidadTrabajo entidadTrabajo){
        this.entidadTrabajo = entidadTrabajo;
        entidadTrabajo.agregarPersona(this);
    }
    public EntidadTrabajo getEntidadTrabajo(){
        return this.entidadTrabajo;
    }
   
    public void agregarInscripcion(Inscripcion inscripcion){
        this.inscripciones.add(inscripcion);
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    
    @Override 
    public String toString(){
        return getDNI() + "-" + getApellido();
    }
}
