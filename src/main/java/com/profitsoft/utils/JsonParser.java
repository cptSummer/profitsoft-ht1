package com.profitsoft.utils;

import com.profitsoft.entity.Photo;
import com.profitsoft.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class JsonParser {

    public List<?> parse(String path, Class<?> className) throws IOException {
        if (className == null) {
            throw new NullPointerException("Class cannot be null");
        }
        if (path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            return parseJsonToObjectList(reader, className);
        }
    }

    private <T> List<T> parseJsonToObjectList(BufferedReader reader, Class<T> valueType) {
        StringBuilder jsonBuilder = new StringBuilder();
        reader.lines().forEach(jsonBuilder::append);
        if (jsonBuilder.toString().isEmpty()) {
            return null;
        }
        JSONArray jsonArr = new JSONArray(jsonBuilder.toString());
        return IntStream.range(0, jsonArr.length())
                .mapToObj(jsonArr::getJSONObject)
                .map(jsonObj -> createObjectFromJson(jsonObj, valueType))
                .collect(Collectors.toList());
    }

    private <T> T createObjectFromJson(JSONObject jsonObj, Class<T> valueType) {
        if (valueType == User.class) {
            String username = jsonObj.getString("username");
            String email = jsonObj.getString("email");
            Date joinDate = Date.valueOf(jsonObj.getString("joinDate"));
            return valueType.cast(new User(username, email, joinDate));
        }
        if (valueType == Photo.class) {
            String photoName = jsonObj.getString("photoName");
            String photoFormat = jsonObj.getString("photoFormat");
            String photoPath = jsonObj.getString("photoPath");
            String photoTags = jsonObj.getString("photoTags");
            Date uploadDate = Date.valueOf(jsonObj.getString("uploadDate"));
            return valueType.cast(new Photo(photoName, photoFormat, photoPath, photoTags, uploadDate));
        }
        return null;
    }

}
