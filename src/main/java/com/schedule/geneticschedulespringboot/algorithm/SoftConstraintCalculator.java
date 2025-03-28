package com.schedule.geneticschedulespringboot.algorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.schedule.geneticschedulespringboot.pojo.ScheduleTask;

@Data
public class SoftConstraintCalculator {
    // 同一课程使用相同教室的奖励值
    private static double SAME_ROOM_REWARD = 1.5;
    // 教室利用率集中度奖励系数
    private static double ROOM_UTILIZATION_REWARD = 3.0;
    // 教师排课教室集中度奖励系数
    private static double TEACHER_ROOM_CONCENTRATION_REWARD = 2.5;
    // 班级固定教室约束奖励系数
    private static double FIXED_CLASSROOM_REWARD = 2.5;
    // 教师每天最大课时数约束奖励系数
    private static double TEACHER_DAILY_WORKLOAD_REWARD = 2.0;
    // 教师每周最大课时数约束奖励系数
    private static double TEACHER_WEEKLY_WORKLOAD_REWARD = 2.0;
    // 教师上下午课时分配约束奖励系数
    private static double TEACHER_DAYPART_BALANCE_REWARD = 2.0;
    // 体育课程下午时段约束奖励系数
    private static double PE_AFTERNOON_REWARD = 2.5;
    // 体育课后不安排课程约束奖励系数
    private static double PE_NO_FOLLOWING_CLASS_REWARD = 1.5;
    // 晚上课程约束奖励系数
    private static double EVENING_CLASS_REWARD = 0.1;
    // 实验课程晚上时段约束奖励系数
    private static double LAB_CLASS_EVENING_REWARD = 0.4;
    // 多学时课程连续性约束奖励系数
    private static double MULTI_PERIOD_CONTINUITY_REWARD = 1.2;

    // 软约束启用状态
    // 控制是否启用同一课程使用相同教室的约束，启用后会鼓励将同一门课安排在同一个教室
    private boolean enableSameRoomConstraint;
    // 控制是否启用教室利用率集中度约束，启用后会鼓励教室使用次数均衡分布
    private boolean enableRoomUtilizationConstraint;
    // 控制是否启用教师排课教室集中度约束，启用后会鼓励教师的课程集中在少数几个教室
    private boolean enableTeacherRoomConcentrationConstraint;
    // 控制是否启用班级固定教室约束，启用后会鼓励将班级的课程安排在其固定教室
    private boolean enableFixedClassroomConstraint;
    // 控制是否启用教师工作量约束，包括每天最大课时数、每周最大课时数和上下午课时分配平衡
    private boolean enableTeacherWorkloadConstraint;
    // 控制是否启用体育课程约束，包括下午时段安排和课后不安排其他课程
    private boolean enablePEConstraint;
    // 控制是否启用晚上课程约束，启用后会避免在晚上时段（第9-12节）安排课程
    private boolean enableEveningClassConstraint;
    // 控制是否启用实验课程约束，启用后会鼓励在晚上时段安排实验课
    private boolean enableLabClassConstraint;
    // 控制是否启用多学时课程连续性约束，启用后会鼓励多学时课程（理论、实验、上机）连续安排
    private boolean enableMultiPeriodContinuityConstraint;

    public SoftConstraintCalculator(boolean enableSameRoomConstraint,
                                  boolean enableRoomUtilizationConstraint,
                                  boolean enableTeacherRoomConcentrationConstraint,
                                  boolean enableFixedClassroomConstraint,
                                  boolean enableTeacherWorkloadConstraint,
                                  boolean enablePEConstraint,
                                  boolean enableEveningClassConstraint,
                                  boolean enableLabClassConstraint,
                                  boolean enableMultiPeriodContinuityConstraint) {
        this.enableSameRoomConstraint = enableSameRoomConstraint;
        this.enableRoomUtilizationConstraint = enableRoomUtilizationConstraint;
        this.enableTeacherRoomConcentrationConstraint = enableTeacherRoomConcentrationConstraint;
        this.enableFixedClassroomConstraint = enableFixedClassroomConstraint;
        this.enableTeacherWorkloadConstraint = enableTeacherWorkloadConstraint;
        this.enablePEConstraint = enablePEConstraint;
        this.enableEveningClassConstraint = enableEveningClassConstraint;
        this.enableLabClassConstraint = enableLabClassConstraint;
        this.enableMultiPeriodContinuityConstraint = enableMultiPeriodContinuityConstraint;
    }

    public SoftConstraintCalculator() {
        this(false, false, false,
                false, false, false,
                false, false, false);
    }

    /**
     * 计算同一课程使用相同教室的奖励
     */
    // - 使用 Map<Integer, Set<Integer>> 存储每个课程（scheduleTaskId）使用的所有教室
    // - 遍历所有任务，将每个任务的教室ID添加到对应课程的教室集合中
    // - 如果某个课程只使用了一个教室（rooms.size() == 1），就给予 SAME_ROOM_REWARD（1.5）的奖励
    // - 这样可以鼓励排课系统尽量将同一门课安排在同一个教室
    public double calculateSameRoomReward(Timetable timetable) {
        if (!enableSameRoomConstraint) return 0.0;
        double reward = 0.0;
        Map<Integer, Set<Integer>> courseRooms = new HashMap<>();
        
        for (Task task : timetable.getTasks()) {
            int scheduleTaskId = task.getScheduleTaskId();
            courseRooms.computeIfAbsent(scheduleTaskId, k -> new HashSet<>())
                    .add(task.getClassroomId());
        }
        
        for (Set<Integer> rooms : courseRooms.values()) {
            if (rooms.size() == 1) { // 如果该课程只使用了一个教室
                reward += SAME_ROOM_REWARD;
            }
        }
        
        return reward;
    }

    /**
     * 计算教室利用率集中度奖励
     * 
     * 
    */
    // 这个函数的目的是使教室使用次数尽量均衡。
    // 使用 Map<Integer, Integer> 统计每个教室被使用的次数
    //  计算所有教室使用次数的平均值（mean）
    // 计算使用次数的标准差（stdDev）
    // 奖励值 = ROOM_UTILIZATION_REWARD * (1 / (1 + stdDev))
    // 标准差越小，表示教室使用越均衡，奖励值就越大
    // 使用 1/(1+stdDev) 确保奖励值在 0-1 之间
    public double calculateRoomUtilizationReward(Timetable timetable) {
        if (!enableRoomUtilizationConstraint) return 0.0;
        Map<Integer, Integer> roomUsage = new HashMap<>();
        
        for (Task task : timetable.getTasks()) {
            roomUsage.merge(task.getClassroomId(), 1, Integer::sum);
        }
        
        double mean = roomUsage.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        double variance = roomUsage.values().stream()
                .mapToDouble(count -> Math.pow(count - mean, 2))
                .average().orElse(0);
        double stdDev = Math.sqrt(variance);

        return ROOM_UTILIZATION_REWARD * (1 / (1 + stdDev));
    }

    /**
     * 计算教师排课教室集中度奖励
     */
    // 这个函数的目的是让每个教师的课尽量集中在少数几个教室上课。具体逻辑：
    // - 使用 Map<String, Set<Integer>> 存储每个教师使用的所有教室
    // - 遍历所有任务，将教室ID添加到对应教师的教室集合中
    // - 对于每个教师，奖励值 = TEACHER_ROOM_CONCENTRATION_REWARD * (1.0 / rooms.size())
    // - 教师使用的教室越少，奖励值越大
    // - 这样可以避免教师在不同教室之间频繁移动
    public double calculateTeacherRoomConcentrationReward(Timetable timetable) {
        if (!enableTeacherRoomConcentrationConstraint) return 0.0;
        double reward = 0.0;
        Map<String, Set<Integer>> teacherRooms = new HashMap<>();
        
        for (Task task : timetable.getTasks()) {
            String teacherId = timetable.getScheduleTask(task.getScheduleTaskId()).getTeacherId();
            teacherRooms.computeIfAbsent(teacherId, k -> new HashSet<>())
                    .add(task.getClassroomId());
        }
        
        for (Set<Integer> rooms : teacherRooms.values()) {
            reward += TEACHER_ROOM_CONCENTRATION_REWARD * (1.0 / rooms.size());
        }
        
        return reward;
    }

    /**
     * 计算总的软约束奖励
     */
    /**
     * 计算班级固定教室约束奖励
     */
    // 这个函数的目的是鼓励将班级的课程安排在其固定教室中
    // - 遍历所有任务，检查每个任务是否安排在其对应班级的固定教室中
    // - 如果安排在固定教室中，给予 FIXED_CLASSROOM_REWARD 的奖励
    // - 这样可以鼓励系统尽量将班级的课程安排在其固定教室中
    public double calculateFixedClassroomReward(Timetable timetable) {
        if (!enableFixedClassroomConstraint) return 0.0;
        double reward = 0.0;
        
        for (Task task : timetable.getTasks()) {
            int scheduleTaskId = task.getScheduleTaskId();
            ScheduleTask scheduleTask = timetable.getScheduleTask(scheduleTaskId);
            Integer fixedClassroomId = scheduleTask.getFixedClassroomId();
            
            // 如果课程安排在了班级的固定教室，且固定教室ID不为null
            if (fixedClassroomId != null && fixedClassroomId > 0 && task.getClassroomId() == fixedClassroomId) {
                reward += FIXED_CLASSROOM_REWARD;
            }
        }
        
        return reward;
    }
    
    
    /**
     * 计算教师工作量约束奖励
     */
    // 实现了calculateTeacherWorkloadReward方法，该方法包含三个主要约束：
    // - 教师每天最大课时数限制（不超过6节课）
    // - 教师每周最大课时数限制（不超过20节课）
    // - 教师上下午课时分配平衡（上下午课时数差异不超过4节课）
    public double calculateTeacherWorkloadReward(Timetable timetable) {
        if (!enableTeacherWorkloadConstraint) return 0.0;
        double reward = 0.0;
        
        // 统计每个教师每天和每周的课时数
        Map<String, Map<Integer, Integer>> teacherDailyWorkload = new HashMap<>();
        Map<String, Integer> teacherWeeklyWorkload = new HashMap<>();
        Map<String, Map<Integer, Integer>> teacherDaypartWorkload = new HashMap<>();
        
        for (Task task : timetable.getTasks()) {
            String teacherId = timetable.getScheduleTask(task.getScheduleTaskId()).getTeacherId();
            Timeslot timeslot = timetable.getTimeslot(task.getTimeId());
            int dayNumber = timeslot.getDayNumber();
            int startPeriod = timeslot.getStart();
            int endPeriod = timeslot.getEnd();
            int periods = endPeriod - startPeriod + 1;
            
            // 更新每天课时数
            teacherDailyWorkload.computeIfAbsent(teacherId, k -> new HashMap<>())
                    .merge(dayNumber, periods, Integer::sum);
            
            // 更新每周课时数
            teacherWeeklyWorkload.merge(teacherId, periods, Integer::sum);
            
            // 更新上下午课时数（假设1-4节为上午，5-8节为下午）
            boolean isMorning = startPeriod <= 4;
            teacherDaypartWorkload.computeIfAbsent(teacherId, k -> new HashMap<>())
                    .merge(isMorning ? 1 : 2, periods, Integer::sum);
        }
        
        // 计算每天最大课时数约束奖励
        final int MAX_DAILY_PERIODS = 6; // 每天最大课时数
        for (Map<Integer, Integer> dailyWorkload : teacherDailyWorkload.values()) {
            for (int periods : dailyWorkload.values()) {
                if (periods <= MAX_DAILY_PERIODS) {
                    reward += TEACHER_DAILY_WORKLOAD_REWARD;
                }
            }
        }
        
        // 计算每周最大课时数约束奖励
        final int MAX_WEEKLY_PERIODS = 20; // 每周最大课时数
        for (int weeklyPeriods : teacherWeeklyWorkload.values()) {
            if (weeklyPeriods <= MAX_WEEKLY_PERIODS) {
                reward += TEACHER_WEEKLY_WORKLOAD_REWARD;
            }
        }
        
        // 计算上下午课时分配约束奖励
        for (Map<Integer, Integer> daypartWorkload : teacherDaypartWorkload.values()) {
            int morningPeriods = daypartWorkload.getOrDefault(1, 0);
            int afternoonPeriods = daypartWorkload.getOrDefault(2, 0);
            // 上下午课时数差异不超过4节课
            if (Math.abs(morningPeriods - afternoonPeriods) <= 4) {
                reward += TEACHER_DAYPART_BALANCE_REWARD;
            }
        }
        
        return reward;
    }
    
    /**
     * 计算总的软约束奖励
     */
    /**
     * 计算体育课程约束奖励
     */
    public double calculatePhysicalEducationReward(Timetable timetable) {
        if (!enablePEConstraint) return 0.0;
        double reward = 0.0;
        
        for (Task task : timetable.getTasks()) {
            ScheduleTask scheduleTask = timetable.getScheduleTask(task.getScheduleTaskId());
            // 检查是否为体育课
            if (scheduleTask.getSubjectType() != null && scheduleTask.getSubjectType().equals("体育课")) {
                Timeslot timeslot = timetable.getTimeslot(task.getTimeId());
                int startPeriod = timeslot.getStart();
                
                // 检查是否在下午时段（第5-8节）
                if (startPeriod >= 5) {
                    reward += PE_AFTERNOON_REWARD;
                }
                
                // 检查体育课后是否有其他课程
                boolean hasFollowingClass = false;
                for (Task otherTask : timetable.getTasks()) {
                    if (otherTask.getTimeId() > task.getTimeId() && 
                        timetable.getTimeslot(otherTask.getTimeId()).getDayNumber() == timeslot.getDayNumber()) {
                        hasFollowingClass = true;
                        break;
                    }
                }
                
                if (!hasFollowingClass) {
                    reward += PE_NO_FOLLOWING_CLASS_REWARD;
                }
            }
        }
        
        return reward;
    }
    
    /**
     * 计算晚上课程约束奖励
     */
    // 这个函数的目的是避免在晚上时段（第9-12节）安排课程
    // - 遍历所有任务，检查每个任务的时间段是否在晚上
    // - 如果不在晚上时段，给予 EVENING_CLASS_REWARD 的奖励
    // - 这样可以鼓励系统尽量避免在晚上安排课程
    public double calculateEveningClassReward(Timetable timetable) {
        if (!enableEveningClassConstraint) return 0.0;
        double reward = 0.0;
        
        for (Task task : timetable.getTasks()) {
            Timeslot timeslot = timetable.getTimeslot(task.getTimeId());
            int startPeriod = timeslot.getStart();
            int endPeriod = timeslot.getEnd();
            
            // 如果课程不在晚上时段（第9-12节）
            if (startPeriod < 9 || endPeriod < 9) {
                reward += EVENING_CLASS_REWARD;
            }
        }
        
        return reward;
    }

    /**
     * 计算实验课程约束奖励
     */
    public double calculateLabClassReward(Timetable timetable) {
        if (!enableLabClassConstraint) return 0.0;
        double reward = 0.0;
        
        for (Task task : timetable.getTasks()) {
            ScheduleTask scheduleTask = timetable.getScheduleTask(task.getScheduleTaskId());
            // 检查是否为实验课程
            if (scheduleTask.getSubjectType() != null && scheduleTask.getSubjectType().equals("实验课")) {
                Timeslot timeslot = timetable.getTimeslot(task.getTimeId());
                int startPeriod = timeslot.getStart();
                
                // 检查是否在晚上时段（第9-12节）
                if (startPeriod >= 9) {
                    reward += LAB_CLASS_EVENING_REWARD;
                }
            }
        }
        
        return reward;
    }

    /**
     * 计算多学时课程连续性约束奖励
     */
    public double calculateMultiPeriodContinuityReward(Timetable timetable) {
        if (!enableMultiPeriodContinuityConstraint) return 0.0;
        double reward = 0.0;
        
        // 按课程ID分组存储任务
        Map<Integer, List<Task>> courseTasksMap = new HashMap<>();
        for (Task task : timetable.getTasks()) {
            courseTasksMap.computeIfAbsent(task.getScheduleTaskId(), k -> new ArrayList<>()).add(task);
        }
        
        // 遍历每个课程的任务
        for (List<Task> courseTasks : courseTasksMap.values()) {
            ScheduleTask scheduleTask = timetable.getScheduleTask(courseTasks.get(0).getScheduleTaskId());
            
            // 检查是否为多学时课程（理论、实验、上机）
            if (scheduleTask.getSubjectType() != null && 
                (scheduleTask.getSubjectType().contains("理论") || 
                 scheduleTask.getSubjectType().contains("实验") || 
                 scheduleTask.getSubjectType().contains("上机"))) {
                
                // 按天分组任务
                Map<Integer, List<Task>> dailyTasks = new HashMap<>();
                for (Task task : courseTasks) {
                    Timeslot timeslot = timetable.getTimeslot(task.getTimeId());
                    dailyTasks.computeIfAbsent(timeslot.getDayNumber(), k -> new ArrayList<>()).add(task);
                }
                
                // 检查每天的任务是否连续
                for (List<Task> tasks : dailyTasks.values()) {
                    if (tasks.size() > 1) {
                        // 按时间排序
                        tasks.sort((t1, t2) -> {
                            Timeslot ts1 = timetable.getTimeslot(t1.getTimeId());
                            Timeslot ts2 = timetable.getTimeslot(t2.getTimeId());
                            return Integer.compare(ts1.getStart(), ts2.getStart());
                        });
                        
                        // 检查是否连续
                        boolean isConsecutive = true;
                        for (int i = 1; i < tasks.size(); i++) {
                            Timeslot prevSlot = timetable.getTimeslot(tasks.get(i-1).getTimeId());
                            Timeslot currSlot = timetable.getTimeslot(tasks.get(i).getTimeId());
                            if (currSlot.getStart() != prevSlot.getEnd() + 1) {
                                isConsecutive = false;
                                break;
                            }
                        }
                        
                        if (isConsecutive) {
                            reward += MULTI_PERIOD_CONTINUITY_REWARD;
                        }
                    }
                }
            }
        }
        
        return reward;
    }

    public double calculateTotalSoftConstraintReward(Timetable timetable) {
        return calculateSameRoomReward(timetable) +
               calculateRoomUtilizationReward(timetable) +
               calculateTeacherRoomConcentrationReward(timetable) +
               calculateFixedClassroomReward(timetable) +
               calculateTeacherWorkloadReward(timetable) +
               calculatePhysicalEducationReward(timetable) +
               calculateEveningClassReward(timetable) +
               calculateLabClassReward(timetable) +
               calculateMultiPeriodContinuityReward(timetable);
    }
}