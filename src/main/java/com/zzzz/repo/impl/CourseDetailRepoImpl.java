package com.zzzz.repo.impl;

import com.zzzz.repo.CourseDetailRepo;
import com.zzzz.vo.CourseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class CourseDetailRepoImpl implements CourseDetailRepo {
    static final String KEY = "course";
    static final String KEY_LATEST = KEY + ":latest";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, CourseDetail> hashOps;

    @Autowired
    private CourseDetailRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(CourseDetail course) {
        hashOps.put(KEY, course.getCourseId(), course);
    }

    @Override
    public void delete(long courseId) {
        hashOps.delete(KEY, courseId);
    }

    @Override
    public CourseDetail getById(long courseId) {
        return hashOps.get(KEY, courseId);
    }

    @Override
    public void saveLatestThree(List<CourseDetail> latestCourses) {
        redisTemplate.opsForValue().set(KEY_LATEST, latestCourses);
    }

    @Override
    public void deleteLatestThree() {
        redisTemplate.delete(KEY_LATEST);
    }

    @Override
    public List<CourseDetail> getLatestThree(long enterpriseId) {
        return (List<CourseDetail>) redisTemplate.opsForValue().get(KEY_LATEST);
    }
}
