package com.schedule.geneticschedulespringboot.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Week {
    private WeekRange weekRange;
    private Integer weekhour;

    public Week() {
    }

    public Week(WeekRange weekRange, Integer weekhour) {
        this.weekRange = weekRange;
        this.weekhour = weekhour;
    }

    public static List<Week> translate(String s) {
        List<Week> weeks = new ArrayList<>();
        if (s == null || s.isEmpty()) {
            return weeks;  // 空字符串返回空列表
        }

        String[] parts = s.split(",");  // 按逗号分割，可能只有一个元素
        for (String part : parts) {
            String[] rangeAndHour = part.split(":");
            if (rangeAndHour.length != 2) {
                throw new IllegalArgumentException("格式错误: " + part);
            }

            String[] weekRange = rangeAndHour[0].split("-");
            if (weekRange.length != 2) {
                throw new IllegalArgumentException("周次范围格式错误: " + rangeAndHour[0]);
            }

            int startWeek = Integer.parseInt(weekRange[0]);
            int endWeek = Integer.parseInt(weekRange[1]);
            int weekhour = Integer.parseInt(rangeAndHour[1]);

            weeks.add(new Week(new WeekRange(startWeek, endWeek), weekhour));
        }

        return weeks;
    }


    /**
     * 获取
     * @return weekRange
     */
    public WeekRange getWeekRange() {
        return weekRange;
    }

    /**
     * 设置
     * @param weekRange
     */
    public void setWeekRange(WeekRange weekRange) {
        this.weekRange = weekRange;
    }

    /**
     * 获取
     * @return weekhour
     */
    public Integer getWeekhour() {
        return weekhour;
    }

    /**
     * 设置
     * @param weekhour
     */
    public void setWeekhour(Integer weekhour) {
        this.weekhour = weekhour;
    }

    public String toString() {
        return "Week{weekRange = " + weekRange + ", weekhour = " + weekhour + "}";
    }
}
