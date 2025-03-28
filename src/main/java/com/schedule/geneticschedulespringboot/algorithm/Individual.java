package com.schedule.geneticschedulespringboot.algorithm;

import com.schedule.geneticschedulespringboot.pojo.ScheduleTask;
import lombok.Data;

import java.util.Arrays;
@Data
public class Individual {
    private int chromosome[];
    private double fitness = -1;

    public Individual(Timetable timetable) {
        int numTasks = timetable.getNumTasks();
        int chromosomeLength = numTasks * 2;
        int newChromosome[] = new int[chromosomeLength];
        int chromosomeIndex = 0;
        ScheduleTask[] scheduleTasks = timetable.getScheduleTasks();

        Arrays.sort(scheduleTasks, (task1, task2) ->
                Integer.compare(task2.getSchedulingPriority(), task1.getSchedulingPriority()));

        for (ScheduleTask scheduleTask : scheduleTasks) {
            for (Week week :  Week.translate(scheduleTask.getWeeklyHours())) {
                int weekHour = week.getWeekhour();
                int continuousPeriods = scheduleTask.getContinuousPeriods();
                if (weekHour % continuousPeriods == 0) {
                    int cnt = weekHour / continuousPeriods;
                    for (int i = 0; i < cnt; i++) {
                        int timeslotId = timetable.getRandomTimeslotId(scheduleTask.getContinuousPeriods());
                        newChromosome[chromosomeIndex] = timeslotId;
                        chromosomeIndex++;

                        int roomId = timetable.getRandomRoomId(scheduleTask.getCampus(), scheduleTask.getDesignatedRoomType());
                        newChromosome[chromosomeIndex] = roomId;
                        chromosomeIndex++;
                    }
                }
            }
        }

        this.chromosome = newChromosome;
    }

    public Individual() {
    }

    public Individual(int[] chromosome, double fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    public Individual(int chromosomeLength) {
        int[] newChromosome = new int[chromosomeLength];
        this.chromosome = newChromosome;
    }

    /**
     * 获取
     * @return chromosome
     */
    public int[] getChromosome() {
        return chromosome;
    }

    /**
     * 设置
     * @param chromosome
     */
    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * 获取
     * @return fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * 设置
     * @param fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public String toString() {
        return "Individual{chromosome = " + chromosome + ", fitness = " + fitness + "}";
    }

    public int getChromosomeLength() {
        return chromosome.length;
    }

    public int getGene(int i) {
        return chromosome[i];
    }

    public void setGene(int i, int gene) {
        chromosome[i] = gene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;
        return Arrays.equals(chromosome, that.chromosome);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(chromosome);
    }
}