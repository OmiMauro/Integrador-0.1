

package edu.unam.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/** 
    @author Ominuka Mauro
*/
@Entity(name = "entidades")
public class EntidadTrabajo implements Serializable {
    @Id
    //@SequenceGenerator(name = "seq_entidad" , initialValue = 1, allocationSize = 5)   
    @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator = "seq_entidad")
    private long id;
    private String CUIT;
    private String nombre;
    private boolean isPublica; // Por defecto la entidad es privada
    @OneToMany(mappedBy = "entidadTrabajo")
    private List<Persona> personas = new ArrayList<>();
    

    public EntidadTrabajo(){}
    
    public EntidadTrabajo(String CUIT, String nombre, boolean isPublica){
        this.CUIT = CUIT;
        this.nombre = nombre;
        this.isPublica = isPublica;
    }    
    //Setters && getters
    public String getCUIT(){
        return this.CUIT;
    }

    public void setCUIT(String CUIT){
        this.CUIT = CUIT;
    }
    
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public List<Persona> mostrarPersonas(){
        return this.personas;
    }
    
    public boolean eliminarPersona(String dni){
        for(Persona temp : personas){
            if(temp.getDNI().equals(dni)){
                personas.remove(temp);
                return true;
            }
        }
        return false;
    }
    
    public void agregarPersona(Persona persona){
        this.personas.add(persona);
        //persona.setEntidadTrabajo(this);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsPublica() {
        return isPublica;
    }

    public void setIsPublica(boolean isPublica) {
        this.isPublica = isPublica;
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
}
