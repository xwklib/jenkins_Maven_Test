package com.njtech.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "task_info")
public class TaskInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private String taskId;

    /**
     * 简介
     */
    @Column(name = "brief")
    private String brief;

    /**
     * 任务名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 测试时间段
     */
    @Column(name = "startTime")
    private String startTime;

    /**
     * 需要的工人数量
     */
    @Column(name = "workerNum")
    private Integer workerNum;

    /**
     * 测试类型（功能测试、性能测试等）
     */
    @Column(name = "type")
    private String type;

    /**
     * 发包方
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 状态（招募中、进行中、已完成）
     */
    @Column(name = "status")
    private String status;

    @Column(name = "finishTime")
    private String finishTime;

}
