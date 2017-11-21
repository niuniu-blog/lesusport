package com.haichuang.lesusport.common.fastdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

/**
 * FastDFS工具类
 */
public class FastDFSUtil {
    public static String uploadPic(byte[] buff, String name, Long size) throws Exception {
        //读取配置文件
        ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
        ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
        //获取tracker
        TrackerClient client = new TrackerClient();
        //获取TrackerServer,包含了storage的信息
        TrackerServer trackerServer = client.getConnection();
        //连接storage
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
        //开始上传文件到fast文件系统
        String ext = FilenameUtils.getExtension(name);
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = new NameValuePair("fileName", name);
        nameValuePairs[1] = new NameValuePair("extName", ext);
        nameValuePairs[2] = new NameValuePair("fileSize", String.valueOf(size));
        String path = storageClient1.upload_file1(buff, ext, nameValuePairs);
        return path;
    }
}
