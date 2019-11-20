package cn.bosenkeji.utils;

import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayVO;
import cn.bosenkeji.vo.combo.UserProductComboVO;

import java.util.ArrayList;
import java.util.List;

public class ComboDOTransformToVOUtil {


    public static List<UserProductComboDayVO> transformUserProductComboDayDOToVO(List<UserProductComboDay> userProductComboDays) {

        List userProductComboDayVOs = new ArrayList();

        userProductComboDays.forEach(userProductComboDay -> {
            UserProductComboDayVO userProductComboDayVO = new UserProductComboDayVO();
            userProductComboDayVO.setId(userProductComboDay.getId());
            userProductComboDayVO.setCreatedAt(userProductComboDay.getCreatedAt());
            userProductComboDayVO.setNumber(userProductComboDay.getNumber());
            userProductComboDayVO.setUserProductComboId(userProductComboDay.getUserProductComboId());
            if(null != userProductComboDay.getUser()) {
                userProductComboDayVO.setUserId(userProductComboDay.getUserId());
                userProductComboDayVO.setTel(userProductComboDay.getUser().getTel());
            }
            if(null != userProductComboDay.getUserProductComboDayByAdmin()) {
                userProductComboDayVO.setOrderNumber(userProductComboDay.getUserProductComboDayByAdmin().getOrderNumber());
                userProductComboDayVO.setRemark(userProductComboDay.getUserProductComboDayByAdmin().getRemark());

                if(null != userProductComboDay.getUserProductComboDayByAdmin().getAdmin()) {
                    userProductComboDayVO.setAdminId(userProductComboDay.getUserProductComboDayByAdmin().getAdminId());
                    userProductComboDayVO.setAdminAccount(userProductComboDay.getUserProductComboDayByAdmin().getAdmin().getAccount());
                }
                if (null != userProductComboDay.getUserProductComboDayByAdmin().getComboDayByAdminReason() && null != userProductComboDay.getUserProductComboDayByAdmin().getComboDayByAdminReason().getReason()) {
                    userProductComboDayVO.setReasonId(userProductComboDay.getUserProductComboDayByAdmin().getReasonId());
                    userProductComboDayVO.setReasonName(userProductComboDay.getUserProductComboDayByAdmin().getComboDayByAdminReason().getReason().getName());
                }
            }

            userProductComboDayVOs.add(userProductComboDayVO);

        });

         return userProductComboDayVOs;
    }

    public static List<UserProductComboVO> transformUserProductComboDOToVO(List<UserProductCombo> userProductCombos) {

        ArrayList<UserProductComboVO> userProductComboVOS = new ArrayList<>();

        userProductCombos.forEach(userProductCombo -> {
            UserProductComboVO userProductComboVO = new UserProductComboVO();

            userProductComboVO.setId(userProductCombo.getId());
            userProductComboVO.setCreatedAt(userProductCombo.getCreatedAt());
            userProductComboVO.setRemainTime(userProductCombo.getRemainTime());

            if (null != userProductCombo.getProductCombo()) {
                userProductComboVO.setProductComboId(userProductCombo.getProductComboId());
                userProductComboVO.setProductComboName(userProductCombo.getProductCombo().getName());

                if (null != userProductCombo.getProductCombo().getProduct()) {
                    userProductComboVO.setProductId(userProductCombo.getProductCombo().getProductId());
                    userProductComboVO.setProductName(userProductCombo.getProductCombo().getProduct().getName());
                    userProductComboVO.setProductVersionName(userProductCombo.getProductCombo().getProduct().getVersionName());
                }
                if (null != userProductCombo.getUser()) {
                    userProductComboVO.setUserId(userProductCombo.getUserId());
                    userProductComboVO.setTel(userProductCombo.getUser().getTel());
                }
            }

            userProductComboVOS.add(userProductComboVO);

        });

        return userProductComboVOS;
    }
}
