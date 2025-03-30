package com.example.utils;

import java.util.HashSet;
import java.util.Set;

public class FreshSymbolGenerator {
    private static final char[] SYMBOLS = "!@#$%^&*()_+-=[]{}|;:',.<>?/".toCharArray();
    public static final Set<Character> usedChars = new HashSet<>();

    public static Character generateFreshSymbol() {
        // Try lowercase letters first
        for (char c = 'a'; c <= 'z'; c++) {
            if (usedChars.add(c)) return c;
        }

        // Then uppercase letters
        for (char c = 'A'; c <= 'Z'; c++) {
            if (usedChars.add(c)) return c;
        }

        // Then digits
        for (char c = '0'; c <= '9'; c++) {
            if (usedChars.add(c)) return c;
        }

        // Finally, use symbols
        for (char c : SYMBOLS) {
            if (usedChars.add(c)) return c;
        }

        return null; // No fresh symbol left
    }

}
