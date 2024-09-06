package org.senla.woodygray.model;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seller")
    private User seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender")
    private User sender;
    @Column(name = "message_text")
    private String messageText;

    private Integer grade;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}
