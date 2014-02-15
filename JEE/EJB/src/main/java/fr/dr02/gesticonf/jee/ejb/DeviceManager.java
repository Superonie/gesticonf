package fr.dr02.gesticonf.jee.ejb;

import fr.dr02.gesticonf.jpa.ConferenceEntity;
import fr.dr02.gesticonf.jpa.DeviceEntity;
import fr.dr02.gesticonf.jpa.PresentationEntity;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.*;
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
    // Renvoie une collection contenant tous les idRegistration des devices existants et suivant la conférence représentée par refConf
    public Collection<String> findAllRegistrationsIdsByConf(int refConf) {
        Query query = emf.createEntityManager().createQuery("SELECT d FROM DeviceEntity d WHERE d.refConference = "+refConf);

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

    @WebMethod
    public String findIdAvailable() {
        Query query = emf.createEntityManager().createQuery("SELECT d FROM DeviceEntity d ORDER BY d.idGlobal DESC");
        List<DeviceEntity> l = query.getResultList();
        if ( l.size() > 0 )
            return (l.get(0).getIdGlobal() + 1)+"";
        else // il se peut qu'il n'y ait aucune donnée persistante dans la base
            return 0+"";
    }

    @WebMethod
    public DeviceEntity findByIdConfIdDevice(String idConf, String idDevice) {
        Integer refConf = Integer.valueOf(idConf);
        Query query = emf.createEntityManager().createQuery("SELECT d FROM DeviceEntity d WHERE d.idDevice ='" + idDevice + "' AND d.refConference =" + refConf);

        try {
         return (DeviceEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @WebMethod
    public Collection<ConferenceEntity> findConferenceByIdDevice(String idDevice) {
        Query query = emf.createEntityManager().createQuery("SELECT c FROM ConferenceEntity AS c INNER JOIN DeviceEntity AS d ON d.refConference = c.idConference WHERE d.idDevice ='" + idDevice + "'");
        return query.getResultList();
    }
}
