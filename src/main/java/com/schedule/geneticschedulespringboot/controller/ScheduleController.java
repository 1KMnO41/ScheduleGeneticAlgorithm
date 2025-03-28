package com.schedule.geneticschedulespringboot.controller;

import com.schedule.geneticschedulespringboot.algorithm.GeneticResult;
import com.schedule.geneticschedulespringboot.algorithm.SoftConstraintCalculator;
import com.schedule.geneticschedulespringboot.service.IScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/schedule")
@Tag(name = "排课管理", description = "自动排课相关接口")  // Swagger 分类标签
public class ScheduleController {
    
    @Autowired
    private IScheduleService scheduleService;
    
    @GetMapping("/generate")
    @Operation(summary = "生成排课", description = "调用遗传算法生成排课结果")  // 接口描述
    public GeneticResult generateSchedule(@RequestBody SoftConstraintCalculator calculator) throws IOException {
        return scheduleService.generateSchedule(calculator);
    }
}