package com.schedule.geneticschedulespringboot.service;

import com.schedule.geneticschedulespringboot.algorithm.GeneticResult;
import com.schedule.geneticschedulespringboot.algorithm.SoftConstraintCalculator;

import java.io.IOException;

public interface IScheduleService {
    GeneticResult generateSchedule(SoftConstraintCalculator calculator) throws IOException;}
