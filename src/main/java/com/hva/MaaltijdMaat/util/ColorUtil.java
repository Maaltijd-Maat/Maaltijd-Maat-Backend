package com.hva.MaaltijdMaat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorUtil {
    public static String intToARGB(int i) {
        return "#" + Integer.toHexString(((i >> 16) & 0xFF)) +
                Integer.toHexString(((i >> 8) & 0xFF)) +
                Integer.toHexString((i & 0xFF));
    }
}
