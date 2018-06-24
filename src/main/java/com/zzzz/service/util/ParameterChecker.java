package com.zzzz.service.util;

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
}
