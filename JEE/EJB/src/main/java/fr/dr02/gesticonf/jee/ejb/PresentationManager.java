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
import java.util.Collection;

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
    public PresentationEntity find(int id) {
        return emf.createEntityManager().find(PresentationEntity.class,id);
    }

    @WebMethod
    public Collection<PresentationEntity> findAll() {
        Query query = emf.createEntityManager().createQuery("SELECT p FROM PresentationEntity p");
        return (Collection<PresentationEntity>) query.getResultList();
    }

    @WebMethod
    public int findIdAvailable() {
        Query query = emf.createEntityManager().createQuery("SELECT p FROM PresentationEntity p ORDER BY p.idPresentation DESC");
        PresentationEntity pe = (PresentationEntity) query.getResultList().get(0);
        return (1+pe.getIdPresentation());
    }

    public void ajouter(PresentationEntity presentationEntity) {
        EntityManager em = emf.createEntityManager();
        em.persist(presentationEntity);
    }
}
