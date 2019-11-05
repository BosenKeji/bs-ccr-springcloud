package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.TradeOrder;

import java.util.List;

/**
 * @author CAJR
 * @date 2019/11/1 4:54 下午
 */
public interface TradeOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeOrder record);

    int insertSelective(TradeOrder record);

    TradeOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeOrder record);

    int updateByPrimaryKey(TradeOrder record);

    List<TradeOrder>  findAll();

    List<TradeOrder>  findAllByOrderGroupId(int orderGroupId);
}