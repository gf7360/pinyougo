package com.pinyougou.shop.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

@RestController
@RequestMapping("/upload")
public class UploadControler {
    //读取配置文件中的fastDFS的IP地址；
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    /**
     * 基于FastDFS实现商品录入图片的上传
     * MultipartFile springMVC接收上传 文件的API
     * @return
     */
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        try {
            //获取文件源名称
            String originalFilename = file.getOriginalFilename();
            //获取文件扩展名；
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //基于工具类实现文件的上传 通过构造方法要求必须传入一个配置文件的类路径
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            //根据文件的内容上传  参数一 文件内容数组 ；参数二 文件扩展名  得到文件的全路径；
            String filePath = fastDFSClient.uploadFile(file.getBytes(), extName);
            //String filUrl = "http://192.168.25.133/"+filePath;
            String fileUrl = FILE_SERVER_URL+filePath;
            return new Result(true,fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }















}












