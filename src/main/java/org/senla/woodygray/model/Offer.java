package org.senla.woodygray.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Currency;
import java.util.Date;

@Data
@Entity
@Table(name = "offer", schema = "public")
public class Offer {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long ig;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offer_status")
    private OfferStatus offerStatus;
    @Column(name = "promotion_start")
    private Date promotionBegin;
    @Column(name = "promotion_end")
    private Date promotionEnd;
    private String title;
    private String description;
    private Double price;
}
