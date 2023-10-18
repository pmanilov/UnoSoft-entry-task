package com.manilov;

import java.io.*;
import java.util.*;

public class Main {
    private static int maxElements = 0;
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        if (args.length != 1) {
            System.err.println("Используйте: java -jar {название проекта}.jar тестовый-файл.txt");
            System.exit(1);
        }
        String inputFileName = args[0];
        List<List<String>> lines = readLinesFromTxt(inputFileName).stream().toList();
        List<List<Integer>> groups = group(lines);
        Collections.sort(groups, Comparator.comparingInt((List<Integer> list) -> list.size()).reversed());
        int count = 0;
        for (List<Integer> group : groups) {
            if (group.size() > 1) {
                count++;
            } else {
                break;
            }
        }
        System.out.println("Число групп с более чем одним элементом: " + count);
        writeGroupsToTxt(lines, groups, count, "result.txt");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Время выполнения программы: " + executionTime / 1000.0 + " секунд");
    }

    private static Set<List<String>> readLinesFromTxt(String fileName) {
        Set<List<String>> lines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isValidLine(line)) {
                    List<String> lineList = new ArrayList<>(Arrays.stream(line.split(";", -1)).toList());
                    /*for (int i = 0; i < lineList.size(); i++) {
                        lineList.set(i, lineList.get(i).replaceAll("\"", ""));
                    }*/
                    lines.add(lineList);
                    if(lineList.size() > maxElements) {
                        maxElements = lineList.size();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }

    public static void writeGroupsToTxt(List<List<String>> lines, List<List<Integer>> groups, int count, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(count + "\n");
            for (int i = 0; i < groups.size(); i++) {
                writer.write("Группа " + (i + 1) +"\n");
                for (Integer index : groups.get(i)) {
                    //StringBuilder line = new StringBuilder("\"");
                    StringBuilder line = new StringBuilder();
                    for (int j = 0; j < lines.get(index).size(); j++) {
                        line.append(lines.get(index).get(j));
                        //String separator = j == lines.get(index).size() - 1 ? "\"" : "\";\"";
                        String separator = j == lines.get(index).size() - 1 ? "" : ";";
                        line.append(separator);
                    }
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean isValidLine(String line) {
        return line.matches("(\"[\\d+.]*\";|;)+(\"[\\d+.]*\"|)?");
    }

    private static List<List<Integer>> group(List<List<String>> lines) {
        Map<Integer, Set<Integer>> groupIndex = new HashMap<>();
        for (int j = 0; j < maxElements; j++) {
            Map<String, Set<Integer>> groupColumn = new HashMap<>();
            for (int i = 0; i < lines.size(); i++) {
                if (j >= lines.get(i).size() || lines.get(i).get(j).isEmpty()) {
                    continue;
                }
                if (!groupColumn.containsKey(lines.get(i).get(j))) {
                    groupColumn.put(lines.get(i).get(j), new HashSet<>());

                }
                groupColumn.get(lines.get(i).get(j)).add(i);
            }
            for (Set<Integer> group : groupColumn.values()) {
                for (Integer value : group) {
                    if (!groupIndex.containsKey(value)) {
                        groupIndex.put(value, group);
                    } else {
                        groupIndex.get(value).addAll(group);
                    }
                }
            }
        }
        int[] visited = new int[lines.size()];
        List<List<Integer>> result = new ArrayList<>();
        for(Map.Entry<Integer, Set<Integer>> group : groupIndex.entrySet()) {
            if(visited[group.getKey()] != 0){
                continue;
            }
            for (Integer value : group.getValue()) {
                if(!value.equals(group.getKey())){
                    visited[value] = 1;
                }
            }
            result.add(group.getValue().stream().toList());
        }
        return result;
    }
}