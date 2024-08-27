package org.senla.woodygray.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offer")
    private Offer offer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender")
    private User sender;
    @Column(name = "message_text")
    private String messageText;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}
