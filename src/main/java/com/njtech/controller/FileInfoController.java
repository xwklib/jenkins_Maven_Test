package com.njtech.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReferenceUtil;
import com.njtech.common.PassToken;
import com.njtech.common.Result;
import com.njtech.dao.FileInfoDao;
import com.njtech.entity.FileInfo;
import com.njtech.exception.CustomException;
import com.njtech.service.FileInfoService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file-info")
@CrossOrigin
public class FileInfoController {
    @Resource
    private FileInfoService fileInfoService;

    //    private static final String BASE_PATH = System.getProperty("user.dir") + "/src/main/resources/file/";
    private static final String BASE_PATH = "../../src/main/resources/file/";

    @PostMapping("/upload/{task_id}")
    public Result upload(MultipartFile file, @PathVariable String task_id, HttpServletRequest request) throws IOException {

        if (file.isEmpty()) return Result.error("4004", "文件为空");

        Snowflake snowflake = IdUtil.createSnowflake(0, 0);
        String fileId = snowflake.nextIdStr();

        FileInfo info = fileInfoService.addTaskFile(file, task_id, fileId);

        if (info != null) {
            return Result.success(info);
        } else {
            return Result.error("4001", "上传失败");
        }
    }

    @PassToken
    @GetMapping("/download/{file_id}")
    public void download(@PathVariable String file_id, HttpServletResponse response) throws IOException {
        if (file_id.isEmpty()) {
            throw new CustomException("1001", "请指定文件id");
        }
        FileInfo fileInfo = fileInfoService.findById(file_id);
        if (fileInfo == null) {
            throw new CustomException("1001", "未查询到该文件");
        }
        String filePath = fileInfo.getPath();
        String fileName = fileInfo.getFilename();
        byte[] bytes = FileUtil.readBytes(filePath + "/" + fileName);

        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.addHeader("Content-Length", "" + bytes.length);
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(bytes);
        toClient.flush();
        toClient.close();
    }
}

