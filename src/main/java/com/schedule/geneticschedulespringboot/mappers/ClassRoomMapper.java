package com.schedule.geneticschedulespringboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.geneticschedulespringboot.pojo.ClassRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassRoomMapper extends BaseMapper<ClassRoom> {
    List<ClassRoom> selectRoom(@Param("campus") String campus, @Param("roomType") String roomType);

    List<ClassRoom> selectAllRoom();
}
