package com.schedule.geneticschedulespringboot.algorithm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "课程冲突信息")
public class Conflict {
    @Schema(description = "冲突类型：1-教室容量冲突，2-教室时间冲突，3-教师时间冲突，4-班级时间冲突")
    private int type;
    
    @Schema(description = "第一个冲突课程")
    private Task task1;
    
    @Schema(description = "第二个冲突课程，教室容量冲突时为null")
    private Task task2;
}