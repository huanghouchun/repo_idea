package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hhc19
 * @date 2022/8/13 23:27
 * @description
 */
@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    /*
        广告分页查询
     */
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVO promotionAdVO) {

        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdVO);

        return new ResponseResult(true, 200, "广告分页查询成功", pageInfo);
    }

    /*
        图片上传接口
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult promotionAdUpload(MultipartFile file, HttpServletRequest request) {

        try {

            // 1.判断文件是否为空
            if (file == null) {
                throw new RuntimeException("File is empty!");
            }

            // 2.获取项目部署路径 也就是 ssm-web 工程在 tomcat 中的部署路径
            String realPath = request.getServletContext().getRealPath("/");
            String webappsPath = realPath.substring(0, realPath.indexOf("ssm-web"));
            System.err.println(webappsPath); // D:/LGStudy/software/apache-tomcat-8.5.55/webapps/

            // 3.获取原文件名
            String originalFilename = file.getOriginalFilename();

            // 4.生成新文件名
            String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 5.上传文件
            // 上传文件路径
            String uploadPath = webappsPath + "upload\\";
            File filePath = new File(uploadPath, newFileName);

            // 如果目录不在就创建目录
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
                System.out.println("创建目录：" + filePath);
            }

            // 上传图片到指定目录，图片进行了真正的上传
            file.transferTo(filePath);

            Map<String, String> map = new HashMap<>();
            map.put("fileName", newFileName);
            map.put("filePath", "http://localhost:8080/upload/" + newFileName);

            return new ResponseResult(true, 200, "图片上传成功", map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        广告动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(@RequestParam("id") int id, @RequestParam("status") int status) {

        promotionAdService.updatePromotionAdStatus(id, status);

        Map<Object, Object> map = new HashMap<>();
        map.put("status", status);

        return new ResponseResult(true, 200, "广告动态上下线成功了", map);
    }

    /*
        新增&修改广告
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {

        if (null != promotionAd.getId()) {

            // 更新操作
            promotionAdService.updatePromotionAd(promotionAd);
            return new ResponseResult(true, 200, "更新广告成功", null);
        } else {

            // 新增操作
            promotionAdService.savePromotionAd(promotionAd);
            return new ResponseResult(true, 200, "新增广告成功", null);
        }
    }

    /*
        根据 id 回显广告信息
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(int id) {

        PromotionAd ad = promotionAdService.findPromotionAdById(id);

        return new ResponseResult(true, 200, "回显广告成功", ad);
    }
}
