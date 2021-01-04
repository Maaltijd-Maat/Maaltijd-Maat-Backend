package com.hva.MaaltijdMaat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorUtil {
    private static final Random random = new Random();
    /**
     * Generates a hex color code from a hashcode
     * @return color code as String (#9A55F9)
     */
    public static String randomColor() {
        return String.format("#%06x", random.nextInt(0xffffff + 1));
    }
}
