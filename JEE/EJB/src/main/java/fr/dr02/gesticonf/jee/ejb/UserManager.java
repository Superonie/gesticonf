package fr.dr02.gesticonf.jee.ejb;

import fr.dr02.gesticonf.jpa.ConferenceEntity;
import fr.dr02.gesticonf.jpa.PresentationEntity;
import fr.dr02.gesticonf.jpa.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.ws.rs.GET;
import java.util.Collection;

/**
 * Created by damien on 15/02/14.
 */
@Stateless
@Named
@WebService
public class UserManager {

    @PersistenceUnit(unitName = "gesticonf")
    private EntityManagerFactory emf;

    @WebMethod
    public UserEntity findAdmin() {
        Query query = emf.createEntityManager().createQuery("SELECT u FROM UserEntity u WHERE u.login = 'admin'");
        return (UserEntity) query.getSingleResult();
    }

    @WebMethod
    @GET
    // Met à jour la base de données à partir de l'entité donnée en paramètre
    public void update(UserEntity userEntity) {
        emf.createEntityManager().merge(userEntity);
    }
}
