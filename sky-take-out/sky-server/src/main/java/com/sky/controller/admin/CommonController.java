package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 通用接口
 */
@RequestMapping("admin/common")
@Slf4j
@RestController
@Api("通用接口")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        log.info("文件上传");
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取后缀 .png
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新的文件名
            String newName = UUID.randomUUID().toString() + substring;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), newName);
            return Result.success(filePath);



        } catch (Exception e) {
            log.error("文件上传失败：{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
