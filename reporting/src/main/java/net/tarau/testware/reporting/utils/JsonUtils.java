package net.tarau.testware.reporting.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * JSON utilities.
 */
public class JsonUtils {

    /**
     * Converts an object to JSON representation.
     *
     * @param value the value to convert
     * @return the serialized version
     */
    public static String toJson(Object value) throws IOException {
        StringWriter sw = new StringWriter();
        toJson(value, sw);
        return sw.toString();
    }

    /**
     * Converts an object to JSON representation.
     *
     * @param value the value to convert
     */
    public static void toJson(Object value, Writer writer) throws IOException {
        ObjectMapper objectMapper = createObjectMapper();
        objectMapper.writeValue(writer, value);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
