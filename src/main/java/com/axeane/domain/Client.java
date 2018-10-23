package com.axeane.domain;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Client
 */
@Entity
@Table(name = "ax_client")
public class Client implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = {Views.ClientView.class})
    private Long id;

    @Column(name = "cin",unique = true)
    @JsonView(value = {Views.ClientView.class})
    private Integer cin;

    @Column(name = "nom")
    @JsonView(value = {Views.ClientView.class})
    private String nom;

    @Column(name = "prenom")
    @JsonView(value = {Views.ClientView.class})
    private String prenom;

    @Column(name = "adresse")
    @JsonView(value = {Views.ClientView.class})
    private String adresse;

    @Column(name = "email")
    @JsonView(value = {Views.ClientView.class})
    private String email;

    @Column(name = "num_tel")
    @JsonView(value = {Views.ClientView.class})
    private Integer numTel;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    @JsonView(value = {Views.ClientView.class})
    private Set<Compte> comptes = new HashSet<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public Integer getNumTel() {
        return numTel;
    }

    public void setNumTel(Integer numTel) {
        this.numTel = numTel;
    }

    public Set<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if (client.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "client{" +
                "id=" + id +
                ", nom='" + nom + "'" +
                ", prenom='" + prenom + "'" +
                ", adress='" + adresse + "'" +
                ", email='" + email + "'" +
                ", numTel='" + numTel + "'" +
                '}';
    }
}
