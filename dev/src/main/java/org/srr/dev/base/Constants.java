package org.srr.dev.base;

public class Constants {

    public static final String ERROR_PATH = "/srr/error/";
    public static final String ERROR_FIX = "/srr/fix/";

    public static final int NETWORK_STATE_IDLE = 0;
    public static final int NETWORK_STATE_WIFI = 1;
    public static final int NETWORK_STATE_CMNET = 2;
    public static final int NETWORK_STATE_CMWAP = 3;
    public static final int NETWORK_STATE_CTWAP = 4;
    public static int CURRENT_NETWORK_STATE_TYPE = NETWORK_STATE_IDLE;
}
