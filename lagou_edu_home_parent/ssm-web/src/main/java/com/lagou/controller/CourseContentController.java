package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hhc19
 * @date 2022/8/13 12:34
 * @description
 */
@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    /*
        查询课程内容（章节 + 课时）
     */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(@RequestParam("courseId") Integer courseId) {

        // 调用service
        List<CourseSection> sectionList = courseContentService.findSectionAndLessonByCourseId(courseId);

        // 封装数据并返回
        ResponseResult result = new ResponseResult(true, 200, "章节及课时内容查询成功", sectionList);
        return result;
    }

    /*
        回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(@RequestParam Integer courseId) {

        Course course = courseContentService.findCourseByCourseId(courseId);

        ResponseResult result = new ResponseResult(true, 200, "查询课程信息成功", course);
        return result;
    }

    /*
        新增&更新章节信息

        回显章节信息让前端 Vue 来完成
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection) {

        // 判断是否携带了章节 id
        // 判断携带id是修改操作否则是插入操作
        if (null != courseSection.getId()) {

            // 有 id 更新操作
            courseContentService.updateSection(courseSection);
            ResponseResult result = new ResponseResult(true, 200, "更新章节成功", null);
            return result;
        } else {

            // 无 id 新增操作
            courseContentService.saveSection(courseSection);
            ResponseResult result = new ResponseResult(true, 200, "新增章节成功", null);
            return result;
        }
    }

    /*
        修改章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(@RequestParam int id, @RequestParam int status) {

        courseContentService.updateSectionStatus(id, status);

        // 数据响应
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);

        ResponseResult result = new ResponseResult(true, 200, "修改章节状态成功", map);
        return result;
    }

    /*
        新增课时信息
     */
    @RequestMapping("/saveLesson")
    public ResponseResult saveLesson(@RequestBody CourseLesson courseLesson) {

        courseContentService.saveLesson(courseLesson);

        ResponseResult result = new ResponseResult(true, 200, "新增课时成功", null);
        return result;
    }
}
