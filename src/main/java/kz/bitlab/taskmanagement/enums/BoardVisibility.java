package kz.bitlab.taskmanagement.enums;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum BoardVisibility {

    WORKSPACE,
    PRIVATE;

    public static Set<String> toSet() {
        return EnumSet.allOf(BoardVisibility.class)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
