package com.zzzz.service.util;

import java.math.BigDecimal;

/**
 * Parameter checker.
 * It provides utilities to help facilitate parameter checking in the service implementations.
 * Methods usually require a parameter of String and a specified possible exception to work.
 * The specified exception will be thrown if the parameter cannot meet the requirements.
 * @param <T> The type of exceptions to be thrown when the parameters are invalid.
 */
public class ParameterChecker<T extends Exception> {
    /**
     * Check if a parameter is null or empty.
     * @param parameter Parameter
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public void rejectIfNullOrEmpty(String parameter, T possibleException) throws T {
        if (parameter == null || parameter.isEmpty())
            throw possibleException;
    }

    /**
     * Parse a parameter to an unsigned long.
     * @param parameter Parameter
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @return The parsed number.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public long parseUnsignedLong(String parameter, T possibleException) throws T {
        long result;
        try {
            result = Long.parseUnsignedLong(parameter);
        } catch (NumberFormatException e) {
            throw possibleException;
        }
        return result;
    }

    /**
     * Parse a parameter to a positive long.
     * @param parameter Parameter
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @return The parsed number.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public long parsePositiveLong(String parameter, T possibleException) throws T {
        long result = parseUnsignedLong(parameter, possibleException);
        if (result == 0)
            throw possibleException;
        return result;
    }

    /**
     * Parse a parameter to a BigDecimal.
     * @param parameter Parameter
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @return The parsed number.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public BigDecimal parseBigDecimal(String parameter, T possibleException) throws T {
        BigDecimal result;
        try {
            result = new BigDecimal(parameter);
        } catch (NumberFormatException e) {
            throw possibleException;
        }
        return result;
    }

    /**
     * Parse a parameter to an unsigned BigDecimal.
     * @param parameter Parameter
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @return The parsed number.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public BigDecimal parseUnsignedBigDecimal(String parameter, T possibleException) throws T {
        BigDecimal result;
        try {
            result = new BigDecimal(parameter);
            if (result.compareTo(BigDecimal.ZERO) < 0)
                throw possibleException;
        } catch (NumberFormatException e) {
            throw possibleException;
        }
        return result;
    }

    /**
     * Reject an email input if it is invalid.
     * @param email Email
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public void rejectEmailIfInvalid(String email, T possibleException) throws T {
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) {
            throw possibleException;
        }
    }

    /**
     * Reject a telephone input if it contains less than 8 chars.
     * @param telephone Telephone
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public void rejectTelephoneIfInvalid(String telephone, T possibleException) throws T {
        if (telephone.length() < 8)
            throw possibleException;
    }

    /**
     * Reject a mobile input if it contains less than 11 chars.
     * @param mobile Mobile
     * @param possibleException The exception to be thrown when the parameter is invalid.
     * @throws T An exception is thrown if the parameter is invalid.
     */
    public void rejectMobileIfInvalid(String mobile, T possibleException) throws T {
        if (mobile.length() < 11)
            throw possibleException;
    }
}
