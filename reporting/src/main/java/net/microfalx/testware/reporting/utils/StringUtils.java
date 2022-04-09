package net.microfalx.testware.reporting.utils;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Various utilities around strings.
 */
public class StringUtils {

    /**
     * Gets the short class name, similar with Class.getSimpleName().
     *
     * @param name a class name
     * @return class short name or null if class object is null
     */
    public static String shortClassName(String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(i + 1);
        }
        return name;
    }

    /**
     * Shorten the given string to the given length, preserving the initial and final substrings
     * and inserting "..." in the middle if the string was shortened.
     *
     * @param value  original string
     * @param length maximum length, including "..." (value less than 3 will be treated as 3)
     * @return original string or shortened string
     */
    public static String truncateMiddle(String value, int length) {
        return truncateMiddle(value, length, "...");
    }

    /**
     * Shorten the given string to the given length, preserving the initial and final substrings
     * and inserting <code>truncationMessage</code> in the middle if the string was shortened.
     *
     * @param value             original string
     * @param length            maximum length, including <code>truncationMessage</code>
     *                          (value less than <code>truncationMessage.length()</code> will be treated as <code>truncationMessage.length()</code>)
     * @param truncationMessage the truncation message
     * @return original string or shortened string
     */
    public static String truncateMiddle(String value, int length, String truncationMessage) {
        if (value == null) {
            return null;
        }
        if (truncationMessage == null) {
            truncationMessage = "...";
        }
        if (value.length() > length) {
            if (value.length() <= truncationMessage.length() || length <= truncationMessage.length())
                return truncationMessage;

            StringBuilder builder = new StringBuilder(Math.max(length, truncationMessage.length()));
            builder.append(value.substring(0, length / 2 - truncationMessage.length() / 2));
            builder.append(truncationMessage);
            builder.append(value.substring(value.length() - (length - length / 2) + (truncationMessage.length() - truncationMessage.length() / 2)));
            return builder.toString();
        }
        return value;
    }

    public static String escapeHtml(String value) {
        if (value == null) {
            return null;
        }
        StringBuilder out = new StringBuilder(Math.max(16, value.length()));
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c > 127 || c == '"' || c == '\'' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }


    public static String escapeAttribute(String value) {
        if (value == null) {
            return null;
        }
        value = value.replace("\n", "\\n");
        return escapeHtml(value);
    }

    /**
     * Returns whether the string is empty or null.
     *
     * @param value the value
     * @return <code>true</code> if empty or null, <code>false</code>
     */
    public static boolean isEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }

    /**
     * Returns whether the string is not empty or null.
     *
     * @param value the value
     * @return <code>true</code> if not empty or null, <code>false</code>
     */
    public static boolean isNotEmpty(CharSequence value) {
        return !isEmpty(value);
    }

    /**
     * Capitalizes the given string
     *
     * @param value the string value
     * @return capitalized string
     */
    public static String capitalize(String value) {
        if (isEmpty(value)) {
            return value;
        }
        value = value.toLowerCase().trim();
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    /**
     * Returns a comma separated string with a name for each item in the collection.
     *
     * @param items items
     * @return a non-null string
     */
    public static String toName(Iterable<?> items) {
        if (items == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = items.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            builder.append(Objects.toString(next));
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    /**
     * Returns a '&gt;' separated string with a name for each item in the collection.
     *
     * @param items items
     * @return a non-null string
     */
    public static String toCategory(Iterable<?> items) {
        if (items == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = items.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            builder.append(Objects.toString(next));
            if (iterator.hasNext()) {
                builder.append(" > ");
            }
        }
        return builder.toString();
    }

    /**
     * Capitalizes all the words in the given string.
     * <p>
     * If the string has <code>_</code> or <code>-</code> character, that would be replaced by <code> </code>.
     *
     * @param value the string value
     * @return capitalized string
     */
    public static String capitalize(String value, boolean all) {
        if (isEmpty(value)) {
            return value;
        }

        value = value.replace('_', ' ');
        value = value.replace('+', ' ');
        value = value.toLowerCase().trim();

        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(value, " ");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.length() > 0) {
                token = Character.toUpperCase(token.charAt(0)) + token.substring(1);
            }
            builder.append(token);
            if (builder.length() > 0) {
                builder.append(" ");
            }
        }
        return builder.toString().trim();
    }

    public static String textToHtml(String text, int maxLines, boolean preserveSpaces) {
        if (text == null) {
            return "";
        }

        StringTokenizer st = new StringTokenizer(text, "\n", false);
        if (!st.hasMoreTokens()) {
            return "";
        }

        int count = 0;
        StringBuilder builder = new StringBuilder(200);

        while (st.hasMoreTokens()) {
            if (count++ > maxLines) {
                break;
            }
            String line = st.nextToken();
            if (!preserveSpaces) {
                line = line.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            }
            builder.append(line);
            if (st.hasMoreElements()) {
                if (preserveSpaces) {
                    builder.append('\n');
                } else {
                    builder.append("<br />");
                }
            }
        }

        return builder.toString();
    }

    public static String textToHtml(String text) {
        return textToHtml(text, true);
    }

    public static String textToHtml(String text, boolean preserveSpaces) {
        return textToHtml(text, Integer.MAX_VALUE, preserveSpaces);
    }
}
