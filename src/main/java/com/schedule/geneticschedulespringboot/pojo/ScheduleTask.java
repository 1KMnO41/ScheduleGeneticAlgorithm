package com.schedule.geneticschedulespringboot.pojo;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTask {
    private Integer id;
    private String courseId;
    private String campus;
    private String teacherId;
    private String classComposition;
    private String classId;
    private Integer schedulingPriority;
    private Integer classSize;
    private String weeklyHours;
    private Integer continuousPeriods;
    private String designatedRoomType;
    private String designatedRoom;
    private String designatedBuilding;
    private String designatedTime;
    private Integer fixedClassroomId;
    private String subjectType;

    public List<String> getclassComposition() {
        List<String> composition = new ArrayList<>();

        String[] split = classComposition.split(",");
        for (String s : split) {
            composition.add(s);
        }

        return composition;
    }

    public ScheduleTask() {
    }

    public ScheduleTask(String courseId, String teacherId, String classComposition, String classId, Integer schedulingPriority, Integer classSize, String campus, String weeklyHours, Integer continuousPeriods, String designatedRoomType, String designatedRoom, String designatedBuilding, String designatedTime) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.classComposition = classComposition;
        this.classId = classId;
        this.schedulingPriority = schedulingPriority;
        this.classSize = classSize;
        this.campus = campus;
        this.weeklyHours = weeklyHours;
        this.continuousPeriods = continuousPeriods;
        this.designatedRoomType = designatedRoomType;
        this.designatedRoom = designatedRoom;
        this.designatedBuilding = designatedBuilding;
        this.designatedTime = designatedTime;
    }

    /**
     * 获取
     * @return courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * 设置
     * @param courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * 获取
     * @return teacherId
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     * 设置
     * @param teacherId
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取
     * @return classComposition
     */
    public String getClassComposition() {
        return classComposition;
    }

    /**
     * 设置
     * @param classComposition
     */
    public void setClassComposition(String classComposition) {
        this.classComposition = classComposition;
    }

    /**
     * 获取
     * @return classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置
     * @param classId
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * 获取
     * @return schedulingPriority
     */
    public Integer getSchedulingPriority() {
        return schedulingPriority;
    }

    /**
     * 设置
     * @param schedulingPriority
     */
    public void setSchedulingPriority(Integer schedulingPriority) {
        this.schedulingPriority = schedulingPriority;
    }

    /**
     * 获取
     * @return classSize
     */
    public Integer getClassSize() {
        return classSize;
    }

    /**
     * 设置
     * @param classSize
     */
    public void setClassSize(Integer classSize) {
        this.classSize = classSize;
    }

    /**
     * 获取
     * @return campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * 设置
     * @param campus
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * 获取
     * @return weeklyHours
     */
    public String getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * 设置
     * @param weeklyHours
     */
    public void setWeeklyHours(String weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * 获取
     * @return continuousPeriods
     */
    public Integer getContinuousPeriods() {
        return continuousPeriods;
    }

    /**
     * 设置
     * @param continuousPeriods
     */
    public void setContinuousPeriods(Integer continuousPeriods) {
        this.continuousPeriods = continuousPeriods;
    }

    /**
     * 获取
     * @return designatedRoomType
     */
    public String getDesignatedRoomType() {
        return designatedRoomType;
    }

    /**
     * 设置
     * @param designatedRoomType
     */
    public void setDesignatedRoomType(String designatedRoomType) {
        this.designatedRoomType = designatedRoomType;
    }

    /**
     * 获取
     * @return designatedRoom
     */
    public String getDesignatedRoom() {
        return designatedRoom;
    }

    /**
     * 设置
     * @param designatedRoom
     */
    public void setDesignatedRoom(String designatedRoom) {
        this.designatedRoom = designatedRoom;
    }

    /**
     * 获取
     * @return designatedBuilding
     */
    public String getDesignatedBuilding() {
        return designatedBuilding;
    }

    /**
     * 设置
     * @param designatedBuilding
     */
    public void setDesignatedBuilding(String designatedBuilding) {
        this.designatedBuilding = designatedBuilding;
    }

    /**
     * 获取
     * @return designatedTime
     */
    public String getDesignatedTime() {
        return designatedTime;
    }

    /**
     * 设置
     * @param designatedTime
     */
    public void setDesignatedTime(String designatedTime) {
        this.designatedTime = designatedTime;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * 获取
     * @return fixedClassroomId
     */
    public Integer getFixedClassroomId() {
        return fixedClassroomId;
    }

    /**
     * 设置
     * @param fixedClassroomId
     */
    public void setFixedClassroomId(Integer fixedClassroomId) {
        this.fixedClassroomId = fixedClassroomId;
    }

    public String toString() {
        return "Task{courseId = " + courseId + ", teacherId = " + teacherId + ", classComposition = " + classComposition + ", classId = " + classId + ", schedulingPriority = " + schedulingPriority + ", classSize = " + classSize + ", campus = " + campus + ", weeklyHours = " + weeklyHours + ", continuousPeriods = " + continuousPeriods + ", designatedRoomType = " + designatedRoomType + ", designatedRoom = " + designatedRoom + ", designatedBuilding = " + designatedBuilding + ", designatedTime = " + designatedTime + "}";
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
