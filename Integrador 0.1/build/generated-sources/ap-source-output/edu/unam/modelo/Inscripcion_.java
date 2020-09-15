package edu.unam.modelo;

import edu.unam.modelo.EdicionConferencia;
import edu.unam.modelo.EntidadTrabajo;
import edu.unam.modelo.Persona;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2020-09-15T15:52:33", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Inscripcion.class)
public class Inscripcion_ { 

    public static volatile SingularAttribute<Inscripcion, Persona> persona;
    public static volatile SingularAttribute<Inscripcion, Boolean> isExpositor;
    public static volatile SingularAttribute<Inscripcion, EntidadTrabajo> entidad;
    public static volatile SingularAttribute<Inscripcion, Long> id;
    public static volatile SingularAttribute<Inscripcion, Boolean> isPresencial;
    public static volatile SingularAttribute<Inscripcion, LocalDate> fechaInscripcion;
    public static volatile SingularAttribute<Inscripcion, EdicionConferencia> edicion;

}