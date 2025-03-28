package com.schedule.geneticschedulespringboot.algorithm;


import com.schedule.geneticschedulespringboot.pojo.ClassRoom;
import com.schedule.geneticschedulespringboot.pojo.ScheduleTask;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Timetable {
    private HashMap<Integer, ClassRoom> classRoomHashMap;
    private HashMap<String, List<ClassRoom>> classRoomListHashMap;
    private HashMap<Integer, Timeslot> timeslotHashMap;
    private HashMap<Integer, ScheduleTask> scheduleTaskHashMap;
    private Task tasks[];
    private int numTasks = 0;

    public Timetable(HashMap<Integer, ClassRoom> classRoomHashMap, HashMap<String, List<ClassRoom>> classRoomListHashMap, HashMap<Integer, Timeslot> timeslotHashMap, HashMap<Integer, ScheduleTask> scheduleTaskHashMap) {
        this.classRoomHashMap = classRoomHashMap;
        this.classRoomListHashMap = classRoomListHashMap;
        this.timeslotHashMap = timeslotHashMap;
        this.scheduleTaskHashMap = scheduleTaskHashMap;
    }

    public Timetable(Timetable timetable) {
        this.scheduleTaskHashMap = timetable.getScheduleTaskHashMap();
        this.timeslotHashMap = timetable.getTimeslotHashMap();
        this.classRoomHashMap = timetable.getClassRoomHashMap();
    }

    public ScheduleTask[] getScheduleTasks() {
        return (ScheduleTask[]) scheduleTaskHashMap.values().toArray(new ScheduleTask[scheduleTaskHashMap.size()]);
    }

    public ClassRoom getClassRoom(int classRoomId) {
        return classRoomHashMap.get(classRoomId);
    }

    public Timeslot getTimeslot(int timeslotId) {
        return timeslotHashMap.get(timeslotId);
    }

    public ScheduleTask getScheduleTask(int scheduleTaskId) {
        return scheduleTaskHashMap.get(scheduleTaskId);
    }

    /**
     * 获取
     * @return classRoomHashMap
     */
    public HashMap<Integer, ClassRoom> getClassRoomHashMap() {
        return classRoomHashMap;
    }

    /**
     * 设置
     * @param classRoomHashMap
     */
    public void setClassRoomHashMap(HashMap<Integer, ClassRoom> classRoomHashMap) {
        this.classRoomHashMap = classRoomHashMap;
    }

    /**
     * 获取
     * @return classRoomListHashMap
     */
    public HashMap<String, List<ClassRoom>> getClassRoomListHashMap() {
        return classRoomListHashMap;
    }

    /**
     * 设置
     * @param classRoomListHashMap
     */
    public void setClassRoomListHashMap(HashMap<String, List<ClassRoom>> classRoomListHashMap) {
        this.classRoomListHashMap = classRoomListHashMap;
    }

    /**
     * 获取
     * @return timeslotHashMap
     */
    public HashMap<Integer, Timeslot> getTimeslotHashMap() {
        return timeslotHashMap;
    }

    /**
     * 设置
     * @param timeslotHashMap
     */
    public void setTimeslotHashMap(HashMap<Integer, Timeslot> timeslotHashMap) {
        this.timeslotHashMap = timeslotHashMap;
    }

    /**
     * 获取
     * @return scheduleTaskHashMap
     */
    public HashMap<Integer, ScheduleTask> getScheduleTaskHashMap() {
        return scheduleTaskHashMap;
    }

    /**
     * 设置
     * @param scheduleTaskHashMap
     */
    public void setScheduleTaskHashMap(HashMap<Integer, ScheduleTask> scheduleTaskHashMap) {
        this.scheduleTaskHashMap = scheduleTaskHashMap;
    }

    /**
     * 获取
     * @return tasks
     */
    public Task[] getTasks() {
        return tasks;
    }

    /**
     * 设置
     * @param tasks
     */
    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    /**
     * 获取
     * @return numTasks
     */
    public int getNumTasks() {
        if (numTasks > 0) {
            return numTasks;
        }

        int numTasks = 0;

        for (ScheduleTask scheduleTask : getScheduleTasks()) {
            for (Week week :  Week.translate(scheduleTask.getWeeklyHours())) {
                int weekHour = week.getWeekhour();
                int continuousPeriods = scheduleTask.getContinuousPeriods();
                if (weekHour % continuousPeriods == 0) {
                    numTasks += weekHour / continuousPeriods;
                }
//            TODO: 如果不能整除怎么办
            }
        }

        this.numTasks = numTasks;

        return numTasks;
    }

    /**
     * 设置
     * @param numTasks
     */
    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public String toString() {
        return "Timetable{classRoomHashMap = " + classRoomHashMap + ", classRoomListHashMap = " + classRoomListHashMap + ", timeslotHashMap = " + timeslotHashMap + ", scheduleTaskHashMap = " + scheduleTaskHashMap + ", tasks = " + tasks + ", numTasks = " + numTasks + "}";
    }

    public int getRandomTimeslotId(Integer continuousPeriods) {
        // 确定 cnt 的范围
        int minId;
        int maxId;

        if (continuousPeriods == 2) {
            // continuousPeriods = 2 的 TimeslotId 范围是 1 到 28
            minId = 1;
            maxId = 28;
        } else if (continuousPeriods == 4) {
            // continuousPeriods = 4 的 TimeslotId 范围是 29 到 49
            minId = 29;
            maxId = 49;
        } else {
            throw new IllegalArgumentException("Unsupported continuousPeriods: " + continuousPeriods);
        }

        // 随机生成一个 TimeslotId
        return ThreadLocalRandom.current().nextInt(minId, maxId + 1);
    }

    public int getRandomRoomId(String campus, String designatedRoomType) {
        String key = campus + "-" + designatedRoomType;
        List<ClassRoom> classRoomList = classRoomListHashMap.get(key);

        return classRoomList.get(ThreadLocalRandom.current().nextInt(classRoomList.size())).getId();
    }

    public void createTasks(Individual individual) {
        Task tasks[] = new Task[getNumTasks()];
        int chromosome[] = individual.getChromosome();
        int chromosomeIndex = 0;
        int taskIndex = 0;
        ScheduleTask[] scheduleTasks = getScheduleTasks();

        Arrays.sort(scheduleTasks, (task1, task2) ->
                Integer.compare(task2.getSchedulingPriority(), task1.getSchedulingPriority()));

        for (ScheduleTask scheduleTask : scheduleTasks) {
            for (Week week :  Week.translate(scheduleTask.getWeeklyHours())) {
                int weekHour = week.getWeekhour();
                int continuousPeriods = scheduleTask.getContinuousPeriods();
                if (weekHour % continuousPeriods == 0) {
                    int cnt = weekHour / continuousPeriods;

                    for (int i = 0; i < cnt; i ++) {
                        tasks[taskIndex] = new Task(taskIndex,  chromosome[chromosomeIndex ++], chromosome[chromosomeIndex ++], scheduleTask.getId(), WeekType.EVERY, week.getWeekRange(), scheduleTask.getclassComposition());
                        taskIndex++;
                    }
                }
//            TODO: 如果不能整除怎么办
            }
        }

        this.tasks = tasks;
    }

    public int calcClashes() {
        int clashes = 0;

        for (int i = 0; i < tasks.length; i ++) {
            Task task = tasks[i];
            int roomCapacity = getClassRoom(task.getClassroomId()).getCapacity();
            int classSize = getScheduleTask(task.getScheduleTaskId()).getClassSize();

            if (roomCapacity < classSize) {
                clashes++;
            }

            for (int j = i + 1; j < tasks.length; j ++) {
                Task task2 = tasks[j];
                String teacherId1 = getScheduleTask(task.getScheduleTaskId()).getTeacherId();
                String teacherId2 = getScheduleTask(task2.getScheduleTaskId()).getTeacherId();

                if (!task.hasConflict(task2)) {
                    continue;
                }

                if (task.getClassroomId() == task2.getClassroomId()) {
                    clashes ++;
                }

                if (teacherId1.equals(teacherId2)) {
                    clashes ++;
                }

                Set<String> classSet = new HashSet<>(task.getClassListId());
                classSet.retainAll(task2.getClassListId());
                if (!classSet.isEmpty()) {
                    clashes ++;
                }
            }
        }

        return clashes;
    }
}