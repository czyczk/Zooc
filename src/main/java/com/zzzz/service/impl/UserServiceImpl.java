package com.zzzz.service.impl;

import com.zzzz.dao.UserDao;
import com.zzzz.po.User;
import com.zzzz.service.UserService;
import com.zzzz.service.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static com.zzzz.service.UserServiceException.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = UserServiceException.class)
    public void insert(String username, String password, String email, String telephone, String avatarUrl) throws UserServiceException {
        // All information is required except the avatar URL
        // Check if all required information is valid
        if (username == null || username.trim().isEmpty())
            throw new UserServiceException(EMPTY_USERNAME);
        if (password == null || password.isEmpty())
            throw new UserServiceException(EMPTY_PASSWORD);
        if (email == null || email.isEmpty())
            throw new UserServiceException(EMPTY_EMAIL);
        if (!checkEmailValidity(email))
            throw new UserServiceException(INVALID_EMAIL);
        if (telephone == null || telephone.isEmpty())
            throw new UserServiceException(EMPTY_TELEPHONE);
        if (!checkTelephoneValidity(telephone))
            throw new UserServiceException(INVALID_TELEPHONE);

        try {
            // Check if the email is occupied
            boolean isOccupied = userDao.checkExistenceByEmail(email);
            if (isOccupied)
                throw new UserServiceException(EMAIL_OCCUPIED);

            // Insert
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setTelephone(telephone);
            user.setAvatarUrl(avatarUrl);
            userDao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserServiceException(INTERNAL_ERROR);
        }
    }

    private boolean checkEmailValidity(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) {
            return false;
        }
        return true;
    }

    private boolean checkTelephoneValidity(String telephone) {
        // A telephone number is invalid if it contains less than 8 chars
        return telephone.length() >= 8;
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(long userId) throws UserServiceException {
        User result;
        try {
            result = userDao.getById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserServiceException(INTERNAL_ERROR);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = UserServiceException.class)
    public void update(long targetUserId, String username, String password, String email, String telephone, String avatarUrl) throws UserServiceException {
        try {
            // Check if the target exists
            User user = userDao.getById(targetUserId);
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
            if (email != null) {
                if (!checkEmailValidity(email))
                    throw new UserServiceException(EMPTY_EMAIL);
                else
                    user.setEmail(email);
            }
            if (telephone != null) {
                if (!checkTelephoneValidity(telephone))
                    throw new UserServiceException(EMPTY_TELEPHONE);
                else
                    user.setTelephone(telephone);
            }
            // Nullable avatarUrl
            if (avatarUrl != null && avatarUrl.isEmpty())
                avatarUrl = null;
            user.setAvatarUrl(avatarUrl);

            userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserServiceException(INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long logIn(String email, String password) throws UserServiceException {
        // Check if the email and the password are not empty
        if (email == null || email.isEmpty())
            throw new UserServiceException(EMPTY_EMAIL);
        if (password == null || password.isEmpty())
            throw new UserServiceException(EMPTY_PASSWORD);

        // Check if the email is valid
        if (!checkEmailValidity(email))
            throw new UserServiceException(INVALID_EMAIL);

        try {
            // Check if the user exists
            User user = userDao.getByEmail(email);
            if (user == null)
                throw new UserServiceException(USER_NOT_EXISTING);

            // Check if the password is correct
            if (!user.getPassword().equals(password))
                throw new UserServiceException(INCORRECT_PASSWORD);

            return user.getUserId();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserServiceException(INTERNAL_ERROR);
        }
    }
}
