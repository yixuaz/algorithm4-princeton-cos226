package part1.week1.analysis;

public enum EggDropStrategy {
    Version0 {
        @Override
        Result findT(EggDropBuilding building) {
            //TODO: ADD YOUR CODE HERE
            return null;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(1,building.getT());
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
            return new Result(log2(building.getHeight()),log2(building.getHeight()));
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
            return new Result(log2(building.getT()),2 * log2(building.getT()));
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
            return new Result(2,2 * sqrt(building.getHeight()));
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
            return new Result(2,3 * sqrt(building.getT()));
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
