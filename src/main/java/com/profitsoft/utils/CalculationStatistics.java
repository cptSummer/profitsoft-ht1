package com.profitsoft.utils;

import com.profitsoft.entity.Photo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculationStatistics {
    public HashMap<String, Integer> getTagAttributeCount(List<Photo> photos) {
        if (photos == null || photos.isEmpty()) {
            throw new IllegalArgumentException("Photos list cannot be null or empty");
        }

        HashMap<String, Integer> attributeCount = new HashMap<>();
        for (Photo photo : photos) {
            String[] tags = photo.getPhotoTags().trim().split(", ");
            for (String tag : tags) {
                if (attributeCount.containsKey(tag)) {
                    attributeCount.put(tag, attributeCount.get(tag) + 1);
                } else {
                    attributeCount.put(tag, 1);
                }
            }
        }
        return sortDescending(attributeCount);
    }


    public HashMap<String, Integer> getAttributeCount(List<?> items, String attribute) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }
        if (attribute == null || attribute.isEmpty()) {
            throw new IllegalArgumentException("Attribute cannot be null or empty");
        }
        HashMap<String, Integer> attributeCount = new HashMap<>();
        for (Object item : items) {
            String attributeValue = "";
            try {
                Field field = item.getClass().getDeclaredField(attribute);
                field.setAccessible(true);
                Object value = field.get(item);
                attributeValue = value != null ? value.toString() : "";
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }

            if (!attributeValue.isEmpty()) {
                if (attributeCount.containsKey(attributeValue)) {
                    attributeCount.put(attributeValue, attributeCount.get(attributeValue) + 1);
                } else {
                    attributeCount.put(attributeValue, 1);
                }
            }
        }
        return sortDescending(attributeCount);
    }

    public HashMap<String, Integer> sortDescending(HashMap<String, Integer> attributeCount) {
        return attributeCount
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
