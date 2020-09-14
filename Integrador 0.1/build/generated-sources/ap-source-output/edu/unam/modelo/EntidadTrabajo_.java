package edu.unam.modelo;

import edu.unam.modelo.Persona;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2020-09-14T15:46:19", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(EntidadTrabajo.class)
public class EntidadTrabajo_ { 

    public static volatile SingularAttribute<EntidadTrabajo, String> CUIT;
    public static volatile SingularAttribute<EntidadTrabajo, Boolean> isPublica;
    public static volatile SingularAttribute<EntidadTrabajo, Long> id;
    public static volatile SingularAttribute<EntidadTrabajo, String> nombre;
    public static volatile ListAttribute<EntidadTrabajo, Persona> personas;

}