package cn.bosenkeji.interfaces;

    /**
     * FileName: RedisInterface
     * Author: xivin
     * Date: 2019-08-19 11:59
     * Description: redis key
    */

    public interface RedisInterface {

        //用户
        String USER_REDIS_ID_KEY="ccr:user:id";

        //产品
        String PRODUCT_ID_KEY="ccr:product:id";
        String PRODUCT_LIST_KEY="ccr:product:list";

        //产品套餐
        String PRODUCT_COMBO_ID_KEY="ccr:productCombo:id";
        String PRODUCT_COMBO_LIST_KEY="ccr:productCombo:list";
        String PRODUCT_COMBO_LIST_PID_STATUS_KEY="ccr:productCombo:listByPidAndStatus";

        //用户套餐
        String USER_COMBO_ID_KEY="ccr:userCombo:id";

        //套餐时长
        String COMBO_DAY_ID_KEY="ccr:comboDay:id";
        String COMBO_DAY_LIST_UPC_ID_KEY="ccr:comboDay:listByUpcId";
        String COMBO_DAY_LIST_TEL_KEY="ccr:comboDay:listByTel";


}
