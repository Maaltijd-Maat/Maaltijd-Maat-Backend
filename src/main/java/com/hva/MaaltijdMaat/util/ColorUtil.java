package com.hva.MaaltijdMaat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorUtil {
    /**
     * Generates a hex color code from a hashcode
     *
     * @param hash hashcode as int
     * @return color code as String (#9A55F9)
     */
    public static String hashToHex(int hash) {
        return ("#" + Integer.toHexString(((hash >> 16) & 0xFF)) +
                Integer.toHexString(((hash >> 8) & 0xFF)) +
                Integer.toHexString((hash & 0xFF))).toUpperCase();
    }
}
