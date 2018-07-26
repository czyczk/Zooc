package com.zzzz.service.impl;

import com.zzzz.dao.MomentDao;
import com.zzzz.dao.MomentImgDao;
import com.zzzz.po.MomentImg;
import com.zzzz.service.MomentImgService;
import com.zzzz.service.MomentImgServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.zzzz.service.MomentImgServiceException.ExceptionTypeEnum.*;

@Service
public class MomentImgServiceImpl implements MomentImgService {
    private final MomentDao momentDao;
    private final MomentImgDao momentImgDao;
    // The maximum number of image can be contained in a moment is 9 in this implementation.
    private static final short MAX_NUM_IMG = 9;
    private final ParameterChecker<MomentImgServiceException> checker = new ParameterChecker<>();

    @Autowired
    public MomentImgServiceImpl(MomentDao momentDao, MomentImgDao momentImgDao) {
        this.momentDao = momentDao;
        this.momentImgDao = momentImgDao;
    }

    @Override
    @Transactional(rollbackFor = { MomentImgServiceException.class, SQLException.class })
    public void updateImgUrls(String momentId, List<String> imgUrls) throws MomentImgServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentImgServiceException(EMPTY_MOMENT_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentImgServiceException(INVALID_MOMENT_ID));
        if (imgUrls == null || imgUrls.isEmpty())
            throw new MomentImgServiceException(EMPTY_IMG_URLS);
        if (imgUrls.size() > MAX_NUM_IMG)
            throw new MomentImgServiceException(INVALID_IMG_URLS);
        for (String url : imgUrls) {
            if (url == null || url.trim().isEmpty()) {
                throw new MomentImgServiceException(INVALID_IMG_URLS);
            }
        }

        // Check if the moment exists
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentImgServiceException(MOMENT_NOT_EXISTING);

        short newSize = (short) imgUrls.size();

        // Get images of the moment
        List<MomentImg> existingImgs = momentImgDao.list(momentIdLong);

        // For the overlapping ones, update them if necessary
        for (int i = 0; i < existingImgs.size() && i < newSize; i++) {
            MomentImg img = existingImgs.get(i);
            if (!img.getImgUrl().equals(imgUrls.get(i))) {
                img.setImgUrl(imgUrls.get(i));
                momentImgDao.update(img);
            }
        }

        // Insert the new ones
        for (short i = (short) existingImgs.size(); i < newSize; i++) {
            MomentImg img = new MomentImg();
            img.setMomentImgIndex(i);
            img.setMomentId(momentIdLong);
            img.setImgUrl(imgUrls.get(i));
            momentImgDao.insert(img);
        }
        // Or delete the ones that no longer exist
        for (short i = newSize; i < existingImgs.size(); i++) {
            momentImgDao.delete(i, momentIdLong);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MomentImg> list(String momentId) throws MomentImgServiceException, SQLException {
        // Check if the parameters are valid
        checker.rejectIfNullOrEmpty(momentId, new MomentImgServiceException(EMPTY_MOMENT_ID));
        long momentIdLong = checker.parseUnsignedLong(momentId, new MomentImgServiceException(INVALID_MOMENT_ID));

        // Check if the moment exists
        boolean isExisting = momentDao.checkExistenceById(momentIdLong);
        if (!isExisting)
            throw new MomentImgServiceException(MOMENT_NOT_EXISTING);

        // Get images of the moment
        return momentImgDao.list(momentIdLong);
    }
}
