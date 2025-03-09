package dennisMohle.myZoo.com;

// Import dependancies
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

        // local variables needed for animal creation
        String name;
        String species;
        int age;

        // ArrayList of Animal objects - array for holding animals
        ArrayList<Animal> animals = new ArrayList<>();

        // creating a String for each file
        String animalsFilePath = "arrivingAnimals.txt";
        String namesFilePath = "animalNames.txt";

        // Load map of names categorized by species, calls a class.method and passes along the file's string
        Map<String, List<String>> animalNamesMap = AnimalNameLoader.loadNamesFromFile(namesFilePath);

        // Track name usage for each species
        Map<String, Integer> nameIndexMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(animalsFilePath))) { //calls the animalArrivals
            while (scanner.hasNextLine()) { // Go through it line by line -
                String line = scanner.nextLine().trim(); // and trim white space
                if (!line.isEmpty()) { //checks that line isnt empty
                    String[] parts = line.split(", "); //splits the line into parts using the commas

                    // Check if the line has at least 1 part
                    if (parts.length >= 1) {
                        String ageAndSpecies = parts[0]; // stores the first part "ex: 8 year old female hyena"

                        // Get age out of 'ageAndSpecies' - takes the age (8) and species (hyena)
                        String[] theParts = ageAndSpecies.split(" "); //split by space
                        age = Integer.parseInt(theParts[0]);
                        species = theParts[4];

                        // Assign a name based on species - calls the assignName method within AnimalNameLoader class. passes the map, its index, and species
                        name = AnimalNameLoader.assignName(animalNamesMap, nameIndexMap, species);

                        // Create animal and add properties
                        Animal myAnimal = new Animal(name, species, age);
                        animals.add(myAnimal); //adds animal to the array we created earlier.
                    }
                    System.out.println("\n Number of animals is: " + Animal.numOfAnimals);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + animalsFilePath);
            e.printStackTrace();  // if theres an error we have this catch to print out some relavent info
        }

        // We now have an arrayList of Animals. Let's output them!
        for (Animal animal : animals){ //cycle through every animal in animals arr
            System.out.println(animal);
            System.out.println("Animal name: " + animal.getName() + ", Age: " + animal.getAge() + ", Species: " + animal.getSpecies());
        }
        System.out.println("\n Number of animals is: " + Animal.numOfAnimals); //tells how many animals we made

    }
}
