package com.pkiks1.passwordmanager.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Credential")
@Table(name = "credential")
public class Credential {

    @Id
    private String id;
    private String title;
    private String mail;
    private char[] password;
    private String note;
    @ManyToOne
    private User user;

    protected Credential() {}

    public Credential(String title, String mail, char[] password, String note, User user) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.mail = mail;
        this.password = password;
        this.note = note;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", mail='" + mail + '\'' +
                ", user=" + user +
                '}';
    }
}
