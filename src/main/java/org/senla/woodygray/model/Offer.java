package org.senla.woodygray.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "offer", schema = "public")
public class Offer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offer_status")
    private OfferStatus offerStatus;
    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Photo> photos;
    @Column(name = "promotion_start")
    private Date promotionBegin;
    @Column(name = "promotion_end")
    private Date promotionEnd;
    private String title;
    private String description;
    private Double price;

}
