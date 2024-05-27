package kz.bitlab.taskmanagement.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class LongParser {

    public static Long parse(String number) {
        try {
            return Long.parseLong(number);
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
