package com.zzzz.service.impl;

import com.zzzz.dao.UserDao;
import com.zzzz.repo.UserRepo;
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
    private final UserDao userDao;
    private final UserRepo userRepo;
    private final ParameterChecker<UserServiceException> checker = new ParameterChecker<>();

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRepo userRepo) {
        this.userDao = userDao;
        this.userRepo = userRepo;
    }

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
            if (isOccupied)
                throw new UserServiceException(MOBILE_OCCUPIED);
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
        long userIdLong = parseUserId(userId);
        // Fetch it
        return getUser(userIdLong);
    }

    @Override
    @Transactional(rollbackFor = { UserServiceException.class, SQLException.class })
    public void update(String targetUserId, String username, String password, String email, String mobile, String avatarUrl) throws UserServiceException, SQLException {
        // Check if the ID is invalid
        long targetUserIdLong = parseUserId(targetUserId);

        // Check if the target exists
        User user = getUser(targetUserIdLong);

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
        userRepo.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User logInByEmail(String email, String password) throws UserServiceException, SQLException {
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
        checkIfPasswordIsCorrect(user.getPassword(), password);

        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User logInByMobile(String mobile, String validationCode) throws UserServiceException {
        // TODO not implemented yet
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public User logInById(String userId, String password) throws UserServiceException, SQLException {
        // Check if the parameters are valid
        long userIdLong = parseUserId(userId);
        checker.rejectIfNullOrEmpty(password, new UserServiceException(EMPTY_PASSWORD));

        // Check if the user exists
        User user = getUser(userIdLong);

        // Check if the password is correct
        checkIfPasswordIsCorrect(user.getPassword(), password);

        user.setPassword(null);
        return user;
    }

    private long parseUserId(String userId) throws UserServiceException {
        checker.rejectIfNullOrEmpty(userId, new UserServiceException(EMPTY_USER_ID));
        return checker.parseUnsignedLong(userId, new UserServiceException(INVALID_USER_ID));
    }

    private User getUser(long userId) throws SQLException, UserServiceException {
        // Attempt to get it from Redis first
        // On miss, fetch it from the database and cache it
        User result = userRepo.getUser(userId);
        if (result == null) {
            result = userDao.getById(userId);
            if (result == null)
                throw new UserServiceException(USER_NOT_EXISTING);
            userRepo.saveUser(result);
        }
        return result;
    }

    private void checkIfPasswordIsCorrect(String actual, String input) throws UserServiceException {
        if (!input.equals(actual))
            throw new UserServiceException(INCORRECT_PASSWORD);
    }
}
