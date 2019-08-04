package org.junhi.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author junhi
 * @date 2019/7/24 14:04
 */
@Service
public class UploadService {

    /**
     * 上传图片后缀的白名单
     */
    private static final List<String> CONTENT_TYPES = Arrays.asList(
            "image/gif", "application/x-img", "image/jpeg", "application/x-jpg", "image/jpeg"
    );

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    public String uploadImage(MultipartFile file) {
            //获取文件的原始名字
            String originalFilename = file.getOriginalFilename();
            //检验文件类型
            //从请求头里获取文件类型
            String contentType = file.getContentType();
            if (!CONTENT_TYPES.contains(contentType)) {
                LOGGER.info("文件类型不合法： {}" + originalFilename);
                return null;
            }

        try {
            //检验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法： {}" + originalFilename);
                return null;
            }

            //保存到服务器
//            file.transferTo(new File("H:\\pythonimg\\test02\\2012车模特大展豪乳\\" + originalFilename));
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);

            //返回url，进行回显
//            return "http://image.leyou.com/" + originalFilename;
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误: {}" + originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
