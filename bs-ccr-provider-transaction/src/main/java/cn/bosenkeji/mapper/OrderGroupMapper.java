package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.OrderGroupOther;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author CAJR
 * @date 2019/11/1 4:54 下午
 */
public interface OrderGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGroup record);

    int insertSelective(OrderGroup record);

    OrderGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGroup record);

    int updateByPrimaryKey(OrderGroup record);

    int updateStatusByPrimaryKey(@Param("id") int id, @Param("status") int status, @Param("updatedAt") Timestamp timestamp);

    List<OrderGroupOther> findAll(int coinPairChoiceId);

    List<OrderGroup> findAllByCoinPairChoiceIdAndIsEnd(@Param("coinPairChoiceId")int coinPairChoiceId,@Param("isEnd")int isEnd);

    int checkExistByCoinPairChoiceIdAndIsEnd(int coinPairChoiceId);

    int checkExistByCoinPairChoiceIdAndIsEnd1(@Param("coinPairChoiceId")int coinPairChoiceId,@Param("isEnd")int isEnd);

    int resultNotEndGroupId(int coinPairChoiceId);

    int checkExistById(int orderGroupId);

    int checkExistByName(String name);

    int getIdByName(String name);

    List<OrderGroup> partFindByCoinPairChoiceIds(@Param("coinPairChoiceIds")List<Integer> coinPairChoiceIds);

}