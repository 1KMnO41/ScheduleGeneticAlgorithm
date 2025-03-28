package com.schedule.geneticschedulespringboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "软约束配置DTO")
public class SoftConstraintDTO {
    @Schema(description = "是否启用同一课程使用相同教室")
    private boolean enableSameRoomForCourse = true;

    @Schema(description = "是否启用教室利用率平衡")
    private boolean enableRoomUtilizationBalance = true;

    @Schema(description = "是否启用教师教室集中度")
    private boolean enableTeacherRoomConcentration = true;

    public boolean isEnableSameRoomForCourse() {
        return enableSameRoomForCourse;
    }

    public void setEnableSameRoomForCourse(boolean enableSameRoomForCourse) {
        this.enableSameRoomForCourse = enableSameRoomForCourse;
    }

    public boolean isEnableRoomUtilizationBalance() {
        return enableRoomUtilizationBalance;
    }

    public void setEnableRoomUtilizationBalance(boolean enableRoomUtilizationBalance) {
        this.enableRoomUtilizationBalance = enableRoomUtilizationBalance;
    }

    public boolean isEnableTeacherRoomConcentration() {
        return enableTeacherRoomConcentration;
    }

    public void setEnableTeacherRoomConcentration(boolean enableTeacherRoomConcentration) {
        this.enableTeacherRoomConcentration = enableTeacherRoomConcentration;
    }
}