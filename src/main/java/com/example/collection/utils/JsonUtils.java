package com.example.collection.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    /**
     * readJson.
     *
     * @param multipartFile multipartFile
     * @return obj
     */
    public static Map<String, String> readJson(MultipartFile multipartFile) {
        Map<String, String> result = new HashMap<>();
        try {
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //MultipartFile to string
            File file = new File("/" + fileName);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            String jsonString = FileUtils.readFileToString(file, "UTF-8");

            if (".json".equals(suffixName) || ".txt".equals(suffixName)) {
                result.put("result", jsonString);
                result.put("code", "200");
                result.put("message", "上传成功！");
            } else {
                result.put("result", "");
                result.put("code", "500");
                result.put("message", "请上传正确格式的.json或.txt文件！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "");
            result.put("code", "500");
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * exportJson.
     *
     * @param response response
     * @param obj      obj
     * @param fileName fileName
     */
    public static void exportJson(HttpServletResponse response, Object obj, String fileName) {
        try {
            String jsonString = JSON.toJSONString(obj,
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);

            String fullPath = "/" + fileName;
            File file = new File(fullPath);

            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            write.write(jsonString);
            write.flush();
            write.close();

            FileInputStream fis = new FileInputStream(file);
            // force-download
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, StandardCharsets.UTF_8))));
            response.setCharacterEncoding("utf-8");

            OutputStream os = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            fis.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
