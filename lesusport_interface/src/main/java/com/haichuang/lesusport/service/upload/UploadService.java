package com.haichuang.lesusport.service.upload;

public interface UploadService {
    /**
     * 上传单个图片到图片服务器
     *
     * @param buff
     * @param name
     * @param size
     * @return
     */
    String singleUpload(byte[] buff, String name, Long size) throws Exception;
}
