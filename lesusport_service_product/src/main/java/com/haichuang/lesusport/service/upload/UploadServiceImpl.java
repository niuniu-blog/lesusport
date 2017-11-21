package com.haichuang.lesusport.service.upload;

import com.haichuang.lesusport.common.fastdfs.FastDFSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("uploadService")
@Transactional
public class UploadServiceImpl implements UploadService {
    @Override
    public String singleUpload(byte[] buff, String name, Long size) throws Exception {
        return FastDFSUtil.uploadPic(buff, name, size);
    }
}
