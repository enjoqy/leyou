package org.junhi.upload.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.junhi.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author junhi
 * @date 2019/7/24 11:27
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;



    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url = this.uploadService.uploadImage(file);

        if(StringUtils.isBlank(url)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(url);

    }

}
