package cn.bosenkeji.util;

import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * @author CAJR
 * @date 2019/11/4 2:14 下午
 * 公共常量工具类
 */
public class CommonConstantUtil {
    public final static int SUCCESS = 1;
    public final static int FAIL = 0;
    public final static int VERIFY_FAIL = -1;

    public final static int DELETE_STATUS = 0;
    public final static int ACTIVATE_STATUS = 1;

    public static final int ACCURACY = 100;

    public static final ArrayList<String> openSearchFetchFieldFormat = Lists.newArrayList("order_group_id","coin_pair_choice_id","name","created_time");

}