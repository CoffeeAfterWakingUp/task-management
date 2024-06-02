package kz.bitlab.taskmanagement.enums;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum TaskPriority {

    HIGH,
    MEDIUM,
    LOW;

    public static Set<String> toSet() {
        return EnumSet.allOf(TaskPriority.class)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
