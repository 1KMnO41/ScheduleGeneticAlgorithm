package com.schedule.geneticschedulespringboot.pojo;

public class Course {
    private String id;
    private String campus;

    public Course() {
    }

    public Course(String id, String campus) {
        this.id = id;
        this.campus = campus;
    }

    /**
     * 获取
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     * @return campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * 设置
     * @param campus
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String toString() {
        return "pojo.Course{id = " + id + ", campus = " + campus + "}";
    }
}
