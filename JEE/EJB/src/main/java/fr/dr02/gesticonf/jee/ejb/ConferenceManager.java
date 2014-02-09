package fr.dr02.gesticonf.jee.ejb;

import fr.dr02.gesticonf.jpa.ConferenceEntity;
import fr.dr02.gesticonf.jpa.PresentationEntity;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by damien on 05/02/14.
 */
@Stateless
@Named
@WebService
public class ConferenceManager {

    @PersistenceUnit(unitName = "gesticonf")
    private EntityManagerFactory emf;

    @WebMethod
    // Renvoie la conférence dont l'id est égal à "id"
    public ConferenceEntity find(int id) {
        return emf.createEntityManager().find(ConferenceEntity.class,id);
    }

    @WebMethod
    // Renvoie une collection contenant toutes les conférences
    public Collection<ConferenceEntity> findAll() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c");
        return (Collection<ConferenceEntity>) query.getResultList();
    }

    // Renvoie la conférence dont le nom est égal à "nom"
    public ConferenceEntity findByName(String nom) {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c WHERE c.nomConference = '"+ nom + "'");
        return (ConferenceEntity) query.getSingleResult();
    }

    @WebMethod
    // Renvoie la liste de tous les noms de conférences triés par ordre alpha-numérique
    public Collection<String> findAllNames() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c");

        List<String> l = new ArrayList<String>();
        for ( ConferenceEntity ce : (Collection<ConferenceEntity>) query.getResultList() )
            l.add(ce.getNomConference());

        Collections.sort(l);

        return l;
    }

    @WebMethod
    // Renvoie la liste de tous les identifiants de conférences triés par ordre croissant
    public Collection<Integer> findIds() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c");

        List<Integer> l = new ArrayList<>();
        for ( ConferenceEntity ce : (Collection<ConferenceEntity>) query.getResultList() )
            l.add(ce.getIdConference());

        Collections.sort(l);

        return l;
    }

    @WebMethod
    // Renvoie un identifiant utilisable pour rendre persistant une nouvelle conférence.
    // Concrètement, on ajoute au maximum des ids trouvés dans la base de données.
    public int findIdAvailable() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c ORDER BY c.idConference DESC");
        List<ConferenceEntity> l = query.getResultList();
        if ( l.size() > 0 )
            return (l.get(0).getIdConference() + 1);
        else // il se peut qu'il n'y ait aucune donnée persistante dans la base
            return 0;
    }

    // Rend persistant une conférence
    public void ajouter(ConferenceEntity conferenceEntity) {
        EntityManager em = emf.createEntityManager();
        em.persist(conferenceEntity);
    }

    // Supprime toutes les conférences persistantes
    public void reset() {
        Query query = emf.createEntityManager().createQuery("DELETE FROM ConferenceEntity c");
        query.executeUpdate();
    }
}
