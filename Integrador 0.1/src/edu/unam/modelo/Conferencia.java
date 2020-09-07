

package edu.unam.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/** 
    @author Ominuka Mauro
*/
@Entity(name = "conferencias")
//@UuidGenerator(name = "UUIDGenerador")
public class Conferencia implements Serializable {
    @Id
    private String id;
    private String nombre;
    private String temaDebate;
    //private Direccion direccion;
    @OneToMany(mappedBy = "conferencia")
    private List<EdicionConferencia> ediciones = new ArrayList<>(); ;
    
    public Conferencia() {      
        this.id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }

    public Conferencia(String nombre, String temaDebate) {     
        this.id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.nombre = nombre;
        this.temaDebate = temaDebate;      
    }

    public String getId() {
        return id;
    }

    public void setId(int numero, String letras) {
        this.id = numero + letras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTemaDebate() {
        return temaDebate;
    }

    public void setTemaDebate(String temaDebate) {
        this.temaDebate = temaDebate;
    }
    
    public List<EdicionConferencia> mostrarEdiciones(){
        return this.ediciones;
    } 
    
    public void agregarEdicion(EdicionConferencia edicion){
        this.ediciones.add(edicion);
        edicion.setConferencia(this);
        
    }
    
    public EdicionConferencia buscarEdicion(int id){
        for (EdicionConferencia edicion : ediciones){
            if (edicion.getId() == id){
                return edicion;
            } 
        }
        return null;
    }
    
    public boolean eliminarEdicion(int id){
        for (EdicionConferencia edicion : ediciones){
            if (edicion.getId() == id){
                ediciones.remove(edicion);
                return true;
                
            }    
        }
        return false;
    }
    @Override
    public String toString(){
        return this.getId() + " " + this.getNombre() + " " +this.getTemaDebate();
    }
}