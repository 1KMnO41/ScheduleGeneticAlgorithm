package com.schedule.geneticschedulespringboot.algorithm;

import java.util.List;

public class Task {
    private int id;
    private int classroomId;
    private int timeId;
    private int scheduleTaskId;
    private WeekType weekType;
    private WeekRange weekRange;
    private List<String> classListId;

    public boolean hasConflict(Task other) {
        if (weekType != WeekType.EVERY && other.weekType != WeekType.EVERY && weekType != other.weekType) {
            return false;
        }

        if (!hashWeekOverlap(other)) {
            return false;
        }

        return timeId == other.timeId;
    }

    private boolean hashWeekOverlap(Task other) {
        return !(weekRange.getEndWeek() < other.weekRange.getStartWeek() ||
                weekRange.getStartWeek() > other.weekRange.getEndWeek());
    }

    public Task() {
    }

    public Task(int id, int timeId, int classroomId, int scheduleTaskId, WeekType weekType, WeekRange weekRange, List<String> classListId) {
        this.id = id;
        this.classroomId = classroomId;
        this.timeId = timeId;
        this.scheduleTaskId = scheduleTaskId;
        this.weekType = weekType;
        this.weekRange = weekRange;
        this.classListId = classListId;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     * @return classroomId
     */
    public int getClassroomId() {
        return classroomId;
    }

    /**
     * 设置
     * @param classroomId
     */
    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * 获取
     * @return timeId
     */
    public int getTimeId() {
        return timeId;
    }

    /**
     * 设置
     * @param timeId
     */
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    /**
     * 获取
     * @return scheduleTaskId
     */
    public int getScheduleTaskId() {
        return scheduleTaskId;
    }

    /**
     * 设置
     * @param scheduleTaskId
     */
    public void setScheduleTaskId(int scheduleTaskId) {
        this.scheduleTaskId = scheduleTaskId;
    }

    /**
     * 获取
     * @return weekType
     */
    public WeekType getWeekType() {
        return weekType;
    }

    /**
     * 设置
     * @param weekType
     */
    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    /**
     * 获取
     * @return weekRange
     */
    public WeekRange getWeekRange() {
        return weekRange;
    }

    /**
     * 设置
     * @param weekRange
     */
    public void setWeekRange(WeekRange weekRange) {
        this.weekRange = weekRange;
    }

    /**
     * 获取
     * @return classListId
     */
    public List<String> getClassListId() {
        return classListId;
    }

    /**
     * 设置
     * @param classListId
     */
    public void setClassListId(List<String> classListId) {
        this.classListId = classListId;
    }

    public String toString() {
        return "Task{id = " + id + ", classroomId = " + classroomId + ", timeId = " + timeId + ", scheduleTaskId = " + scheduleTaskId + ", weekType = " + weekType + ", weekRange = " + weekRange + ", classListId = " + classListId + "}";
    }
}
