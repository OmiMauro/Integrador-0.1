

package edu.unam.repositorio;

/** 
    @author Ominuka Mauro
 */

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;


public class Repositorio {
    
    private final EntityManager em;
    
    public Repositorio(EntityManagerFactory emf){
        this.em = emf.createEntityManager();
    }
    
    public void iniciarTransaccion(){
        this.em.getTransaction().begin();
    }
    
    public void finalizarTransaccion(){
        this.em.getTransaction().commit();
    }
    
    
    public void rollbackTransaccion(){
        this.em.getTransaction().rollback();
    }
    
    public void insertar(Object o){
        this.em.persist(o);
    }
    
    public void modificar(Object o){
        this.em.merge(o);
    }
    
    public void eliminar(Object o){
        this.em.remove(o);
    }
    
    public void recargar(Object o){
        this.em.refresh(o);
    }
    
    public <B extends Object> B buscar(Class<B> clase, Object id) {
        return (B) this.em.find(clase, id);
    } 
    
    //Listar objetos de una clase
    public <B extends Object> List<B> buscarTodos (Class<B> clase) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<B> consulta = cb.createQuery(clase);
        Root<B> inicio = consulta.from(clase);
        consulta.select(inicio);
        return em.createQuery(consulta).getResultList();      
    }
    
    public <B extends Object, P extends Object> List<B> buscarTodosOrdenados (
            Class<B> clase,  SingularAttribute <B, P> orden) {       
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<B> consulta = cb.createQuery(clase);
        Root<B> inicio = consulta.from(clase);
        consulta.select(inicio);
        consulta.orderBy(cb.asc(inicio.get(orden)));
        return em.createQuery(consulta).getResultList();      
    }
}
