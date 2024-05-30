package kz.bitlab.taskmanagement.entity;

import jakarta.persistence.*;
import kz.bitlab.taskmanagement.entity.key.BoardMemberKey;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardMember {

    @EmbeddedId
    private BoardMemberKey id;

    @ManyToOne
    @MapsId(value = "boardId")
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BoardMemberRole boardMemberRole;
}
