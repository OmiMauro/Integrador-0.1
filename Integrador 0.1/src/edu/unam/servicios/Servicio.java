package edu.unam.servicios;

import edu.unam.modelo.Conferencia;
import edu.unam.modelo.EdicionConferencia;
import edu.unam.modelo.EntidadTrabajo;
import edu.unam.modelo.Inscripcion;
import edu.unam.modelo.Persona;
import edu.unam.repositorio.Repositorio;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ominuka Mauro
 */
public class Servicio {

    Repositorio repositorio;

    public Servicio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List listarConferencias() {
        return this.repositorio.buscarTodos(Conferencia.class);
    }

    public Conferencia buscarConferencia(String id) {
        return this.repositorio.buscar(Conferencia.class, id);
    }

    public void agregarConferencia(String nombre, String temaDebate) {
        this.repositorio.iniciarTransaccion();
        var conferencia = new Conferencia(nombre.toUpperCase().trim(), temaDebate.toUpperCase().trim());
        this.repositorio.insertar(conferencia);
        this.repositorio.finalizarTransaccion();
    }

    public boolean actualizarConferencia(String id, String nombre, String temaDebate) {
        this.repositorio.iniciarTransaccion();
        Conferencia conf = this.repositorio.buscar(Conferencia.class, id);
        conf.setNombre(nombre.toUpperCase().trim());
        conf.setTemaDebate(temaDebate.toUpperCase().trim());
        this.repositorio.modificar(conf);
        this.repositorio.finalizarTransaccion();
        return true;
    }

    public boolean eliminarConferencia(String id) {
        this.repositorio.iniciarTransaccion();
        Conferencia conf = this.repositorio.buscar(Conferencia.class, id);
        if (conf != null && conf.mostrarEdiciones().isEmpty()) {
            this.repositorio.eliminar(conf);
            this.repositorio.finalizarTransaccion();
            return true;
        } else {
            this.repositorio.rollbackTransaccion();
            return false;
        }
    }

    public List listarEdiciones() {
        return this.repositorio.buscarTodos(EdicionConferencia.class);
    }

    public EdicionConferencia buscarEdicion(int id) {
        return this.repositorio.buscar(EdicionConferencia.class, id);
    }

    public void agregarEdicion(LocalDate fechaInicio, LocalDate fechaFin,
            Conferencia conferencia, String direccion) {
        this.repositorio.iniciarTransaccion();
        var edicion = new EdicionConferencia(fechaInicio, fechaFin, conferencia,
                direccion.trim().toUpperCase().trim());
        this.repositorio.insertar(edicion);
        this.repositorio.finalizarTransaccion();
    }

    public boolean actualizarEdicion(long id, LocalDate fechaInicio, LocalDate fechaFin,
            Conferencia conferencia, String direccion) {
        this.repositorio.iniciarTransaccion();
        EdicionConferencia edicion = this.repositorio.buscar(EdicionConferencia.class, id);
        if (edicion != null) {
            edicion.setConferencia(conferencia);
            edicion.setDireccion(direccion.trim().toUpperCase().trim());
            edicion.setFechaInicio(fechaInicio);
            edicion.setFechaFin(fechaFin);
            this.repositorio.modificar(edicion);
            this.repositorio.finalizarTransaccion();
            return true;
        }
        this.repositorio.rollbackTransaccion();
        return false;
    }

    public boolean eliminarEdicion(long id) {
        this.repositorio.iniciarTransaccion();
        EdicionConferencia edicion = this.repositorio.buscar(EdicionConferencia.class, id);
        if (edicion != null & edicion.getInscripciones().isEmpty()) {
            this.repositorio.eliminar(edicion);
            this.repositorio.finalizarTransaccion();
            return true;
        }
        this.repositorio.rollbackTransaccion();
        return false;
    }

    public List listarEntidades() {
        return this.repositorio.buscarTodos(EntidadTrabajo.class);
    }

    public EntidadTrabajo buscarEntidad(long id) {
        return this.repositorio.buscar(EntidadTrabajo.class, id);
    }

    public void agregarEntidad(String cuit, String nombre, boolean isPublica) {
        this.repositorio.iniciarTransaccion();
        var entidad = new EntidadTrabajo(cuit, nombre.toUpperCase().trim(), isPublica);
        this.repositorio.insertar(entidad);
        this.repositorio.finalizarTransaccion();
    }

    public void actualizarEntidad(String cuit, String nombre, boolean isPublica, EntidadTrabajo entidad) {
        this.repositorio.iniciarTransaccion();
        entidad.setCUIT(cuit);
        entidad.setNombre(nombre.toUpperCase().trim());
        entidad.setIsPublica(isPublica);
        this.repositorio.modificar(entidad);
        this.repositorio.finalizarTransaccion();
    }

    public boolean eliminarEntidad(long id) {
        this.repositorio.iniciarTransaccion();
        EntidadTrabajo entidad = this.repositorio.buscar(EntidadTrabajo.class, id);
        if (entidad != null && entidad.mostrarPersonas().isEmpty()) {
            this.repositorio.eliminar(entidad);
            this.repositorio.finalizarTransaccion();
            return true;
        } else {
            this.repositorio.rollbackTransaccion();
            return false;
        }

    }

    public List listarInscripciones() {
        return this.repositorio.buscarTodos(Inscripcion.class);
    }

    public Inscripcion buscarInscripcion(long id) {
        return this.repositorio.buscar(Inscripcion.class, id);
    }

    public void agregarInscripcion(boolean isExpositor, boolean isPrescencial,
            Persona persona, EdicionConferencia edicion) {
        this.repositorio.iniciarTransaccion();
        var inscripcion = new Inscripcion(isExpositor, isPrescencial, persona, edicion);
        this.repositorio.insertar(inscripcion);
        this.repositorio.finalizarTransaccion();
    }

    public boolean actualizarInscripcion(long id, boolean isExpositor, boolean isPrescencial,
            Persona persona, EdicionConferencia edicion) {
        this.repositorio.iniciarTransaccion();
        Inscripcion inscripcion = this.repositorio.buscar(Inscripcion.class, id);
        if (inscripcion != null) {
            inscripcion.setEdicion(edicion);
            inscripcion.setIsExpositor(isExpositor);
            inscripcion.setIsPresencial(isPrescencial);
            inscripcion.setPersona(persona);
            inscripcion.setEdicion(edicion);
            this.repositorio.modificar(inscripcion);
            this.repositorio.finalizarTransaccion();
            return true;
        } else {
            this.repositorio.rollbackTransaccion();
            return false;
        }
    }

    public boolean eliminarInscripcion(long id) {
        this.repositorio.iniciarTransaccion();
        Inscripcion inscripcion = this.repositorio.buscar(Inscripcion.class, id);
        if (inscripcion != null) {
            this.repositorio.eliminar(inscripcion);
            this.repositorio.finalizarTransaccion();
            return true;
        } else {
            this.repositorio.rollbackTransaccion();
        }
        return false;
    }

    public Persona buscarPersona(String dni) {
        return this.repositorio.buscar(Persona.class, dni);
    }

    public List listarPersonas() {
        return this.repositorio.buscarTodos(Persona.class);
    }

    public void agregarPersona(String nombre, String apellido, String dni,
            LocalDate fechaNac, EntidadTrabajo entidad) {
        this.repositorio.iniciarTransaccion();
        var persona = new Persona(nombre.toUpperCase(),
                apellido.toUpperCase().trim(), dni.toUpperCase().trim(), fechaNac);
        persona.setEntidadTrabajo(entidad);
        this.repositorio.insertar(persona);
        this.repositorio.finalizarTransaccion();
    }

    public boolean actualizarPersona(String DNI, String nombre, String ape, LocalDate fechaNac,
            EntidadTrabajo entidad) {
        this.repositorio.iniciarTransaccion();
        Persona per = this.repositorio.buscar(Persona.class, DNI);
        if (per != null) {
            per.setApellido(ape.toUpperCase().trim());
            per.setfechaNacimiento(fechaNac);
            per.setEntidadTrabajo(entidad);
            per.setNombre(nombre.toUpperCase().trim());
            this.repositorio.modificar(per);
            this.repositorio.finalizarTransaccion();
            return true;
        } else {
            this.repositorio.rollbackTransaccion();
            return false;
        }
    }

    public boolean eliminarPersona(String DNI) {
        this.repositorio.iniciarTransaccion();
        Persona per = this.repositorio.buscar(Persona.class, DNI);
        if (per != null && per.getInscripciones().isEmpty()) {
            this.repositorio.eliminar(per);
            this.repositorio.finalizarTransaccion();
            return true;
        } else {
            this.repositorio.rollbackTransaccion();
            return false;
        }
    }
}
