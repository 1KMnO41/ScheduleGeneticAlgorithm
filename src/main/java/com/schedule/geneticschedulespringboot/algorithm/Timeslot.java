package com.schedule.geneticschedulespringboot.algorithm;

import java.util.List;

public class Timeslot {
    private int id;
    private Integer dayNumber;  // 是星期几
    private Integer start;  //  是从那一小节开始的
    private Integer end;    //  是从那一小节结束的
    public static final int[] continuousPeriods2 = {1, 3, 5, 7};
    public static final int[] continuousPeriods4 = {1, 3, 5};
    public static final int DAYS_PER_WEEK = 7;

    public static List<Timeslot> translate(String s) {

        return null;
    }

    public Timeslot(int id, Integer dayNumber, Integer start, Integer end) {
        this.id = id;
        this.dayNumber = dayNumber;
        this.start = start;
        this.end = end;
    }

    public Timeslot() {
    }

    public Timeslot(int dayNumber, int start, int end) {
        this.dayNumber = dayNumber;
        this.start = start;
        this.end = end;
    }

    /**
     * 获取
     * @return dayNumber
     */
    public int getDayNumber() {
        return dayNumber;
    }

    /**
     * 设置
     * @param dayNumber
     */
    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * 获取
     * @return start
     */
    public int getStart() {
        return start;
    }

    /**
     * 设置
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * 获取
     * @return end
     */
    public int getEnd() {
        return end;
    }

    /**
     * 设置
     * @param end
     */
    public void setEnd(int end) {
        this.end = end;
    }

    public String toString() {
        return "algorithm.Timeslot{dayNumber = " + dayNumber + ", start = " + start + ", end = " + end + "}";
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
