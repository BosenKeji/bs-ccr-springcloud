package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.cdKey.CdKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CdKeyMapper {

    int insert(CdKey cdKey);

    int insertSelective(CdKey cdKey);

    int insertCdKeyByBatch(List<CdKey> cdKeys);

    int updateUsernameAndStatusById(@Param("id") Integer id, @Param("username") String username, @Param("status") Integer status);

    int deleteByIds(@Param("ids") List<Integer> ids);

    List<CdKey> getByIds(@Param("ids") List<Integer> ids);

    CdKey getById(@Param("id") Integer id);

    List<CdKey> get();
}