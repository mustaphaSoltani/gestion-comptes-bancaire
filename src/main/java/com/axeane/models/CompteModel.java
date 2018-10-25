package com.axeane.models;

import com.axeane.domain.enumuration.TypeMouvementEnum;

import java.util.Date;

public class CompteModel {
    private Double somme;
    private Date date;
    private TypeMouvementEnum typeMouvement;

    public CompteModel(Double somme, Date date, TypeMouvementEnum typeMouvement) {
        this.somme = somme;
        this.date = date;
        this.typeMouvement = typeMouvement;
    }


    public CompteModel() {

    }

    public TypeMouvementEnum getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvementEnum typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Double getSomme() {
        return somme;
    }

    public void setSomme(Double somme) {
        this.somme = somme;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
