package com.epam.esm.entity;

import com.epam.esm.validation.annotation.EnglishLanguage;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Tag implements Serializable{
    public static final long serialVersionUID = 5476611966156520246L;

    private long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    private List<GiftCertificate> giftCertificates;

    public Tag() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    public void addGiftCertificate(GiftCertificate giftCertificate){
        if(giftCertificate != null){
            giftCertificates.add(giftCertificate);
        }
    }

    public void deleteGiftCertificate(GiftCertificate giftCertificate){
        if(giftCertificate != null){
            giftCertificates.removeIf(g -> g.getId() == giftCertificate.getId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return getId() == tag.getId() &&
                Objects.equals(getName(), tag.getName()) &&
                Objects.equals(getGiftCertificates(), tag.getGiftCertificates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getGiftCertificates());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", giftCertificates=" + giftCertificates +
                '}';
    }
}
