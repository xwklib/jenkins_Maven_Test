package com.njtech.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.njtech.common.Result;
import com.njtech.entity.FileInfo;
import com.njtech.entity.SubmissionInfo;
import com.njtech.entity.TaskInfo;
import com.njtech.exception.CustomException;
import com.njtech.service.FileInfoService;
import com.njtech.service.SubmissionInfoService;
import com.njtech.service.TaskInfoService;
import com.njtech.vo.TaskInfoVo;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/task-info")
@CrossOrigin
public class TaskInfoController {
    @Resource
    private TaskInfoService taskInfoService;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private SubmissionInfoService submissionInfoService;

    private static final String BASE_PATH = "../../src/main/resources/file/";

    @GetMapping("/getAllTasks")
    public Result<List<TaskInfo>> getAllTasks() {
        List<TaskInfo> tasks = taskInfoService.findAllTasks();
        return Result.success(tasks);
    }

    @GetMapping("/getBossTasks")
    public Result<List<TaskInfo>> getBossTasks(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId.isEmpty()) {
            return Result.success(new ArrayList<>());
        }
        List<TaskInfo> tasks = taskInfoService.findTasksByUserId(userId);
        Result<List<TaskInfo>> res = new Result<>();
        res.setData(tasks);
        return res;
    }

    @GetMapping("/getTaskInfo/{taskId}")
    public Result<TaskInfoVo> getTaskInfo(@PathVariable String taskId) {
        TaskInfo task = taskInfoService.findTaskById(taskId);
        if (task == null) {
            return Result.error("4004", "????????????????????????????????????ID");
        }
        TaskInfoVo taskInfoVo = new TaskInfoVo(task);
        List<FileInfo> files = fileInfoService.findFilesByTaskId(taskId);
        taskInfoVo.setFiles(files);
        return Result.success(taskInfoVo);
    }

    @PostMapping("/publish")
    public Result<TaskInfo> publishTask(HttpServletRequest request, TaskInfo taskInfo, MultipartFile file) throws IOException {
        String userId = (String) request.getAttribute("userId");
        taskInfo.setUserId(userId);

        Snowflake snowflake = IdUtil.createSnowflake(0, 0);
        String taskId = String.valueOf(snowflake.nextIdStr());
        taskInfo.setTaskId(taskId);
        String fileId = snowflake.nextIdStr();
        taskInfo.setStatus("?????????");

        boolean success = taskInfoService.addTask(taskInfo);
        if (!success) {
            return Result.error("6001", "????????????????????????????????????????????????????????????");
        }

        if (!taskInfo.getTaskId().isEmpty()) {
            System.err.println(this.getClass() + " " + taskInfo.getTaskId());
        } else {
            return Result.error("2003", "????????????????????????????????????????????????");
        }
        if (file.isEmpty()) return Result.error("4002", "??????????????????????????????????????????????????????????????????????????????");

        FileInfo fileInfo = fileInfoService.addTaskFile(file, taskId, fileId);

        if (fileInfo != null) {
            return Result.success(taskInfo);
        } else {
            return Result.error("4002", "?????????????????????????????????????????????????????????????????????????????????");
        }
    }

    @GetMapping("/getWorkerSubmissions/{task_id}")
    public Result<List<SubmissionInfo>> getWorkerSubmissions(@PathVariable String task_id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId.isEmpty()) {
            return Result.success(new ArrayList<>());
        }
        // ????????????????????????submission
        List<SubmissionInfo> submissions = submissionInfoService.findSubmissionsByTaskId(task_id);
        Result<List<SubmissionInfo>> res = new Result<>();
        res.setData(submissions);
        return res;
    }

}

