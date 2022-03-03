package com.barry.util;

import java.util.Base64;

public interface ParamUtils {


    /**
     * encode the secret in base64 to avoid having it visibly available in the jvm
     */
    public static final String SECRET_KEY = Base64.getEncoder().encodeToString("mySecretKey".getBytes());
}
