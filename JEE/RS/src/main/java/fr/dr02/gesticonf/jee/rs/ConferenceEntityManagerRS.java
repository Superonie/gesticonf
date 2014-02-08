package fr.dr02.gesticonf.jee.rs;

import fr.dr02.gesticonf.jee.ejb.ConferenceManager;
import fr.dr02.gesticonf.jpa.ConferenceEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by damien on 06/02/14.
 */
@Path("conferenceEntityManagerRS")
@RequestScoped
public class ConferenceEntityManagerRS {

    @Inject
    ConferenceManager conferenceManager;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    // Renvoie une collection contenant toutes les conférences existantes
    public Collection<ConferenceEntity> findAll() {
        return conferenceManager.findAll();
    }

    @GET
    @Path("/{index}")
    @Produces({MediaType.APPLICATION_JSON})
    // Renvoie la collection dont l'id est "index"
    public ConferenceEntity getConference(@PathParam("index") int index)
    {
        return conferenceManager.find(index);
    }

}
