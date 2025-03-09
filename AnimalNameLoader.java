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

    // Loads names from passed on file string and categorizes them by species
    public static Map<String, List<String>> loadNamesFromFile(String filename) {
        Map<String, List<String>> animalNamesMap = new HashMap<>(); // creates a new map object

        try (Scanner scanner = new Scanner(new File(filename))) {
            String currentSpecies = null;
            while (scanner.hasNextLine()) { //go through filename line by line until no lines
                String line = scanner.nextLine().trim(); //the string is the next line without spaces

                if (line.endsWith("Names:")) { // checks for a common entry to determine if its a name line in the code (Hyena Names:)
                    currentSpecies = line.replace(" Names:", "").trim().toLowerCase();  // species tag made lowercase to match strings from arrAnimals and removes space
                    animalNamesMap.put(currentSpecies, new ArrayList<>()); //sets the current species (ex: hyena) to the map in preperation for giving it a list of names.
                } else if (!line.isEmpty() && currentSpecies != null) { //as long as its not a species type and isnt null sets the names
                    String[] names = line.split(", "); // splits the names string by commas
                    animalNamesMap.get(currentSpecies).addAll(Arrays.asList(names)); // adds all the names to the species type
                }
            }
        } catch (FileNotFoundException e) { //catch in case it cant do its job
            System.out.println("Error: File not found - " + filename);
            e.printStackTrace();
        }

        System.out.println("Sending back " + animalNamesMap); // used to debug as I was getting default values from assignName, corrected by making the species type lowercase to match.

        return animalNamesMap; //returns the map to the function that called it.
    }

    // Assigns a name from the list based on species needs the map created earlier with names, the index for each name and the species
    public static String assignName(Map<String, List<String>> animalNamesMap, Map<String, Integer> nameIndexMap, String species) {
        List<String> names = animalNamesMap.get(species); // set the name list based on which species it requires.
        if (names != null && !names.isEmpty()) { //if as long as names list isnt empty or null,
            int index = nameIndexMap.getOrDefault(species, 0); //get an index value or default to the first one
            String name = names.get(index % names.size());  // Cycles through names
            nameIndexMap.put(species, index + 1);
            System.out.println("Assigned name: " + name + " for species: " + species); // used for debug of name assignment problem.
            return name;
        }
        System.out.println("No names available for species: " + species); //if the if doesnt occur then this will be output and a default will be sent back as the name.
        return "Unnamed " + species; // Default if no names available
    }
}