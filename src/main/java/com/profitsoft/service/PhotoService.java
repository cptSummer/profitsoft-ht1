package com.profitsoft.service;

import com.profitsoft.entity.Photo;
import com.profitsoft.utils.CalculationStatistics;
import com.profitsoft.utils.UtilService;
import com.profitsoft.utils.XmlFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public class PhotoService {
    private final XmlFactory xmlFactory = new XmlFactory();
    private final UtilService utilService = new UtilService();
    private final CalculationStatistics calcStatistics = new CalculationStatistics();

    public void getCountOfAttribute(String directoryPath, String attribute) {
        try {
            Field[] fields = Photo.class.getDeclaredFields();
            List<String> attributes = Arrays.stream(fields)
                    .map(Field::getName)
                    .collect(Collectors.toList());
            if (!attribute.isEmpty() && !attributes.contains(attribute)) {
                System.out.println("Wrong attribute name");
                return;
            }
            List<Photo> photos = utilService.getParsedJsonList(directoryPath, Photo.class);
            if (photos == null) {
                return;
            }

            HashMap<String, HashMap<String, Integer>> toXml = new HashMap<>();
            if (attribute.isEmpty()) {
                attributes.stream()
                        .filter(attr -> !attr.equals("photoTags"))
                        .forEach(attr -> toXml.put(attr, calcStatistics.getAttributeCount(photos, attr)));
                toXml.put("photoTags",calcStatistics.getTagAttributeCount(photos));

            } else {

                if (attribute.equals("photoTags")) {
                    toXml.put(attribute,calcStatistics.getTagAttributeCount(photos));
                } else {
                    toXml.put(attribute, calcStatistics.getAttributeCount(photos, attribute));
                }
            }

            xmlFactory.toXml(directoryPath, toXml);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }


}
