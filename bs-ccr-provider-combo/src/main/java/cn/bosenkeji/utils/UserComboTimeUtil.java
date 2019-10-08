package cn.bosenkeji.utils;

import cn.bosenkeji.UserComboRedisEnum;

public class UserComboTimeUtil {

    /**
     *  通过 ID 计算相应的键   直接用ID 除以 5000 取整
     * @param id
     * @return
     */
    public static String getRedisKeyById(int id) {
        return UserComboRedisEnum.UserComboTime+"_"+CalculateUtil.dividedBy5000(id);
    }
}
