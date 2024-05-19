package kz.bitlab.taskmanagement.entity;

import jakarta.persistence.*;
import kz.bitlab.taskmanagement.entity.key.WorkspaceMemberKey;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
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

    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WorkspaceMemberRole memberRole;

}
