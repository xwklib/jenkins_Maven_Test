package com.njtech.service;

import com.njtech.common.Result;
import com.njtech.dao.TaskInfoDao;
import com.njtech.dao.UserInfoDao;
import com.njtech.entity.TaskInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskInfoService {

    @Resource
    private TaskInfoDao taskInfoDao;

    public boolean addTask(TaskInfo taskInfo) {
        return taskInfoDao.insert(taskInfo) == 1;
    }

    public List<TaskInfo> findTasksByUserId(String userId) {
        TaskInfo tmp = new TaskInfo();
        tmp.setUserId(userId);
        return taskInfoDao.select(tmp);
    }

    public TaskInfo findTaskById(String taskId) {
        return taskInfoDao.selectByPrimaryKey(taskId);
    }

    public List<TaskInfo> findAllTasks() {
        return taskInfoDao.selectAll();
    }
}
