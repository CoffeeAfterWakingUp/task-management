package kz.bitlab.taskmanagement.entity;

import jakarta.persistence.*;
import kz.bitlab.taskmanagement.entity.key.WorkspaceMemberKey;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMember {

    @EmbeddedId
    private WorkspaceMemberKey id;

    @ManyToOne
    @MapsId(value = "workspaceId")
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private WorkspaceMemberRole memberRole;

}
