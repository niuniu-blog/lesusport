package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.constans.Constans;
import com.haichuang.lesusport.service.upload.UploadService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 上传图片Controller
 */
@Controller
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping("uploadFck")
    public void String(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();
        response.setContentType("applicaitonContext/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, MultipartFile> entry : entries) {
            MultipartFile pic = entry.getValue();
            String path = Constans.IMG_URL + uploadService.singleUpload(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
            jsonObject.put("error", 0);
            jsonObject.put("url", path);
            response.getWriter().write(jsonObject.toString());
        }
    }

    /**
     * 异步上传多张图片并回显
     *
     * @param pics
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadPics")
    public @ResponseBody
    List<String> String(@RequestParam MultipartFile[] pics) throws Exception {
        List<String> list = new ArrayList<>();
        for (MultipartFile pic : pics) {
            list.add(Constans.IMG_URL + uploadService.singleUpload(pic.getBytes(), pic.getOriginalFilename(), pic.getSize()));
        }
        return list;
    }

    /**
     * 上传单张图片
     *
     * @param pic
     * @param response
     * @throws IOException
     */
    @RequestMapping("singlePic")
    public void singlePic(MultipartFile pic, HttpServletResponse response) throws Exception {
        String path = uploadService.singleUpload(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", Constans.IMG_URL + path);
        response.getWriter().write(jsonObject.toString());
    }
}
