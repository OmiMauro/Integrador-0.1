package edu.unam.modelo;

import edu.unam.modelo.Conferencia;
import edu.unam.modelo.Inscripcion;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2020-09-12T07:37:05", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(EdicionConferencia.class)
public class EdicionConferencia_ { 

    public static volatile SingularAttribute<EdicionConferencia, LocalDate> fechaInicio;
    public static volatile ListAttribute<EdicionConferencia, Inscripcion> inscripciones;
    public static volatile SingularAttribute<EdicionConferencia, Conferencia> conferencia;
    public static volatile SingularAttribute<EdicionConferencia, String> direccion;
    public static volatile SingularAttribute<EdicionConferencia, Long> id;
    public static volatile SingularAttribute<EdicionConferencia, LocalDate> fechaFin;

}