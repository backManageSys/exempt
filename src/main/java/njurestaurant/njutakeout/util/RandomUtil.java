package njurestaurant.njutakeout.util;

import java.util.Date;

public class RandomUtil {
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateNonceStr() {
        return new Date().getTime() + "";
    }
}
