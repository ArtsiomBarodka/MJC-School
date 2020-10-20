package entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class GiftCertificate implements Serializable {
    public static final long serialVersionUID = 5476611966156520246L;

    private long id;
    private String description;
    private double price;
    private String createDate;
    private String lastUpdateDate;
    private int duration;
}
