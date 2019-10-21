package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.Job;

public interface JobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Job record);

    int insertSelective(Job record);

    Job selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Job record);

    int updateByPrimaryKey(Job record);

    Job selectByJobName(String jobName);
    int checkExistByJobName(String jobName);
}