package com.profitsoft.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilService {
    private final JsonParser jsonParser = new JsonParser();


    public <T> List<T> getParsedJsonList(String directoryPath, Class<T> className) throws IOException {
        List<String> jsonFilesList = getJsonFilesList(directoryPath);
        if (jsonFilesList.isEmpty()) {
            System.out.println("Directory doesn't have any JSON files");
            return null;
        }
        return parseAll(jsonFilesList, className);
    }


    private List<String> getJsonFilesList(String directoryPath) throws IOException {
        try (Stream<Path> pathStream = Files.walk(Paths.get(directoryPath))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(string -> string.endsWith(".json"))
                    .collect(Collectors.toList());
        }
    }

    public <T> List<T> parseAll(List<String> jsonPaths, Class<T> className) {
        ExecutorService executor = Executors.newFixedThreadPool(jsonPaths.size());
        List<T> itemList = Collections.synchronizedList(new ArrayList<>());
        List<Callable<Void>> tasks = new ArrayList<>();

        jsonPaths.forEach(path -> tasks.add(() -> {
            try {
                List<?> items = jsonParser.parse(path, className);
                if(items == null) {
                    return null;
                }
                List<T> filteredItems = items.parallelStream()
                        .filter(className::isInstance)
                        .map(className::cast)
                        .collect(Collectors.toList());
                itemList.addAll(filteredItems);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return null;
        }));

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            System.out.println("Error in task execution: " + e.getMessage());
        }

        executor.shutdown();
        return itemList;
    }




}
