package dennisMohle.myZoo.com;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class AnimalNameLoader {

    // Loads names from file and categorizes them by species
    public static Map<String, List<String>> loadNamesFromFile(String filename) {
        Map<String, List<String>> animalNamesMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            String currentSpecies = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.endsWith("Names:")) {
                    currentSpecies = line.replace(" Names:", "").trim().toLowerCase();  // species tag made lowercase to match strings from arrAnimals
                    animalNamesMap.put(currentSpecies, new ArrayList<>());
                } else if (!line.isEmpty() && currentSpecies != null) {
                    String[] names = line.split(", ");
                    animalNamesMap.get(currentSpecies).addAll(Arrays.asList(names));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
            e.printStackTrace();
        }

        System.out.println("Sending back " + animalNamesMap);

        return animalNamesMap;
    }

    // Assigns a name from the list based on species
    public static String assignName(Map<String, List<String>> animalNamesMap, Map<String, Integer> nameIndexMap, String species) {
        List<String> names = animalNamesMap.get(species);
        if (names != null && !names.isEmpty()) {
            int index = nameIndexMap.getOrDefault(species, 0);
            String name = names.get(index % names.size());  // Cycles through names
            nameIndexMap.put(species, index + 1);
            System.out.println("Assigned name: " + name + " for species: " + species);
            return name;
        }
        System.out.println("No names available for species: " + species);
        return "Unnamed " + species; // Default if no names available
    }
}