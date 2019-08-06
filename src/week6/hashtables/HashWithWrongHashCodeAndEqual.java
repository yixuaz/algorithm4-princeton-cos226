package week6.hashtables;

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
