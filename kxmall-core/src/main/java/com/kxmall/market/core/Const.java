package com.kxmall.market.core;

import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2018-08-11
 * Time: 下午11:43
 */
public class Const {

    public static final List<Class> IGNORE_PARAM_LIST = new ArrayList<>();

    static {
        IGNORE_PARAM_LIST.add(Integer.class);
        IGNORE_PARAM_LIST.add(Long.class);
        IGNORE_PARAM_LIST.add(String.class);
        IGNORE_PARAM_LIST.add(Float.class);
        IGNORE_PARAM_LIST.add(Double.class);
        IGNORE_PARAM_LIST.add(Boolean.class);
    }

    public static final Integer CACHE_ONE_DAY = 60 * 60 * 24;

    public static final Integer CACHE_TWO_DAY = 60 * 60 * 24 * 2;

    public static final Integer CACHE_THREE_DAY = 60 * 60 * 24 * 3;

    public static final Integer CACHE_SEVEN_DAY = 60 * 60 * 24 * 7;

    public static final String USER_ACCESS_TOKEN = "ACCESSTOKEN";

    public static final String USER_REDIS_PREFIX = "USER_SESSION_";

    public static final String RIDER_ACCESS_TOKEN = "RIDERTOKEN";

    public static final String RIDER_REDIS_PREFIX = "RIDER_SESSION_";

    public static final String ADMIN_ACCESS_TOKEN = "ADMINTOKEN";

    public static final String ADMIN_REDIS_PREFIX = "ADMIN_SESSION_";

}
