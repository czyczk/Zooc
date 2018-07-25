package com.zzzz.repo;

import com.zzzz.po.User;

public interface UserRepo {
    /**
     * Save a user.
     * @param user User
     */
    void saveUser(User user);

    /**
     * Update a user. This will also delete related cache about this user,
     * including reservations, orders, moment likes and moment comments.
     * @param user New user
     */
    void updateUser(User user);

    /**
     * Get a user by its ID.
     * @param userId User ID
     * @return User
     */
    User getUser(long userId);
}
