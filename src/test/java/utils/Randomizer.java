package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Utility class for generating random strings and emails.
 */
public class Randomizer {

    /**
     * Generates a random email using a UUID.
     * Example output: user_123e4567-e89b-12d3-a456-426614174000@example.com
     *
     * @return A random email address with a UUID.
     */
    public static String generateRandomUUIDEmail() {
        String uuid = UUID.randomUUID().toString();
        return "user_" + uuid + "@example.com";
    }

    /**
     * Generates a random email using alphanumeric characters.
     * Example output: abc123def456@example.com
     * Uses the  RandomStringUtils.randomAlphabetic() from apache.commons
     * NOTE: This method does not include spaces
     *
     * @return A random email address with a 10-character alphanumeric string.
     */
    public static String generateRandomEmail() {
        String randomString = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        return randomString + "@example.com";
    }

    /**
     * Generates a random alphabetic string of the specified length.
     * Example output: "abcdefghij" (for length 10)
     *
     * @param length The length of the random string.
     * @return A random alphabetic string in lowercase.
     */
    public static String generateRandomAlphabeticStr(int length) {
        String randomString = RandomStringUtils.randomAlphabetic(length).toLowerCase();
        return randomString;
    }

    /**
     * Generates a random alphanumeric string of the specified length.
     * Example output: "abc123def456" (for length 12)
     *
     * @param length The length of the random string.
     * @return A random alphanumeric string in lowercase.
     */
    public static String generateRandomAlphaNumericStr(int length) {
        String randomString = RandomStringUtils.randomAlphanumeric(length).toLowerCase();
        return randomString;
    }

    /**
     * Generates a random numeric string of the specified length.
     * Example output: "1234567890" (for length 10)
     *
     * @param length The length of the random numeric string.
     * @return A random numeric string.
     */
    public static String generateRandomNumberStr(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
