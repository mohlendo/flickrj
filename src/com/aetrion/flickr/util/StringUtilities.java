/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.aetrion.flickr.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * String utility methods.
 *
 * @author Anthony Eden
 */
public class StringUtilities {

    private StringUtilities() {

    }

    /**
     * Join the array of Strings using the specified delimiter.
     *
     * @param s The String array
     * @param delimiter The delimiter String
     * @return The joined String
     */
    public static String join(String[] s, String delimiter) {
        return join(Arrays.asList(s), delimiter);
    }

    /**
     * Join the Collection of Strings using the specified delimiter.
     *
     * @param s The String collection
     * @param delimiter The delimiter String
     * @return The joined String
     */
    public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

}
