package com.unvise.transportbook.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFromFile {

    public static List<List<String>> readByLineFromFile(String filename, String separator) throws IOException {
        List<String> data;
        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            data = lines.collect(Collectors.toList());
        }

        List<List<String>> result = new ArrayList<>();
        for (String s : data) {
            List<String> oneLineValuesList = Stream.of(s.split(separator)).map(String::trim).toList();

            result.add(oneLineValuesList);
        }
        return result;
    }

}
