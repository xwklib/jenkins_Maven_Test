package com.njtech.entity;


import lombok.Data;
import javax.persistence.*;

@Data
@Table(name = "file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileId;

    @Column(name = "filename")
    private String filename;

    @Column(name = "path")
    private String path;

    @Column(name = "task_id")
    private String taskId;


}
