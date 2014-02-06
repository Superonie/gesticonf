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
    public ConferenceEntity find(int id) {
        return emf.createEntityManager().find(ConferenceEntity.class,id);
    }

    @WebMethod
    public Collection<ConferenceEntity> findAll() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c");
        return (Collection<ConferenceEntity>) query.getResultList();
    }

    public ConferenceEntity findByName(String nom) {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c WHERE c.nomConference = '"+ nom + "'");
        return (ConferenceEntity) query.getSingleResult();
    }

    @WebMethod
    public Collection<String> findAllNames() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c");

        List<String> l = new ArrayList<String>();
        for ( ConferenceEntity ce : (Collection<ConferenceEntity>) query.getResultList() )
            l.add(ce.getNomConference());

        Collections.sort(l);

        return l;
    }

    @WebMethod
    public Collection<Integer> findIds() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c");

        List<Integer> l = new ArrayList<Integer>();
        for ( ConferenceEntity ce : (Collection<ConferenceEntity>) query.getResultList() )
            l.add(ce.getIdConference());

        Collections.sort(l);

        return l;
    }

    @WebMethod
    public int findIdAvailable() {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity c ORDER BY c.idConference DESC");
        ConferenceEntity ce = (ConferenceEntity) query.getResultList().get(0);
        return (1+ce.getIdConference());
    }


    public void ajouter(ConferenceEntity conferenceEntity) {
        EntityManager em = emf.createEntityManager();
        em.persist(conferenceEntity);
    }
}
