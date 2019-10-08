package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.Job;

import java.util.List;

public interface JobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Job record);


    Job selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Job record);

    int updateByPrimaryKey(Job record);

    List<Job> findAll();

    Job selectByJobName(String jobName);


}