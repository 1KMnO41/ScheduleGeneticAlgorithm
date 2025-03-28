package com.schedule.geneticschedulespringboot.algorithm;

import lombok.Data;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;
@Data
public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int tournamentSize;
    private SoftConstraintCalculator calculator;
    private Map<Individual, Double> fitnessHash = Collections.synchronizedMap(new LinkedHashMap<Individual, Double>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Individual, Double> eldest) {
            return size() > 1000;
        }
    });

    public void evalPopulation(Population population, Timetable timetable) {
        IntStream.range(0, population.size()).parallel().forEach(i -> this.calcFitness(population.getIndividual(i), timetable));

        double populationFitness = 0;

        for (Individual individual : population.getIndividuals()) {
            populationFitness += individual.getFitness();
        }

        population.setPopulationFitness(populationFitness);
    }

    private double calcFitness(Individual individual, Timetable timetable) {
        Double storedFitness = fitnessHash.get(individual);
        if (storedFitness != null) {
            individual.setFitness(storedFitness);
            return storedFitness;
        }

        Timetable threadTimetable = new Timetable(timetable);
        threadTimetable.createTasks(individual);

        // 计算硬约束违反的惩罚值
        int clashes = threadTimetable.calcClashes();
        double hardConstraintPenalty = clashes * -100;

        double softConstraintPenalty = 0;            
        // 满足软约束的奖励值
        if (calculator!= null) {
            softConstraintPenalty = calculator.calculateTotalSoftConstraintReward(threadTimetable);
        }

        // 总适应度 = 硬约束惩罚 + 软约束奖励
        double fitness = hardConstraintPenalty + softConstraintPenalty;

        individual.setFitness(fitness);
        fitnessHash.put(individual, fitness);

        return fitness;
    }

    public Population initPopulation(Timetable timetable) {
        Population population = new Population(populationSize, timetable);

        return population;
    }

    public GeneticAlgorithm() {
    }

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }



    public String toString() {
        return "GeneticAlgorithm{populationSize = " + populationSize + ", mutationRate = " + mutationRate + ", crossoverRate = " + crossoverRate + ", elitismCount = " + elitismCount + ", tournamentSize = " + tournamentSize + "}";
    }

    public boolean isTerminationConditionMet(int generationCount, int maxGenerations) {
        return generationCount > maxGenerations;
    }

    public boolean isTerminationConditionMet(Population population) {
        return population.getFittest(0).getFitness() >= 0;
    }

    public Population crossoverPopulation(Population population, Timetable timetable) {
        Population newPopulation = new Population(population.size());

        for (int i = 0; i < population.size(); i++) {
            Individual parent1 = population.getFittest(i);

            if (this.crossoverRate > Math.random() && i >= elitismCount) {
                Individual parent2 = selectParent(population);
                Individual offspring = crossoverOperator(parent1, parent2);

                newPopulation.setIndividual(i, offspring);
            }
            else {
                newPopulation.setIndividual(i, parent1);
            }
        }

        evalPopulation(newPopulation, timetable);
        return newPopulation;
    }

    private Individual crossoverOperator(Individual parent1, Individual parent2) {
        Individual offspring = new Individual(parent1.getChromosomeLength());

        for (int i = 0; i < parent1.getChromosomeLength(); i++) {
            if (0.5 > Math.random()) {
                offspring.setGene(i, parent1.getGene(i));
            }
            else {
                offspring.setGene(i, parent2.getGene(i));
            }
        }

        return offspring;
    }

    private Individual selectParent(Population population) {
        Population tournamentPopulation = new Population(tournamentSize);

        population.shuffle();

        for (int i = 0; i < tournamentSize; i ++) {
            Individual tournamentIndividual = population.getIndividual(i);

            tournamentPopulation.setIndividual(i, tournamentIndividual);
        }

        return tournamentPopulation.getFittest(0);
    }

    public Population mutatePopulation(Population population, Timetable timetable) {
        Population newPopulation = new Population(populationSize);

        for (int i = 0; i < population.size(); i++) {
            Individual individual = population.getFittest(i);
            Individual randomIndividual = new Individual(timetable);

            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex ++) {
                if (this.mutationRate > Math.random() && i >= elitismCount) {
                    individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
                }
            }

            newPopulation.setIndividual(i, individual);
        }

        evalPopulation(newPopulation, timetable);
        return newPopulation;
    }
}
