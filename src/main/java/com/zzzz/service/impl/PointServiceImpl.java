package com.zzzz.service.impl;

import com.zzzz.dao.EnterpriseDao;
import com.zzzz.dao.PointDao;
import com.zzzz.dao.UserDao;
import com.zzzz.po.Point;
import com.zzzz.repo.EnterpriseRepo;
import com.zzzz.repo.PointRepo;
import com.zzzz.repo.UserRepo;
import com.zzzz.service.PointService;
import com.zzzz.service.PointServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.PointServiceException.ExceptionTypeEnum.*;

@Service
public class PointServiceImpl implements PointService {
    private final PointDao pointDao;
    private final UserDao userDao;
    private final EnterpriseDao enterpriseDao;
    private final PointRepo pointRepo;
    private final UserRepo userRepo;
    private final EnterpriseRepo enterpriseRepo;
    private final ParameterChecker<PointServiceException> checker = new ParameterChecker<>();

    @Autowired
    public PointServiceImpl(PointDao pointDao, UserDao userDao, EnterpriseDao enterpriseDao,
                            PointRepo pointRepo, UserRepo userRepo, EnterpriseRepo enterpriseRepo) {
        this.pointDao = pointDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.pointRepo = pointRepo;
        this.userRepo = userRepo;
        this.enterpriseRepo = enterpriseRepo;
    }

    @Override
    @Transactional(rollbackFor = { PointServiceException.class, SQLException.class })
    public void incrBy(String userId, String enterpriseId, String numPoints) throws PointServiceException, SQLException {
        long userIdLong = parseUserId(userId);
        long enterpriseIdLong = parseEnterpriseId(enterpriseId);
        int numPointsInt = parseNumPoints(numPoints);
        checkIfTheUserExists(userIdLong);
        checkIfTheEnterpriseExists(enterpriseIdLong);

        // Increment it
        pointDao.incrBy(userIdLong, enterpriseIdLong, numPointsInt);
        // Cache it if not done
        Long point = pointRepo.getByPk(userIdLong, enterpriseIdLong);
        if (point == null) {
            point = pointDao.getByPk(userIdLong, enterpriseIdLong).getPoint();
            pointRepo.save(userIdLong, enterpriseIdLong, point);
        } else {
            pointRepo.incrByIfExisting(userIdLong, enterpriseIdLong, numPointsInt);
        }
    }

    @Override
    public void decrBy(String userId, String enterpriseId, String numPoints) throws PointServiceException, SQLException {
        long userIdLong = parseUserId(userId);
        long enterpriseIdLong = parseEnterpriseId(enterpriseId);
        int numPointsInt = parseNumPoints(numPoints);
        checkIfTheUserExists(userIdLong);
        checkIfTheEnterpriseExists(enterpriseIdLong);

        // Decrement it
        Long point = pointRepo.getByPk(userIdLong, enterpriseIdLong);
        if (point == null) {
            point = pointDao.getByPk(userIdLong, enterpriseIdLong).getPoint();
            pointRepo.save(userIdLong, enterpriseIdLong, point);
        }

        if (point - numPointsInt < 0)
            throw new PointServiceException(NOT_ENOUGH_POINTS);
        pointDao.decrBy(userIdLong, enterpriseIdLong, numPointsInt);
        pointRepo.decrByIfExisting(userIdLong, enterpriseIdLong, numPointsInt);
    }

    @Override
    public long getByPk(String userId, String enterpriseId) throws PointServiceException, SQLException {
        long userIdLong = parseUserId(userId);
        long enterpriseIdLong = parseEnterpriseId(enterpriseId);
        checkIfTheUserExists(userIdLong);
        checkIfTheEnterpriseExists(enterpriseIdLong);

        // Try to get it from cache
        Long point = pointRepo.getByPk(userIdLong, enterpriseIdLong);
        if (point == null) {
            // Try to get it from the database
            Point pointEntry = pointDao.getByPk(userIdLong, enterpriseIdLong);
            if (pointEntry == null) {
                // Create a new point entry with the default values
                pointDao.insertWithDefaultValues(userIdLong, enterpriseIdLong);
                point = 0L;
            } else {
                point = pointEntry.getPoint();
            }
            // Cache it
            pointRepo.save(userIdLong, enterpriseIdLong, point);
        }
        return point;
    }

    private long parseUserId(String userId) throws PointServiceException {
        checker.rejectIfNullOrEmpty(userId, new PointServiceException(EMPTY_USER_ID));
        return checker.parseUnsignedLong(userId, new PointServiceException(INVALID_USER_ID));
    }

    private long parseEnterpriseId(String enterpriseId) throws PointServiceException {
        checker.rejectIfNullOrEmpty(enterpriseId, new PointServiceException(EMPTY_ENTERPRISE_ID));
        return checker.parseUnsignedLong(enterpriseId, new PointServiceException(INVALID_ENTERPRISE_ID));
    }

    private int parseNumPoints(String numPoints) throws PointServiceException {
        checker.rejectIfNullOrEmpty(numPoints, new PointServiceException(EMPTY_POINT_DELTA));
        return checker.parseUnsignedInt(numPoints, new PointServiceException(INVALID_POINT_DELTA));
    }

    private void checkIfTheUserExists(long userId) throws SQLException, PointServiceException {
        boolean isExisting = userRepo.isCached(userId) || userDao.checkExistenceById(userId);
        if (!isExisting)
            throw new PointServiceException(USER_NOT_EXISTING);
    }

    private void checkIfTheEnterpriseExists(long enterpriseId) throws SQLException, PointServiceException {
        boolean isExisting = enterpriseRepo.isCached(enterpriseId) || enterpriseDao.checkExistenceById(enterpriseId);
        if (!isExisting)
            throw new PointServiceException(ENTERPRISE_NOT_EXISTING);
    }
}
