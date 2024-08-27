package org.senla.woodygray.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "offer_status", schema = "public")
@Data
public class OfferStatus {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private OfferStatusType statusType;
}
