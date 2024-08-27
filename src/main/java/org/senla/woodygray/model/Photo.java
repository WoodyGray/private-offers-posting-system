package org.senla.woodygray.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offer")
    private Offer offer;
    @Column(name = "file_path")
    private String filePath;
}
