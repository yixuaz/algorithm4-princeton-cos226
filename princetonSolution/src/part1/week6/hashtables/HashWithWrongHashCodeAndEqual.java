package part1.week6.hashtables;

public class HashWithWrongHashCodeAndEqual {
    public static class OlympicAthlete1 {
        private String name;

        public OlympicAthlete1(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    public static class OlympicAthlete2 {
        private String name;

        public OlympicAthlete2(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object that) {
            if (that == null) return false;
            if (! (that instanceof OlympicAthlete2)) return false;
            return name.equals(((OlympicAthlete2) that).name);
        }
    }

    public static class OlympicAthlete3 {
        private String name;

        public OlympicAthlete3(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        public boolean equals(OlympicAthlete3 that) {
            if (that == null) return false;
            return name.equals(that.name);
        }
    }
}
