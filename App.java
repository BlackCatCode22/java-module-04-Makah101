package dennisMohle.myZoo.com;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        System.out.println("\n\n Welcome to My Zoo Program\n\n");
        System.out.println("\n Number of animals is: " + Animal.numOfAnimals);

        // local variables
        String name;
        String species;
        int age;

        // ArrayList of Animal objects
        ArrayList<Animal> animals = new ArrayList<>();

        // Open an external file, parse it line by line, and get age and species
        String animalsFilePath = "arrivingAnimals.txt";
        String namesFilePath = "animalNames.txt";

        // Load names categorized by species
        Map<String, List<String>> animalNamesMap = AnimalNameLoader.loadNamesFromFile(namesFilePath);

        // Track name usage for each species
        Map<String, Integer> nameIndexMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(animalsFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(", ");

                    // Check if the line has at least 1 part
                    if (parts.length >= 1) {
                        String ageAndSpecies = parts[0];

                        // Get age out of 'ageAndSpecies'
                        String[] theParts = ageAndSpecies.split(" ");
                        age = Integer.parseInt(theParts[0]);
                        species = theParts[4];

                        // Assign a name based on species
                        name = AnimalNameLoader.assignName(animalNamesMap, nameIndexMap, species);

                        // Create and add the animal
                        Animal myAnimal = new Animal(name, species, age);
                        animals.add(myAnimal);
                    }
                    System.out.println("\n Number of animals is: " + Animal.numOfAnimals);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + animalsFilePath);
            e.printStackTrace();
        }

        // We now have an arrayList of Animals. Let's output them!
        for (Animal animal : animals){
            System.out.println(animal);
            System.out.println("Animal name: " + animal.getName() + ", Age: " + animal.getAge() + ", Species: " + animal.getSpecies());
        }
        System.out.println("\n Number of animals is: " + Animal.numOfAnimals);

    }
}
