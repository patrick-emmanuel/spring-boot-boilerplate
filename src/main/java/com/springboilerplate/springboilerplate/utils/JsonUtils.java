package com.springboilerplate.springboilerplate.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;

import java.io.*;
import java.nio.charset.Charset;

public class JsonUtils {

    public static String json(Object o, HttpMessageConverter httpMessageConverter)
            throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        httpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    public static <T> T getElement(String value, Class<T> clazz) {
        return new Gson().fromJson(value, clazz);
    }

    public static <T> T[] getElements(String value, Class<T[]> clazz) {
        return new Gson().fromJson(value, clazz);
    }

    public static <T> String getJson(T element) {
        return new Gson().toJson(element);
    }

    public static <T> String getJsonRecursive(T element) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = mapper.writeValueAsString(element);
            return jsonInString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T[] loadJsonFromFile(String path, Class<T[]> clazz, Class<?> loaderClazz) {
        InputStream in = loaderClazz.getResourceAsStream(path);
        Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
        Gson gson = new Gson();
        return gson.fromJson(reader, clazz);
    }
}
