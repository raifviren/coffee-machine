package coffeeMachine.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Json utils for serialization and deserialization
 * @author Virender Bhargav
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final ObjectMapper NON_EMPTY_OBJECT_MAPPER;
    private static final ObjectMapper OBJECT_MAPPER_WITH_IGNORE_NULL;
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    static {
        OBJECT_MAPPER = new ObjectMapper();
        NON_EMPTY_OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER_WITH_IGNORE_NULL = new ObjectMapper();
        OBJECT_MAPPER_WITH_IGNORE_NULL.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        NON_EMPTY_OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public static <T> String serialize(T object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> String serialize(T object, Class view) throws JsonProcessingException {
        return OBJECT_MAPPER.writerWithView(view).writeValueAsString(object);
    }


    public static <T> T deserialize(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static <T> T deserialize(String json, Class<T> clazz, Class view) throws IOException {
        return OBJECT_MAPPER.readerWithView(view).forType(clazz).readValue(json);
    }

}
