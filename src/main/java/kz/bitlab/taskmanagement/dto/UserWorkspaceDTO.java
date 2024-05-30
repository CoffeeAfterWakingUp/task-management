package kz.bitlab.taskmanagement.dto;

import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkspaceDTO {

    private WorkspaceDTO workspaceDTO;
    private WorkspaceMemberRole memberRole;
}
