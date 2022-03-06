package com.njtech.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.njtech.common.Result;
import com.njtech.entity.FileInfo;
import com.njtech.entity.ReportInfo;
import com.njtech.service.FileInfoService;
import com.njtech.service.ReportInfoService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report-info")
@CrossOrigin
public class ReportInfoController {
    @Resource
    private ReportInfoService reportInfoService;
    @Resource
    private FileInfoService fileInfoService;

    @PostMapping("/submitReport/{submission_id}")
    public Result<ReportInfo> submitReport(HttpServletRequest request, ReportInfo reportInfo, MultipartFile file,
                                           @PathVariable String submission_id) throws IOException {
        String userId = (String) request.getAttribute("userId");

        Snowflake snowflake = IdUtil.createSnowflake(0, 0);
        String report_id = String.valueOf(snowflake.nextIdStr());
        String file_id = String.valueOf(snowflake.nextIdStr());
        reportInfo.setReportId(report_id);
        reportInfo.setSubmissionId(submission_id);

        FileInfo fileInfo = fileInfoService.addReportFile(file, report_id, file_id);

        if (fileInfo != null) {
            reportInfo.setFileId(file_id);
            if (reportInfoService.addReport(reportInfo)) {
                return Result.success(reportInfo);
            } else {
                return Result.error("6001", "未知错误，请检查后端代码（ReportInfoController)");
            }
        } else {
            return Result.error("4002", "报告创建成功！文件上传失败！请在报告界面中重新上传相关文件");
        }

    }

    @GetMapping("/getReportInfo/{submission_id}")
    public Result<List<ReportInfo>> getReportInfo(@PathVariable String submission_id) {
        List<ReportInfo> reportInfo = reportInfoService.findReportBySubmissionId(submission_id);
        return Result.success(reportInfo);
    }

}

