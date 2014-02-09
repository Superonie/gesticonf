package fr.dr02.gesticonf.jee.ejb;

import fr.dr02.gesticonf.jpa.PresentationEntity;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.ws.rs.GET;
import java.util.Collection;
import java.util.List;

/**
 * Created by damien on 04/02/14.
 */
@Stateless
@Named
@WebService
public class PresentationManager {

    @PersistenceUnit(unitName = "gesticonf")
    private EntityManagerFactory emf;

    @WebMethod
    // Renvoie la présentation dont l'id est égal à "id"
    public PresentationEntity find(int id) {
        return emf.createEntityManager().find(PresentationEntity.class,id);
    }

    @WebMethod
    // Renvoie une collection contenant toutes les présentations existantes.
    public Collection<PresentationEntity> findAll() {
        Query query = emf.createEntityManager().createQuery("SELECT p FROM PresentationEntity p");
        return (Collection<PresentationEntity>) query.getResultList();
    }

    @WebMethod
    // Renvoie un identifiant utilisable pour rendre persistant une nouvelle conférence.
    // Concrètement, on ajoute au maximum des ids trouvés dans la base de données.
    public int findIdAvailable() {
        Query query = emf.createEntityManager().createQuery("SELECT p FROM PresentationEntity p ORDER BY p.idPresentation DESC");
        List<PresentationEntity> l = query.getResultList();
        if ( l.size() > 0 )
            return (l.get(0).getIdPresentation() + 1);
        else // il se peut qu'il n'y ait aucune donnée persistante dans la base
            return 0;
    }

    // Rend persistant la présentation donnée en paramètre
    public void ajouter(PresentationEntity presentationEntity) {
        EntityManager em = emf.createEntityManager();
        em.persist(presentationEntity);
    }

    @WebMethod
    // Renvoie toutes les présentations liées à la conférence dont l'id est égal à "idConf"
    public Collection<PresentationEntity> findAllByConf(int idConf) {
        Query query = emf.createEntityManager().createQuery("SELECT p FROM PresentationEntity p WHERE p.refConference ="+idConf);
        return (Collection<PresentationEntity>) query.getResultList();
    }

    @WebMethod
    @GET
    // Met à jour la base de données à partir de l'entité donnée en paramètre
    public void update(PresentationEntity presentationEntity) {
        emf.createEntityManager().merge(presentationEntity);
    }

    @WebMethod
    @GET
    // Met à jour la base de données à partir de l'entité donnée en paramètre
    public void delete(PresentationEntity presentationEntity) {
        EntityManager em = emf.createEntityManager();
        PresentationEntity toBeRemoved = em.merge(presentationEntity);
        em.remove(toBeRemoved);
        em.flush();
    }

    @WebMethod
    // Supprime tous les présentations persistantes
    public void delete(int index) {
        Query query = emf.createEntityManager().createQuery("DELETE FROM PresentationEntity p WHERE p.idPresentation ="+index);
        query.executeUpdate();
    }

    @WebMethod
    // Supprime tous les présentations persistantes
    public void reset() {
        Query query = emf.createEntityManager().createQuery("DELETE FROM PresentationEntity p");
        query.executeUpdate();
    }


}