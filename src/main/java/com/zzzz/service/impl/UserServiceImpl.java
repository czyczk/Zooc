package com.zzzz.service.impl;

import com.zzzz.dao.UserDao;
import com.zzzz.po.User;
import com.zzzz.service.UserService;
import com.zzzz.service.UserServiceException;
import com.zzzz.service.util.ParameterChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.UserServiceException.ExceptionTypeEnum.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    private ParameterChecker<UserServiceException> checker = new ParameterChecker<>();

    @Override
    @Transactional(rollbackFor = { UserServiceException.class, SQLException.class })
    public void insert(String username, String password, String email, String mobile, String avatarUrl) throws UserServiceException, SQLException {
        // All information is required except the avatar URL
        // Check if all required information is valid
        checker.rejectIfNullOrEmpty(username, new UserServiceException(EMPTY_USERNAME));
        checker.rejectIfNullOrEmpty(password, new UserServiceException(EMPTY_PASSWORD));
        // At least one identifier must be provided
        if ((email == null || email.isEmpty()) && (mobile == null || mobile.isEmpty()))
            throw new UserServiceException(MISSING_IDENTIFIER);

        // Check the email if the email is provided
        if (email == null || email.isEmpty())
            email = null;
        else {
            checker.rejectEmailIfInvalid(email, new UserServiceException(INVALID_EMAIL));
            // Check if the email is occupied
            boolean isOccupied = userDao.checkExistenceByEmail(email);
            if (isOccupied)
                throw new UserServiceException(EMAIL_OCCUPIED);
        }

        // Check the mobile if the mobile is provided
        if (mobile == null || mobile.isEmpty())
            mobile = null;
        else {
            checker.rejectMobileIfInvalid(mobile, new UserServiceException(INVALID_MOBILE));
            // Check if the mobile is occupied
            boolean isOccupied = userDao.checkExistenceByMobile(mobile);
        }

        // Insert
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setAvatarUrl(avatarUrl);
        userDao.insert(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(String userId) throws UserServiceException, SQLException {
        // Check if the ID is invalid
        checker.rejectIfNullOrEmpty(userId, new UserServiceException(EMPTY_USER_ID));
        long userIdLong = checker.parseUnsignedLong(userId, new UserServiceException(INVALID_USER_ID));

        User result = userDao.getById(userIdLong);
        return result;
    }

    @Override
    @Transactional(rollbackFor = { UserServiceException.class, SQLException.class })
    public void update(String targetUserId, String username, String password, String email, String mobile, String avatarUrl) throws UserServiceException, SQLException {
        // Check if the ID is invalid
        checker.rejectIfNullOrEmpty(targetUserId, new UserServiceException(EMPTY_USER_ID));
        long targetUserIdLong = checker.parseUnsignedLong(targetUserId, new UserServiceException(INVALID_USER_ID));

        // Check if the target exists
        User user = userDao.getById(targetUserIdLong);
        if (user == null)
            throw new UserServiceException(USER_NOT_EXISTING);

        // Check validity if the information is specified
        if (username != null) {
            if (username.trim().isEmpty())
                throw new UserServiceException(EMPTY_USERNAME);
            else
                user.setUsername(username);
        }
        if (password != null) {
            if (password.isEmpty())
                throw new UserServiceException(EMPTY_PASSWORD);
            else
                user.setPassword(password);
        }
        // If a new email is specified (not the same as before)
        if (email != null && !email.equalsIgnoreCase(user.getEmail())) {
            // Set the email if it's valid
            checker.rejectEmailIfInvalid(email, new UserServiceException(INVALID_EMAIL));
            user.setEmail(email);
        }
        // If a new mobile is specified (not the same as before)
        if (mobile != null && !mobile.equalsIgnoreCase(user.getMobile())) {
            // Set the mobile if it's valid
            checker.rejectMobileIfInvalid(mobile, new UserServiceException(INVALID_MOBILE));
            user.setMobile(mobile);
        }
        // Nullable avatarUrl
        if (avatarUrl != null && avatarUrl.isEmpty())
            avatarUrl = null;
        user.setAvatarUrl(avatarUrl);

        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public long logInByEmail(String email, String password) throws UserServiceException, SQLException {
        // Check if the email and the password are not empty
        checker.rejectIfNullOrEmpty(email, new UserServiceException(EMPTY_EMAIL));
        checker.rejectIfNullOrEmpty(password, new UserServiceException(EMPTY_PASSWORD));

        // Check if the email is valid
        checker.rejectEmailIfInvalid(email, new UserServiceException(INVALID_EMAIL));

        // Check if the user exists
        User user = userDao.getByEmail(email);
        if (user == null)
            throw new UserServiceException(USER_NOT_EXISTING);

        // Check if the password is correct
        if (!user.getPassword().equals(password))
            throw new UserServiceException(INCORRECT_PASSWORD);

        return user.getUserId();
    }

    @Override
    @Transactional(readOnly = true)
    public long logInByMobile(String mobile, String validationCode) throws UserServiceException {
        // TODO not implemented yet
        throw new UnsupportedOperationException();
    }
}
