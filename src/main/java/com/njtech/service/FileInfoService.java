package com.njtech.service;

import cn.hutool.core.io.FileUtil;
import com.njtech.dao.FileInfoDao;
import com.njtech.entity.FileInfo;
import com.njtech.entity.TaskInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class FileInfoService {

    private static final String BASE_PATH = "../../src/main/resources/file/";

    @Resource
    private FileInfoDao fileInfoDao;

    public List<FileInfo> findFilesByTaskId(String taskId) {
        FileInfo tmp = new FileInfo();
        tmp.setTaskId(taskId);
        return fileInfoDao.select(tmp);
    }

    public FileInfo findById(String fileId) {
        return fileInfoDao.selectByPrimaryKey(fileId);
    }

    public FileInfo addTaskFile(MultipartFile file, String taskId, String fileId) throws IOException {

        String originName = file.getOriginalFilename();
        String fileName = FileUtil.mainName(originName) + System.currentTimeMillis() + "." + FileUtil.extName(originName);
        FileUtil.writeBytes(file.getBytes(), BASE_PATH + taskId + "/" + originName);

        FileInfo info = new FileInfo();
        info.setFileId(fileId);
        info.setFilename(originName);
        info.setPath(BASE_PATH + taskId);
        info.setTaskId(taskId);

        if (fileInfoDao.insert(info) == 1) {
            return info;
        } else {
            return null;
        }
    }

    public FileInfo addReportFile(MultipartFile file, String reportId, String fileId) throws IOException {
        String originName = file.getOriginalFilename();
        String fileName = FileUtil.mainName(originName) + System.currentTimeMillis() + "." + FileUtil.extName(originName);
        FileUtil.writeBytes(file.getBytes(), BASE_PATH + reportId + "/" + originName);

        FileInfo info = new FileInfo();
        info.setFileId(fileId);
        info.setFilename(originName);
        info.setPath(BASE_PATH + reportId);
        info.setTaskId(null);

        if (fileInfoDao.insert(info) == 1) {
            return info;
        } else {
            return null;
        }
    }
}
