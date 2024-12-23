package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/springboot/file/";

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        synchronized (FileController.class) {
            // 获取当前时间戳
            String flag = System.currentTimeMillis() + "";
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            try {
                // 如果文件存储路径不存在，就进行创建，在项目的根目录下
                if (!FileUtil.isDirectory(filePath)) {
                    FileUtil.mkdir(filePath);
                }
                // 文件存储形式：时间戳-文件名
                FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
                System.out.println(fileName + "--上传成功");
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
            }
            return Result.success(flag);
        }
    }


    /**
     * 获取文件
     */
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(avatar)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    /**
     * wang-editor编辑器文件上传接口
     */
    @PostMapping("/wangeditor")
    public Map<String, Object> wangEditorUpload(@RequestParam MultipartFile file, @RequestParam String type) {
        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();
        try {
            // 文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        Map<String, Object> resMap = new HashMap<>();

        if ("img".equals(type)) {  // 上传图片
            // wangEditor上传图片成功后， 需要返回的参数
            resMap.put("errno", 0);
            resMap.put("data", CollUtil.newArrayList(
                    Dict.create()
                            .set("url", "http://localhost:9090/api/files/" + flag)
                            .set("href", "http://localhost:9090/api/files/" + flag)
            ));
        } else if ("video".equals(type)) {
            // wangEditor上传图片成功后， 需要返回的参数
            resMap.put("errno", 0);
            resMap.put("data",
                    Dict.create()
                            .set("url", "http://localhost:9090/api/files/" + flag));
        } else {
            resMap.put("error", 0);
        }
        return resMap;
    }

}
