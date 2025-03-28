package com.schedule.geneticschedulespringboot.pojo;

public class Class {
    private String id;  //  班级id
    private Integer size;   //  班级人数

    public Class() {
    }

    public Class(String id, Integer size) {
        this.id = id;
        this.size = size;
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
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置
     * @param size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    public String toString() {
        return "pojo.Class{id = " + id + ", size = " + size + "}";
    }
}
