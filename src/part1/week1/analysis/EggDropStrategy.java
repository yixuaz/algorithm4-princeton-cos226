package part1.week1.analysis;

/**
 * Egg drop. Suppose that you have an nn-story building (with floors 1 through n) and plenty of eggs.
 * An egg breaks if it is dropped from floor T or higher and does not break otherwise.
 * Your goal is to devise a strategy to determine the value of T given the following
 * limitations on the number of eggs and tosses:
 *
 * Version 0: 1 egg, ≤T tosses.
 * Version 1: ∼1lgn eggs and ∼1lgn tosses.
 * Version 2: ∼lgT eggs and ∼2lgT tosses.
 * Version 3: 2 eggs and ∼2√n tosses.
 * Version 4: 2 eggs and ≤ c√T tosses for some fixed constant cc.
 */
public enum EggDropStrategy {
    Version0 {
        @Override
        Result findT(EggDropBuilding building) {
            //TODO: ADD YOUR CODE HERE
            return null;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(1, building.getT());
        }
    },
    Version1 {
        @Override
        Result findT(EggDropBuilding building) {
            //TODO: ADD YOUR CODE HERE
            return null;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(log2(building.getHeight()), log2(building.getHeight()));
        }

    },
    Version2 {
        @Override
        Result findT(EggDropBuilding building) {
            //TODO: ADD YOUR CODE HERE
            return null;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(log2(building.getT()), 2 * log2(building.getT()));
        }
    },
    Version3 {
        @Override
        Result findT(EggDropBuilding building) {
            //TODO: ADD YOUR CODE HERE
            return null;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(2, 2 * sqrt(building.getHeight()));
        }

    },
    Version4 {
        @Override
        Result findT(EggDropBuilding building) {
            //TODO: ADD YOUR CODE HERE
            return null;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(2, 3 * sqrt(building.getT()));
        }
    };

    private static int log2(int k) {
        return (int) (Math.log(k) / Math.log(2)) + 1;
    }

    private static int sqrt(int k) {
        return (int) Math.ceil(Math.sqrt(k));
    }

    private static boolean toss(int h, Result r, EggDropBuilding building) {
        r.tossCost++;
        boolean res = building.isBreak(h);
        if (res) r.eggCost++;
        return res;
    }

    public static class Result {
        int eggCost;
        int tossCost;
        int T;

        public Result() {
            eggCost = 0;
            tossCost = 0;
            T = 0;
        }

        public Result(int eggs, int toss) {
            eggCost = eggs;
            tossCost = toss;
        }
    }


    abstract Result findT(EggDropBuilding building);

    abstract Result getRestrict(EggDropBuilding building);
}
