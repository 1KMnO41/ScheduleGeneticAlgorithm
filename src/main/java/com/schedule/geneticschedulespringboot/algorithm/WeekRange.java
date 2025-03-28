package com.schedule.geneticschedulespringboot.algorithm;

public class WeekRange {
    private Integer startWeek;  //  从第几周开始
    private Integer endWeek;    //  到第几周结束

    public WeekRange() {
    }

    public WeekRange(Integer startWeek, Integer endWeek) {
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    /**
     * 获取
     * @return startWeek
     */
    public Integer getStartWeek() {
        return startWeek;
    }

    /**
     * 设置
     * @param startWeek
     */
    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * 获取
     * @return endWeek
     */
    public Integer getEndWeek() {
        return endWeek;
    }

    /**
     * 设置
     * @param endWeek
     */
    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public String toString() {
        return "WeekRange{startWeek = " + startWeek + ", endWeek = " + endWeek + "}";
    }
}
