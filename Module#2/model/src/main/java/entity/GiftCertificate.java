package entity;

import lombok.Data;
import validation.annotation.EnglishLanguage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class GiftCertificate implements Serializable {
    public static final long serialVersionUID = 5476611966156520246L;

    private long id;

    @NotNull
    @Size(min = 3, max = 20)
    @EnglishLanguage(withPunctuations = false)
    private String name;

    @NotNull
    @EnglishLanguage
    private String description;

    @Positive
    private double price;

    private String createDate;

    private String lastUpdateDate;

    @Positive
    private int duration;

    private List<Tag> tags;
}
