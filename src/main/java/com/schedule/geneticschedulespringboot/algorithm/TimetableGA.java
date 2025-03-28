package com.schedule.geneticschedulespringboot.algorithm;

import com.schedule.geneticschedulespringboot.Utils.MyBatisUtils;
import com.schedule.geneticschedulespringboot.pojo.ClassRoom;
import com.schedule.geneticschedulespringboot.pojo.ScheduleTask;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.schedule.geneticschedulespringboot.algorithm.Timeslot.DAYS_PER_WEEK;

public class TimetableGA {
    public GeneticResult run(SoftConstraintCalculator calculator) throws IOException {
        Instant start = Instant.now();

        List<ScheduleTask> tasks = initTask();
        Timetable timetable = initializeTimetable(tasks);

        //  Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.95, 4, 15);

        //  Initialize population
        Population population = ga.initPopulation(timetable);

        //  Evaluate population
        ga.evalPopulation(population, timetable);

        // set SoftCalculator
        ga.setCalculator(calculator);

        int generation = 1;

        while (ga.isTerminationConditionMet(generation, 1000) == false
                && ga.isTerminationConditionMet(population) == false) {
            // Print fitness
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population, timetable);

            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

        Individual bestIndividual = population.getFittest(0);
        timetable.createTasks(bestIndividual);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        Task[] arrangedTasks = timetable.getTasks();
        List<Conflict> conflicts = new ArrayList<>();
        int clashes = 0;

        for (int i = 0; i < arrangedTasks.length; i ++) {
            Task task = arrangedTasks[i];
            int roomCapacity = timetable.getClassRoom(task.getClassroomId()).getCapacity();
            int classSize = timetable.getScheduleTask(task.getScheduleTaskId()).getClassSize();

            if (roomCapacity < classSize) {
                conflicts.add(new Conflict(1, task, null));
                clashes++;
            }

            for (int j = i + 1; j < arrangedTasks.length; j ++) {
                Task task2 = arrangedTasks[j];
                String teacherId1 = timetable.getScheduleTask(task.getScheduleTaskId()).getTeacherId();
                String teacherId2 = timetable.getScheduleTask(task2.getScheduleTaskId()).getTeacherId();

                if (!task.hasConflict(task2)) {
                    continue;
                }

                if (task.getClassroomId() == task2.getClassroomId()) {
                    conflicts.add(new Conflict(2, task, task2));
                    clashes++;
                }

                if (teacherId1.equals(teacherId2)) {
                    conflicts.add(new Conflict(3, task, task2));
                    clashes++;
                }

                Set<String> classSet = new HashSet<>(task.getClassListId());
                classSet.retainAll(task2.getClassListId());
                if (!classSet.isEmpty()) {
                    conflicts.add(new Conflict(4, task, task2));
                    clashes++;
                }
            }
        }

        return new GeneticResult(
                duration.toMillis(),
                generation,
                bestIndividual.getFitness(),
                clashes,
                arrangedTasks,
                conflicts
        );

    }

    private static Timetable initializeTimetable(List<ScheduleTask> tasks) throws IOException {
        // 初始化HashMap
        HashMap<Integer, ClassRoom> classRoomHashMap = new HashMap<>();
        HashMap<String, List<ClassRoom>> classRoomListHashMap = new HashMap<>();
        HashMap<Integer, Timeslot> timeslotHashMap = new HashMap<>();
        HashMap<Integer, ScheduleTask> scheduleTaskHashMap = new HashMap<>();

        // 调用封装的方法初始化数据
        initializeClassRooms(classRoomHashMap, classRoomListHashMap);
        initializeScheduleTasks(scheduleTaskHashMap, tasks);
        initializeTimelots(timeslotHashMap);

        // 创建并返回 Timetable 对象
        return new Timetable(classRoomHashMap, classRoomListHashMap, timeslotHashMap, scheduleTaskHashMap);
    }

    private static void initializeTimelots(HashMap<Integer, Timeslot> timeslotHashMap) {
        int cnt = 0;
        cnt = initializeContinuousPeriods(cnt, timeslotHashMap, Timeslot.continuousPeriods2, 2); // 2节课
        initializeContinuousPeriods(cnt, timeslotHashMap, Timeslot.continuousPeriods4, 4); // 4节课
    }

    private static int initializeContinuousPeriods(int cnt, HashMap<Integer, Timeslot> timeslotHashMap,
                                                   int[] periods, int duration) {
        for (int i = 1; i <= DAYS_PER_WEEK; i++) {
            for (int start : periods) {
                int id = ++cnt;
                timeslotHashMap.put(id, new Timeslot(id, i, start, start + duration - 1));
            }
        }
        return cnt;
    }

    private static void initializeScheduleTasks(HashMap<Integer, ScheduleTask> scheduleTaskHashMap, List<ScheduleTask> tasks) {
        tasks.forEach(task -> scheduleTaskHashMap.put(task.getId(), task));
    }

    /**
     * 初始化教室数据，填充 classRoomHashMap 和 classRoomListHashMap
     *
     * @param classRoomHashMap     用于存储教室 ID 到教室对象的映射
     * @param classRoomListHashMap 用于存储校区和类型到教室列表的映射
     * @throws IOException 如果数据库访问失败
     */
    private static void initializeClassRooms(HashMap<Integer, ClassRoom> classRoomHashMap,
                                             HashMap<String, List<ClassRoom>> classRoomListHashMap) throws IOException {
        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
            // 从数据库中查询所有教室
            List<ClassRoom> classRooms = sqlSession.selectList("classRoom.selectAllRoom");

            // 遍历查询结果
            for (ClassRoom classRoom : classRooms) {
                // 将教室放入 classRoomHashMap，假设教室的 ID 是唯一的
                classRoomHashMap.put(classRoom.getId(), classRoom);

                // 生成教室的校区和类型作为键
                String key = classRoom.getCampus() + "-" + classRoom.getType();

                // 将教室放入 classRoomListHashMap
                classRoomListHashMap.computeIfAbsent(key, k -> new ArrayList<>()).add(classRoom);
            }
        }
    }

    /**
     * 初始化任务列表
     *
     * @return 任务列表
     * @throws IOException 如果数据库访问失败
     */
    private static List<ScheduleTask> initTask() throws IOException {
        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
            return sqlSession.selectList("task.selectAllTask");
        }
    }
}