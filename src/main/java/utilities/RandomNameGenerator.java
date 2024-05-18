package utilities;

import java.util.Random;

/**
 * Utility class for generating random names.
 */
public class RandomNameGenerator  {

    private static final String[] NAMES = {
            "Emma", "Olivia", "Ava", "Isabella", "Sophia", "Mia",
            "Charlotte", "Amelia", "Evelyn", "Abigail", "Harper",
            "Emily", "Elizabeth", "Avery", "Sofia", "Ella", "Madison",
            "Scarlett", "Victoria", "Aria", "Grace", "Chloe", "Camila",
            "Penelope", "Riley", "Layla", "Lila", "Aurora", "Maya",
            "Audrey", "Nova", "Savannah", "Claire", "Bella", "Skylar",
            "Lucy", "Paisley", "Everly", "Aaliyah", "Ellie", "Stella",
            "Violet", "Hazel", "Ariana", "Luna", "Elena", "Piper",
            "Arianna", "Gianna", "Hannah", "William", "Liam", "Noah",
            "Oliver", "Elijah", "Lucas", "Mason", "Logan", "Alexander",
            "Ethan", "Jacob", "Michael", "Daniel", "Henry", "Jackson",
            "Sebastian", "Aiden", "Matthew", "Samuel", "David", "Joseph",
            "Carter", "Owen", "Wyatt", "John", "Jack"
    };

    private static final Random RANDOM = new Random();

    /**
     * Returns a random name from the predefined list.
     *
     * @return a randomly selected name
     */
    public static String getRandomName() {
        int randomIndex = RANDOM.nextInt(NAMES.length);
        return NAMES[randomIndex];
    }

}
