package com.zzzz.repo.impl;

import com.zzzz.po.CourseCategory;
import com.zzzz.repo.CourseCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class CourseCategoryRepoImpl implements CourseCategoryRepo {
    private static final String KEY = "course_category";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, CourseCategory> hashOps;

    @Autowired
    private CourseCategoryRepoImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(CourseCategory courseCategory) {
        hashOps.put(KEY, courseCategory.getCategoryId(), courseCategory);
    }

    @Override
    public void saveAll(List<CourseCategory> courseCategories) {
        Map<Long, CourseCategory> map = new HashMap<>();
        courseCategories.parallelStream().forEach(it -> map.put(it.getCategoryId(), it));
        hashOps.putAll(KEY, map);
    }

    @Override
    public CourseCategory getById(long courseCategoryId) {
        return hashOps.get(KEY, courseCategoryId);
    }

    @Override
    public CourseCategory getByName(String name) {
        List<CourseCategory> list = getAllUnsorted();
        Optional<CourseCategory> category = list.parallelStream().filter(it -> it.getName().equals(name)).findAny();
        return category.orElse(null);
    }

    @Override
    public List<CourseCategory> getAll() {
        List<CourseCategory> result = getAllUnsorted();
        result.sort(Comparator.comparing(CourseCategory::getCategoryId));
        return result;
    }

    private List<CourseCategory> getAllUnsorted() {
        Set<Long> hKeys = hashOps.keys(KEY);
        List<CourseCategory> result = new ArrayList<>(hKeys.size());
        hKeys.parallelStream().forEach(hKey -> result.add(hashOps.get(KEY, hKey)));
        return result;
    }

    @Override
    public boolean isCached(long courseCategoryId) {
        return hashOps.hasKey(KEY, courseCategoryId);
    }
}
