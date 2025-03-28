package com.schedule.geneticschedulespringboot.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Population {
    private Individual[] individuals;
    private double populationFitness = -1;

    public Population() {
    }

    public Population(Individual[] individuals, double populationFitness) {
        this.individuals = individuals;
        this.populationFitness = populationFitness;
    }

    public Population(int populationSize, Timetable timetable) {
        individuals = new Individual[populationSize];

        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(timetable);
        }
    }

    public Population(int size) {
        individuals = new Individual[size];
    }

    /**
     * 获取
     * @return individuals
     */
    public Individual[] getIndividuals() {
        return individuals;
    }

    /**
     * 设置
     * @param individuals
     */
    public void setIndividuals(Individual[] individuals) {
        this.individuals = individuals;
    }

    /**
     * 获取
     * @return populationFitness
     */
    public double getPopulationFitness() {
        return populationFitness;
    }

    /**
     * 设置
     * @param populationFitness
     */
    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }

    public String toString() {
        return "Population{individuals = " + individuals + ", populationFitness = " + populationFitness + "}";
    }

    public Individual getFittest(int offset) {
        // 按照 fitness 从大到小排序
        Arrays.sort(individuals, (o1, o2) -> Double.compare(o2.getFitness(), o1.getFitness()));

        // 返回排序后的第 offset 个个体
        return individuals[offset];
    }

    public int size() {
        return individuals.length;
    }

    public Individual getIndividual(int i) {
        return individuals[i];
    }

    public Individual setIndividual(int i, Individual individual) {
        return individuals[i] = individual;
    }

    public void shuffle() {
        Random rand = new Random();

        for (int i = getIndividuals().length - 1; i > 0; i -- ) {
            int index = rand.nextInt(i + 1);
            Individual temp = getIndividual(index);
            individuals[index] = individuals[i];
            individuals[i] = temp;
        }
    }
}
