package com.njtech.service;

import com.njtech.dao.SubmissionInfoDao;
import com.njtech.entity.SubmissionInfo;
import com.njtech.entity.TaskInfo;
import com.njtech.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubmissionInfoService {
    @Resource
    private SubmissionInfoDao submissionInfoDao;

    public List<SubmissionInfo> findSubmissionsByUserId(String userId) {
        SubmissionInfo tmp = new SubmissionInfo();
        tmp.setUserId(userId);
        return submissionInfoDao.select(tmp);
    }

    public boolean addSubmission(SubmissionInfo submissionInfo) throws CustomException {
        List<SubmissionInfo> submissionInfo1 = submissionInfoDao.select(submissionInfo);
        if (submissionInfo1.isEmpty()) {
            return submissionInfoDao.insert(submissionInfo) == 1;
        }
        throw new CustomException("6001", "无法多次接收同一个任务");
    }

    public List<SubmissionInfo> findSubmissionsByTaskId(String task_id) {
        SubmissionInfo tmp = new SubmissionInfo();
        tmp.setTaskId(task_id);
        return submissionInfoDao.select(tmp);
    }

    public SubmissionInfo findSubmissionById(String submission_id) {
        return submissionInfoDao.selectByPrimaryKey(submission_id);
    }

}
