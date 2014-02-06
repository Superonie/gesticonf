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
        List<PresentationEntity> l = query.getResultList();
        if ( l.size() > 0 )
            return (l.get(0).getIdPresentation() + 1);
        else // il se peut qu'il n'y ait aucune donnée persistante dans la base
            return 0;
    }

    public void ajouter(PresentationEntity presentationEntity) {
        EntityManager em = emf.createEntityManager();
        em.persist(presentationEntity);
    }
}
