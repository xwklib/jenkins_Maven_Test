package com.njtech.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.njtech.common.Result;
import com.njtech.entity.SubmissionInfo;
import com.njtech.entity.TaskInfo;
import com.njtech.service.SubmissionInfoService;
import com.njtech.service.TaskInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/submission-info")
@CrossOrigin
public class SubmissionInfoController {
    @Resource
    private SubmissionInfoService submissionInfoService;
    @Resource
    private TaskInfoService taskInfoService;

    @GetMapping("/getWorkerTasks")
    public Result<List<TaskInfo>> getWorkerTasks(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId.isEmpty()) {
            return Result.success(new ArrayList<>());
        }
        // 获取接收的所有任务
        List<SubmissionInfo> submissions = submissionInfoService.findSubmissionsByUserId(userId);
        Result<List<TaskInfo>> res = new Result<>();
        List<TaskInfo> tasks = new ArrayList<>();
        // 获取所有的任务id并取出相关信息
        submissions.forEach(submission -> {
            tasks.add(taskInfoService.findTaskById(submission.getTaskId()));
        });
        res.setData(tasks);
        return res;
    }

    @PostMapping("/receiveTask")
    public Result<SubmissionInfo> receiveTask(HttpServletRequest request,
                                              @RequestBody String task_id) {
        String userId = request.getAttribute("userId").toString();
        if (task_id == null) {
            return Result.error("4004", "请指定TaskId");
        }
        SubmissionInfo submissionInfo = new SubmissionInfo();

        Snowflake snowflake = IdUtil.createSnowflake(0, 0);
        String submissionId = String.valueOf(snowflake.nextIdStr());

        submissionInfo.setSubmissionId(submissionId);
        submissionInfo.setUserId(userId);
        submissionInfo.setTaskId(task_id);

        if (submissionInfoService.addSubmission(submissionInfo)) {
            return Result.success(submissionInfo);
        }
        return Result.error();
    }

    @GetMapping("/getSubmissionInfo/{submission_id}")
    public Result<SubmissionInfo> getSubmissionInfo(@PathVariable String submission_id) {
        return Result.success(submissionInfoService.findSubmissionById(submission_id));
    }

}

