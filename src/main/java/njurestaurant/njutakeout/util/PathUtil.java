package njurestaurant.njutakeout.util;

import java.io.File;

public class PathUtil {
    public final static String TEMP_FILE_NAME = "NJUTakeOut";

    public static String getTmpPath() {
        java.util.Properties properties = System.getProperties();
        String tempFileName = properties.getProperty("java.io.tmpdir");
        return tempFileName + TEMP_FILE_NAME;
    }

    public static String getStaticPath() {
        return new File("static/").getAbsolutePath() + "/";
    }

}
