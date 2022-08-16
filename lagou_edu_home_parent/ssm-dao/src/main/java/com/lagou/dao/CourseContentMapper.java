package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/13 12:03
 * @description
 */
public interface CourseContentMapper {

    /*
        根据课程id查询关联的章节信息及章节信息关联的课时信息
     */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /*
        回显章节对应的课程信息
     */
    public Course findCourseByCourseId(Integer courseId);

    /*
        保存章节信息
     */
    public void saveSection(CourseSection courseSection);

    /*
        更新章节信息————回显章节信息让前端 Vue 来完成
     */
    public void updateSection(CourseSection section);

    /*
        修改章节状态
     */
    public void updateSectionStatus(CourseSection section);

    /*
        保存课时
     */
    public void saveLesson(CourseLesson lesson);
}
