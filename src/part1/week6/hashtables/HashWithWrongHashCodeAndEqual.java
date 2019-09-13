package part1.week6.hashtables;

/**
 * Hashing with wrong hashCode() or equals(). Suppose that you implement a data type OlympicAthlete for
 * use in a java.util.HashMap.
 *
 * Describe what happens if you override hashCode() but not equals().
 * Describe what happens if you override equals() but not hashCode().
 * Describe what happens if you override hashCode() but implement
 * public boolean equals(OlympicAthlete that)) instead of public boolean equals(Object that)
 * */
public class HashWithWrongHashCodeAndEqual {
    public static class OlympicAthlete1 {
        private String name;

        public OlympicAthlete1(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            // TODO : ADD YOUR CODE HERE
            return -1;
        }
    }

    public static class OlympicAthlete2 {
        private String name;

        public OlympicAthlete2(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object that) {
            // TODO : ADD YOUR CODE HERE
            return false;
        }
    }

    public static class OlympicAthlete3 {
        private String name;

        public OlympicAthlete3(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            // TODO : ADD YOUR CODE HERE
            return -1;
        }

        public boolean equals(OlympicAthlete3 that) {
            // TODO : ADD YOUR CODE HERE
            return false;
        }
    }
}
