package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * UUID (Universally Unique Identifier) is commonly used to generate random strings
 * Guaranteed Uniqueness: UUIDs are designed to be unique across space and time. The chance of two UUIDs colliding (being the same) is astronomically low
 * UUIDs follow a standardized format (e.g., 123e4567-e89b-12d3-a456-426614174000), which is useful for logging, debugging, and data organization
 */
public class Randomizer {

    // Generate a random email using UUID
    // e.g., user_123e4567-e89b-12d3-a456-426614174000@example.com
    public static String generateRandomUUIDEmail() {
        String uuid = UUID.randomUUID().toString();
        return "user_" + uuid + "@example.com";
    }

    // Generate Random email using RandomStringUtils form apache commons
    // NOTE: RandomStringUtils.randomAlphabetic() does not include spaces
    public static String generateRandomEmail() {
        String randomString = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        return randomString + "@example.com";
    }

    // Generate Random string using RandomStringUtils form apache commons
    // For strings with spaces or special characters:
    // Use RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyz ")
    // (Note: You’ll need to define the allowed characters explicitly.)
    public static String generateRandomAlphabeticStr(int length) {
        String randomString = RandomStringUtils.randomAlphabetic(length).toLowerCase();
        return randomString;
    }

    public static String generateRandomAlphaNumericStr(int length) {
        String randomString = RandomStringUtils.randomAlphanumeric(length).toLowerCase();
        return randomString;
    }

    public static String generateRandomNumberStr(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
