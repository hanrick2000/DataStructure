package com.weltond.service;

import com.weltond.domain.User;
import com.weltond.exceptions.UserExistException;
import com.weltond.exceptions.UsersException;

/**
 * @author Weltond Ning
 * @Project MyLoginMVC
 * @Date 5/21/2019
 */
public interface UserService {
    /**
     * Register
     * @param user
     * @throws Exception
     */
    public void register(User user) throws Exception;

    /**
     * Log in
     * @param user
     * @return
     */
    public User login(User user) throws UsersException;

    /**
     * Find if user exists based on user name
     * @param name
     * @return
     * @throws UserExistException
     */
    public boolean findUserByName(String name) throws UserExistException;
}
