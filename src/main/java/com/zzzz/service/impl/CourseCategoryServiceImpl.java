package com.zzzz.service.impl;

import com.zzzz.dao.CourseCategoryDao;
import com.zzzz.dao.GeneralDao;
import com.zzzz.po.CourseCategory;
import com.zzzz.repo.CourseCategoryRepo;
import com.zzzz.service.CourseCategoryService;
import com.zzzz.service.CourseCategoryServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static com.zzzz.service.CourseCategoryServiceException.ExceptionTypeEnum.*;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    private final GeneralDao generalDao;
    private final CourseCategoryDao courseCategoryDao;
    private final CourseCategoryRepo courseCategoryRepo;
    private final ParameterChecker<CourseCategoryServiceException> checker = new ParameterChecker<>();

    @Autowired
    public CourseCategoryServiceImpl(GeneralDao generalDao, CourseCategoryDao courseCategoryDao,
                                     CourseCategoryRepo courseCategoryRepo) {
        this.generalDao = generalDao;
        this.courseCategoryDao = courseCategoryDao;
        this.courseCategoryRepo = courseCategoryRepo;
    }

    @Override
    @Transactional(rollbackFor = { CourseCategoryServiceException.class, SQLException.class })
    public long insert(String name) throws CourseCategoryServiceException, SQLException {
        // Check if the parameter is valid
        checker.rejectIfNullOrEmpty(name, new CourseCategoryServiceException(EMPTY_COURSE_CATEGORY_NAME));
        boolean isExisting = courseCategoryDao.checkExistenceByName(name);
        if (isExisting)
            throw new CourseCategoryServiceException(COURSE_CATEGORY_NAME_OCCUPIED);

        // Prepare a new category
        CourseCategory category = new CourseCategory();
        category.setName(name);
        courseCategoryDao.insert(category);
        long lastId = generalDao.getLastInsertId();

        // Redis: cache the new one
        courseCategoryRepo.save(category);
        return generalDao.getLastInsertId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseCategory> list() throws SQLException {
        List<CourseCategory> result = courseCategoryRepo.getAll();
        if (result == null || result.isEmpty()) {
            result = courseCategoryDao.list();
            courseCategoryRepo.saveAll(result);
        }
        return result;
    }
}
