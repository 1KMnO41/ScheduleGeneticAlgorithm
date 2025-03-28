package com.schedule.geneticschedulespringboot.pojo;

public class ClassRoom {
    private Integer id;  // 教室id
    private Integer capacity;   // 教室大小
    private String type;    //教室类型
    private String campus;  //教室在那个校区

    public ClassRoom() {
    }

    public ClassRoom(Integer id, Integer capacity, String type, String campus) {
        this.id = id;
        this.capacity = capacity;
        this.type = type;
        this.campus = campus;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * 设置
     * @param capacity
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 设置
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
        return "ClassRoom{id = " + id + ", capacity = " + capacity + ", type = " + type + ", campus = " + campus + "}";
    }
}