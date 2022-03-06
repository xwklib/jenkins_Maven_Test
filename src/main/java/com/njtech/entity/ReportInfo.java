package com.njtech.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Table(name = "report_info")
public class ReportInfo implements Serializable {

    /**
     * 报告id（随机就行）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reportId;

    @Column(name = "submission_id")
    private String submissionId;

    @Column(name = "file_id")
    private String fileId;

    /**
     * 缺陷情况说明
     */
    @Column(name = "description")
    private String description;

    /**
     * 缺陷复先步骤
     */
    @Column(name = "recoverySteps")
    private String recoverySteps;

    /**
     * 测试设备信息
     */
    @Column(name = "equipmentInfo")
    private String equipmentInfo;
}
