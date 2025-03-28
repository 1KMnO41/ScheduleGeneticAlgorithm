package com.schedule.geneticschedulespringboot;

import com.schedule.geneticschedulespringboot.algorithm.SoftConstraintCalculator;
import com.schedule.geneticschedulespringboot.algorithm.TimetableGA;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@MapperScan("com.schedule.geneticschedulespringboot.mappers")
class GeneticScheduleSpringboot2ApplicationTests {

    @Test
    void TasksTest() {
        TimetableGA ga = new TimetableGA();
        try {
            SoftConstraintCalculator calculator = new SoftConstraintCalculator();

            // 软约束启用状态
            // 控制是否启用同一课程使用相同教室的约束，启用后会鼓励将同一门课安排在同一个教室
            //calculator.setEnableSameRoomConstraint(true);

            // 控制是否启用教室利用率集中度约束，启用后会鼓励教室使用次数均衡分布
            //calculator.setEnableRoomUtilizationConstraint(true);

            // 控制是否启用教师排课教室集中度约束，启用后会鼓励教师的课程集中在少数几个教室
            //calculator.setEnableTeacherRoomConcentrationConstraint(true);

            // 控制是否启用班级固定教室约束，启用后会鼓励将班级的课程安排在其固定教室
            //calculator.setEnableFixedClassroomConstraint(true);

            // 控制是否启用教师工作量约束，包括每天最大课时数、每周最大课时数和上下午课时分配平衡
            //calculator.setEnableTeacherWorkloadConstraint(true);

            // 控制是否启用体育课程约束，包括下午时段安排和课后不安排其他课程
            //calculator.setEnablePEConstraint(true);

            // 控制是否启用晚上课程约束，启用后会避免在晚上时段（第9-12节）安排课程
            //calculator.setEnableEveningClassConstraint(true);

            // 控制是否启用实验课程约束，启用后会鼓励在晚上时段安排实验课
            //calculator.setEnableLabClassConstraint(true);

            // 控制是否启用多学时课程连续性约束，启用后会鼓励多学时课程（理论、实验、上机）连续安排
            //calculator.setEnableMultiPeriodContinuityConstraint(true);

            ga.run(calculator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
