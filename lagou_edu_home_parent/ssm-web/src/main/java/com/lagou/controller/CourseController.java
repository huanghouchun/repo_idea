package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hhc19
 * @date 2022/8/11 23:25
 * @description
 */
@RestController // 组合注解：@ResponseBody + @Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO) {

        // 调用 service
        List<Course> courses = courseService.findCourseByCondition(courseVO);

        ResponseResult result = new ResponseResult(true, 200, "响应成功", courses);

        return result;
    }

    /*
        课程图片上传接口
     */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request) {

        try {
            // 1.判断文件是否为空
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty!");
            }

            // 2.获取项目部署路径 也就是 ssm_web 工程在 tomcat 中的部署路径
            // D:\apache-tomcat-8.5.56\webapps\ssm_web\
            String realPath = request.getServletContext().getRealPath("/");
           /* System.err.println("上下文路径" + request.getServletContext().getContextPath()); // 上下文路径/ssm_web
            System.err.println(request.getContextPath()); // /ssm_web
            System.err.println(request.getRequestURL()); // http://localhost:8080/ssm_web/course/courseUpload
            System.err.println(request.getRequestURI()); // /ssm_web/course/courseUpload*/
            // D:\apache-tomcat-8.5.56\webapps
            String webappsPath = realPath.substring(0, realPath.indexOf("ssm-web"));

            // 3.获取原文件名
            // lagou.jpg
            String originalFilename = file.getOriginalFilename();

            // 4.新文件名：防止上传文件名重复 也可以使用 UUID
            // 通过截取获取原文件的后缀名 12421321.jpg
            String newFilename = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 5. 上传文件
            /** 上传文件路径：D://apache-tomcat-8.5.56//webapps//upload*/
            String uploadPath = webappsPath + "upload\\";
            File filePath = new File(uploadPath, newFilename);

            // 如果目录不在就创建目录
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
                System.out.println("创建目录：" + filePath);
            }
            // 上传文件到指定目录 图片进行了真正上传
            file.transferTo(filePath);

            // 6.将文件名和文件路径返回
            Map<String, String> map = new HashMap<String, String>();
            map.put("fileName", newFilename);
            // "filePath": "http://localhost:8080/upload/1597112871741.JPG"
            map.put("filePath", "http://localhost:8080/upload/" + newFilename); // 存储在服务器端的路径

            ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", map);

            return responseResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        新增课程信息及讲师信息

         新增课程信息和修改课程信息要写在同一个方法中，根据逻辑判断就可以判断出它是一个修改请求还是一个更新请求
     */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        ResponseResult result = null;

        if (null == courseVO.getId()) {

            // 前台传来的参数课程 id 为空执行新增操作
            // 调用 service
            courseService.saveCourseOrTeacher(courseVO);
            // 当前是一个保存操作，后台不用向前台返回数据
            result = new ResponseResult(true, 200, "新增成功", null);
        } else {

            // 不为空 更新操作 前台表单页面会传递 id
            courseService.updateCourseOrTeacher(courseVO);
            // 当前是一个保存操作，后台不用向前台返回数据
            result = new ResponseResult(true, 200, "修改成功", null);
        }

        return result;
    }

    /*
        根据 id 查询具体的课程信息及关联的讲师信息
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(@RequestParam("id") Integer id) {
        CourseVO courseVO = courseService.findCourseById(id);
        ResponseResult result = new ResponseResult(true, 200, "根据ID查询课程信息成功", courseVO);
        return result;
    }

    /*
        课程状态管理
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(@RequestParam("id") int id, @RequestParam("status") int status) {

        // 执行修改操作
        // 调用 service，传递参数，完成状态的变更
        courseService.updateCourseStatus(id, status);

        // 保存修改后的状态并返回
        // 响应数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);

        ResponseResult result = new ResponseResult(true, 200, "响应成功", map);
        return result;
    }
}
