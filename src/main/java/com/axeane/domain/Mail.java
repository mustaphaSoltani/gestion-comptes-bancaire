package com.axeane.domain;

import javax.persistence.Id; // to be deleted
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Mail { // nom non adapté

    @NotNull
    @Size(max = 20)
    private String titre;

    @Size(max = 20)
    private String objet;

    @Size(max = 20)
    private String message;

    private String text;

    @NotNull
    private String urlFile;

    @NotNull
    private String email;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "titre='" + titre + '\'' +
                ", objet='" + objet + '\'' +
                ", message='" + message + '\'' +
                ", text='" + text + '\'' +
                ", urlFile='" + urlFile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
