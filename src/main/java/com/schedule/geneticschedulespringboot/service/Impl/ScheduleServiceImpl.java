package com.schedule.geneticschedulespringboot.service.Impl;

import com.schedule.geneticschedulespringboot.algorithm.GeneticResult;
import com.schedule.geneticschedulespringboot.algorithm.SoftConstraintCalculator;
import com.schedule.geneticschedulespringboot.algorithm.TimetableGA;
import com.schedule.geneticschedulespringboot.service.IScheduleService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    
    public GeneticResult generateSchedule(SoftConstraintCalculator calculator) throws IOException {
        TimetableGA timetableGA = new TimetableGA();
        return timetableGA.run(calculator);
    }
}