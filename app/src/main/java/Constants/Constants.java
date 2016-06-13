package Constants;

import android.os.Environment;

public class Constants {

    public static final String APP_PATH = Environment.getExternalStorageDirectory().getPath() + "/zcoin/";
    public static final String IMAGE_PATH = APP_PATH + "image/";
    public static final String ERROR_PATH = APP_PATH + "error/";

    public static class BUNDLE_KEY {
        public static final String STR1 = "str1";
        public static final String STR2 = "str2";
        public static final String STR3 = "str3";
        public static final String STR4 = "str4";
        public static final String STR5 = "str5";
        public static final String STR6 = "str6";
        public static final String STR7 = "str7";
    }

    public static class IntentExtra{
        public static final String INTENT_SELECTED_PICTURE = "INTENT_SELECTED_PICTURE";
    }
}