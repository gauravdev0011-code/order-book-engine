package com.gaurav.orderbook.util;

import java.util.UUID;

/**
 * Utility class for generating unique identifiers
 * used across the trading engine.
 */
public final class IdGenerator {

    // Prevent instantiation
    private IdGenerator() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated"
        );
    }

    /**
     * Generate unique identifier
     *
     * @return UUID string
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}