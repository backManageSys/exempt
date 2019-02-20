package njurestaurant.njutakeout.util;

import java.util.UUID;

public class OrderUUIDUtil {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
