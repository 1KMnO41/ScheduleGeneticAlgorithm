package com.schedule.geneticschedulespringboot.algorithm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "排课结果")
public class GeneticResult {

    public GeneticResult(long executionTimeMs, int generations, double bestFitness, int clashes, Task[] tasks, List<Conflict> conflicts) {
        this.executionTimeMs = executionTimeMs;
        this.generations = generations;
        this.bestFitness = bestFitness;
        this.clashes = clashes;
        this.tasks = tasks;
        this.conflicts = conflicts;
    }
    
    @Schema(description = "运行时间（毫秒）")
    private long executionTimeMs;       
    
    @Schema(description = "迭代次数")
    private int generations;            
    
    @Schema(description = "最优适应度")
    private double bestFitness;         
    
    @Schema(description = "冲突数")
    private int clashes;                

    @Schema(description = "排好的课程")
    private Task tasks[];
    
    @Schema(description = "冲突列表")
    private List<Conflict> conflicts;
}