package com.schedule.geneticschedulespringboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.geneticschedulespringboot.pojo.ScheduleTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<ScheduleTask> {
    List<ScheduleTask> selectAllTask();
}
