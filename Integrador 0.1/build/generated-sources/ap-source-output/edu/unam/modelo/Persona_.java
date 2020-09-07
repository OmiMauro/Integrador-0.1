package edu.unam.modelo;

import edu.unam.modelo.EntidadTrabajo;
import edu.unam.modelo.Inscripcion;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2020-09-07T10:04:11", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, LocalDate> fechaNacimiento;
    public static volatile SingularAttribute<Persona, String> apellido;
    public static volatile ListAttribute<Persona, Inscripcion> inscripciones;
    public static volatile SingularAttribute<Persona, EntidadTrabajo> entidadTrabajo;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, String> DNI;

}