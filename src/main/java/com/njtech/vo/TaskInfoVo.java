package com.njtech.vo;

import com.njtech.entity.FileInfo;
import com.njtech.entity.TaskInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Data
public class TaskInfoVo extends TaskInfo {

    private List<FileInfo> files;

    public TaskInfoVo(TaskInfo task) {
        this.setTaskId(task.getTaskId());
        this.setName(task.getName());
        this.setUserId(task.getUserId());
        this.setBrief(task.getBrief());
        this.setStartTime(task.getStartTime());
        this.setFinishTime(task.getFinishTime());
        this.setStatus(task.getStatus());
        this.setType(task.getType());
        this.setWorkerNum(task.getWorkerNum());
    }
}
