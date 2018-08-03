package com.zzzz.repo.impl;

import com.zzzz.repo.CourseDetailRepo;
import com.zzzz.vo.CourseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class CourseDetailRepoImpl implements CourseDetailRepo {
    static final String KEY = "course";
    static final String KEY_LATEST = KEY + ":latest";
    private final RedisTemplate<String, CourseDetail> redisTemplate;
    private HashOperations<String, Long, CourseDetail> hashOps;
    private ListOperations<String, CourseDetail> listOps;

    @Autowired
    private CourseDetailRepoImpl(RedisTemplate<String, CourseDetail> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
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
        listOps.rightPushAll(KEY_LATEST, latestCourses);
    }

    @Override
    public void deleteLatestThree() {
        redisTemplate.delete(KEY_LATEST);
    }

    @Override
    public List<CourseDetail> getLatestThree(long enterpriseId) {
        return listOps.range(KEY_LATEST, 0, -1);
    }
}
