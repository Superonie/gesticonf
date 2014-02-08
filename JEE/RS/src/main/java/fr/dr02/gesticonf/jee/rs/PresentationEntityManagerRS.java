package fr.dr02.gesticonf.jee.rs;

import fr.dr02.gesticonf.jee.ejb.PresentationManager;
import fr.dr02.gesticonf.jpa.ConferenceEntity;
import fr.dr02.gesticonf.jpa.PresentationEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by damien on 07/02/14.
 */
@Path("presentationEntityManagerRS")
@RequestScoped
public class PresentationEntityManagerRS {

    @Inject
    PresentationManager presentationManager;

    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_JSON})
    // Renvoie une collection contenant toutes les présentations liées à la conférence dont l'id est égal à "index"
    public Collection<PresentationEntity> getPresentationsByConf(@PathParam("index") int index)
    {
        return presentationManager.findAllByConf(index);
    }
}
