package kz.bitlab.taskmanagement.entity;

import jakarta.persistence.*;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Workspace workspace;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private BoardVisibility visibility;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BoardMember> members;

    @ManyToMany(mappedBy = "favoriteBoards")
    private Set<User> favoritedUsers = new HashSet<>();

    public Board() {
        this.createdTime = LocalDateTime.now();
    }

    public void addCard(Card card) {
        if (cards == null) cards = new ArrayList<>();
        cards.add(card);
        card.setBoard(this);
    }
}
