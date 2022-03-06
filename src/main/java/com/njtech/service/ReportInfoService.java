package com.njtech.service;

import com.njtech.dao.ReportInfoDao;
import com.njtech.entity.ReportInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 众测报告信息 服务类
 * </p>
 *
 * @author wkXuan
 * @since 2022-03-05
 */
@Service
public class ReportInfoService {
    @Resource
    private ReportInfoDao reportInfoDao;

    public ReportInfo findReportById(String report_id) {
        return reportInfoDao.selectByPrimaryKey(report_id);
    }

    public List<ReportInfo> findReportBySubmissionId(String submission_id) {
        ReportInfo example = new ReportInfo();
        example.setSubmissionId(submission_id);
        return reportInfoDao.select(example);
    }

    public boolean addReport(ReportInfo reportInfo) {
        return reportInfoDao.insert(reportInfo) == 1;
    }
}
