package kz.bitlab.taskmanagement.enums;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum BoardMemberRole {

    BOARD_ADMIN,
    BOARD_MEMBER;

    public static Set<String> toSet() {
        return EnumSet.allOf(BoardMemberRole.class)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
