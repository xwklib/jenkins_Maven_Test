package com.njtech.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "submission_info")
public class SubmissionInfo {

    /**
     * 报告Id（通过Id寻找提交的File）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String submissionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "task_id")
    private String taskId;

    /**
     * 任务进度
     */
    @Column(name = "progress")
    private String progress;

    /**
     * 评价
     */
    @Column(name = "comment")
    private String comment;

    /**
     * 接受时间
     */
    @Column(name = "receiveTime")
    private String receiveTime;

    @Column(name = "completeTime")
    private String completeTime;

}
