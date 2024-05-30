package kz.bitlab.taskmanagement.enums;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum WorkspaceMemberRole {

    WORKSPACE_ADMIN,
    WORKSPACE_MEMBER;

    public static Set<String> toSet() {
        return EnumSet.allOf(WorkspaceMemberRole.class)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
