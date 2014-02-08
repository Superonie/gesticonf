package fr.dr02.gesticonf.jee.ejb;

import fr.dr02.gesticonf.jpa.ConferenceEntity;
import fr.dr02.gesticonf.jpa.DeviceEntity;

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
 * Created by damien on 08/02/14.
 */
@Stateless
@Named
@WebService
public class DeviceManager {

    @PersistenceUnit(unitName = "gesticonf")
    private EntityManagerFactory emf;

    @WebMethod
    // Renvoie une collection contenant tous les devices existants
    public Collection<DeviceEntity> findAll() {
        Query query = emf.createEntityManager().createQuery("SELECT d FROM DeviceEntity d");
        return (Collection<DeviceEntity>) query.getResultList();
    }

    @WebMethod
    // Renvoie une collection contenant tous les idRegistration des devices existants
    public Collection<String> findAllRegistrationsIds() {
        Query query = emf.createEntityManager().createQuery("SELECT d FROM DeviceEntity d");

        List<String> l = new ArrayList<String>();
        for ( DeviceEntity de : (Collection<DeviceEntity>) query.getResultList() )
            l.add(de.getIdRegistration());

        return l;
    }

    @WebMethod
    // Rend persistant le device donné en paramètre
    public void ajouter(DeviceEntity deviceEntity) {
        EntityManager em = emf.createEntityManager();
        em.persist(deviceEntity);
    }

    @WebMethod
    // Supprime tous les devices persistants
    public void reset() {
        Query query = emf.createEntityManager().createQuery("DELETE FROM DeviceEntity");
        query.executeUpdate();
    }

}
