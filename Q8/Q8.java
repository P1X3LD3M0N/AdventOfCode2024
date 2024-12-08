import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Q8 {
    public static int part1(char[][] grid) {
        Map<Character, List<List<Integer>>> antennaSpots = new HashMap<>();
        Set<List<Integer>> uniqueNodes = new HashSet<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char cell = grid[i][j];
                if (cell == '.') {
                    continue;
                }

                if (!antennaSpots.containsKey(cell)) {
                    antennaSpots.put(cell, new ArrayList<>());
                }

                antennaSpots.get(cell).add(List.of(i, j));
            }
        }

        for (char antenna : antennaSpots.keySet()) {
            List<List<Integer>> spots = antennaSpots.get(antenna);
            for (int i = 0; i < spots.size(); i++) {
                for (int j = i + 1; j < spots.size(); j++) {
                    int x1 = spots.get(i).get(0);
                    int y1 = spots.get(i).get(1);
                    int x2 = spots.get(j).get(0);
                    int y2 = spots.get(j).get(1);

                    int disx = x1 - x2;
                    int disy = y1 - y2;

                    int nodex1 = x1 + disx;
                    int nodey1 = y1 + disy;

                    int nodex2 = x2 - disx;
                    int nodey2 = y2 - disy;

                    if (nodex1 >= 0 && nodey1 >= 0 && nodex1 < grid.length && nodey1 < grid[0].length) {
                        List<Integer> node1 = List.of(nodex1, nodey1);

                        if (!uniqueNodes.contains(node1)) {
                            uniqueNodes.add(node1);
                        }
                    }

                    if (nodex2 < grid.length && nodey2 < grid[0].length && nodex2 >= 0 && nodey2 >= 0) {
                        List<Integer> node2 = List.of(nodex2, nodey2);

                        if (!uniqueNodes.contains(node2)) {
                            uniqueNodes.add(node2);
                        }
                    }
                }
            }
        }
        return uniqueNodes.size();
    }

    public static int part2(char[][] grid) {
        Map<Character, List<List<Integer>>> antennaSpots = new HashMap<>();
        Set<List<Integer>> uniqueNodes = new HashSet<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char cell = grid[i][j];
                if (cell == '.') {
                    continue;
                }

                if (!antennaSpots.containsKey(cell)) {
                    antennaSpots.put(cell, new ArrayList<>());
                }

                antennaSpots.get(cell).add(List.of(i, j));
            }
        }

        for (char antenna : antennaSpots.keySet()) {
            List<List<Integer>> spots = antennaSpots.get(antenna);
            for (int i = 0; i < spots.size(); i++) {
                for (int j = i + 1; j < spots.size(); j++) {
                    List<Integer> antenna1 = spots.get(i);
                    List<Integer> antenna2 = spots.get(j);

                    if (!uniqueNodes.contains(antenna1)) {
                        uniqueNodes.add(antenna1);
                    }

                    if (!uniqueNodes.contains(antenna2)) {
                        uniqueNodes.add(antenna2);
                    }

                    int x1 = spots.get(i).get(0);
                    int y1 = spots.get(i).get(1);
                    int x2 = spots.get(j).get(0);
                    int y2 = spots.get(j).get(1);

                    int disx = x1 - x2;
                    int disy = y1 - y2;

                    int nodex1 = x1 + disx;
                    int nodey1 = y1 + disy;

                    int nodex2 = x2 - disx;
                    int nodey2 = y2 - disy;

                    List<Integer> node1 = List.of(nodex1, nodey1);
                    List<Integer> node2 = List.of(nodex2, nodey2);

                    while (nodex1 >= 0 && nodey1 >= 0 && nodex1 < grid.length && nodey1 < grid[0].length) {
                        if (!uniqueNodes.contains(node1)) {
                            uniqueNodes.add(node1);
                        }

                        nodex1 = nodex1 + disx;
                        nodey1 = nodey1 + disy;
                        node1 = List.of(nodex1, nodey1);
                    }

                    while (nodex2 < grid.length && nodey2 < grid[0].length && nodex2 >= 0 && nodey2 >= 0) {
                        if (!uniqueNodes.contains(node2)) {
                            uniqueNodes.add(node2);
                        }

                        nodex2 = nodex2 - disx;
                        nodey2 = nodey2 - disy;
                        node2 = List.of(nodex2, nodey2);
                    }
                }
            }
        }
        return uniqueNodes.size();
    }

    public static void main(String args[]){
        String fileName = "input.txt";
        List<char[]> gridList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                gridList.add(line.toCharArray());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        char[][] grid = gridList.toArray(new char[0][]);

        System.out.println("part 1: " + part1(grid));
        System.out.println("part 2: " + part2(grid));
    }
}
