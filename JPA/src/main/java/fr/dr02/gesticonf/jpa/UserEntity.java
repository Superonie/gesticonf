package fr.dr02.gesticonf.jpa;

import javax.persistence.*;

/**
 * Created by damien on 15/02/14.
 */
@Entity
@Table(name = "ADMIN", schema = "PUBLIC", catalog="GESTICONF")
public class UserEntity {

    @Id
    @Column(name= "LOGIN")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name= "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String login;
    private String password;



}
