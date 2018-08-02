package com.zzzz.repo;

import com.zzzz.vo.CourseDetail;

import java.util.List;

public interface CourseDetailRepo {
    void save(CourseDetail course);
    void delete(long courseId);
    CourseDetail getById(long courseId);
    void saveLatestThree(List<CourseDetail> latestCourses);
    void deleteLatestThree();
    List<CourseDetail> getLatestThree(long enterpriseId);
}
