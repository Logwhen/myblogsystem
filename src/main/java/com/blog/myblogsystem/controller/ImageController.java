package com.blog.myblogsystem.controller;

import com.aliyun.oss.OSSClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
@RestController
public class ImageController {
    @RequestMapping(value = "saveImg",method = RequestMethod.POST)
    public String uploadImgToOSS(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Part part = null;
        try {
            part = request.getPart("myFileName");// myFileName是文件域的name属性值
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        // 包含原始文件名的字符串
        String fi = part.getHeader("content-disposition");
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length() - 1);
        // 生成实际存储的真实文件名
        String realName = UUID.randomUUID().toString() + fileNameExtension;
        // 图片存放的真实路径
        String realPath = "http://whitealbum.oss-cn-beijing.aliyuncs.com/wangEditor/" + realName;
        // 将文件写入指定路径下
        OSSClient client = new OSSClient("oss-cn-beijing.aliyuncs.com", "LTAI4Fth4afrtSPEA55FnXfe", "5SWPdCDJtWNBRkVL9UwsNsHFkzbuG9");
        String dir = "wangEditor/";
        String ossUrl = "";
        try {
            InputStream inputStream = part.getInputStream();
            ossUrl = dir + realName;
            client.putObject("whitealbum", ossUrl, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.shutdown();
        // 返回图片的URL地址
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(realPath);
        jsonObject.put("errno", 0);
        jsonObject.put("data", jsonArray);
        return jsonObject.toString();
    }
}