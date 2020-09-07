package edu.unam.modelo;

import edu.unam.modelo.EdicionConferencia;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2020-09-07T15:53:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Conferencia.class)
public class Conferencia_ { 

    public static volatile ListAttribute<Conferencia, EdicionConferencia> ediciones;
    public static volatile SingularAttribute<Conferencia, String> id;
    public static volatile SingularAttribute<Conferencia, String> temaDebate;
    public static volatile SingularAttribute<Conferencia, String> nombre;

}