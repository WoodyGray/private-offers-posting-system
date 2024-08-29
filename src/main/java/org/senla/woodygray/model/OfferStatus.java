package org.senla.woodygray.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "offer_status", schema = "public")
@Data
public class OfferStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private OfferStatusType statusType;
}
