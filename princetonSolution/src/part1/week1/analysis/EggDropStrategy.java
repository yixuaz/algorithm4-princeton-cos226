package part1.week1.analysis;

public enum EggDropStrategy {
    /**
     * the idea behind the egg drop is that when we only have 1 egg, we have no choice,
     * to find the target floor, we need brute force search from the bottom to top.
     * that is solution to version 0.
     */
    Version0 {
        @Override
        Result findT(EggDropBuilding building) {
            int i = 1, n = building.getHeight();
            Result result = new Result();
            for (; i <= n; i++) {
                if (toss(i, result, building)) break;
            }
            result.T = i;
            return result;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(1, building.getT());
        }
    },
    /**
     * we have lg n eggs and lg n tosses.which function u can toss lgn,
     * yes binary search. we can toss and n/2, if it break, then n/4 .
     * if not break, go 3n/4. and so on.
     */
    Version1 {
        @Override
        Result findT(EggDropBuilding building) {
            int s = 1, e = building.getHeight();
            Result result = new Result();
            while (s <= e) {
                int mid = s + (e - s) / 2;
                if (toss(mid, result, building)) {
                    e = mid - 1;
                } else {
                    s = mid + 1;
                }

            }
            result.T = e + 1;
            return result;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(log2(building.getHeight()), log2(building.getHeight()));
        }

    },
    /**
     * eggs is lg T, and we have 2 lgT tosses. because at the beginning we do not know what exactly T is.
     * so use first lg T tosses from 1,2,4,... to find the nearest 2^K = T. k = lgT.
     * then use binary search with in k to find the T.
     */
    Version2 {
        @Override
        Result findT(EggDropBuilding building) {
            Result result = new Result();
            int s = 1, pow = 1;
            for (; !toss(s, result, building); pow <<= 1, s += pow) ;
            int e = s - 1;
            s = s - pow + 1;
            while (s <= e) {
                int mid = s + (e - s) / 2;
                if (toss(mid, result, building)) {
                    e = mid - 1;
                } else {
                    s = mid + 1;
                }
            }
            result.T = e + 1;
            return result;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(log2(building.getT()), 2 * log2(building.getT()));
        }
    },
    /**
     *  we only have two egg. and the tosses the 2 sqrt n. so the same thought we use 1 egg and sqrt N tosses to
     *  minimize range to sqrt n, so use another egg to brute force.
     * the question is that how to minimize range with 1 egg and sqrt N tosses.
     * the answer is also use sqrt. we can toss on sqrt n, 2 * sqrt n, .... sqrt n * sqrt n
     * then search in k sqrt n to k+1 sqrt n.
     */
    Version3 {
        @Override
        Result findT(EggDropBuilding building) {
            Result result = new Result();
            int n = building.getHeight(), sqrtN = (int) Math.sqrt(n) + 1;
            int i = sqrtN + 1;
            for (; i < n; i += sqrtN) {
                if (toss(i, result, building)) break;
            }
            int j = i - sqrtN;
            for (; j <= i; j++) {
                if (toss(j, result, building)) break;
            }
            result.T = j;
            return result;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(2, 2 * sqrt(building.getHeight()));
        }

    },
    /**
     * idea is similar to above. the difference is that now we don't know the T is at the
     * beginning. so we need to use 1 egg to find the nearest number to sqrt T.
     * so the tosses way is 1,4,9, 16...., find the number so use the brute force search.
     */
    Version4 {
        @Override
        Result findT(EggDropBuilding building) {
            Result result = new Result();
            int sqrtT = 2;
            for (; !toss(sqrtT * sqrtT, result, building); sqrtT++) ;
            int i = (sqrtT - 1) * (sqrtT - 1);
            for (; i < sqrtT * sqrtT; i++) {
                if (toss(i, result, building)) break;
            }
            result.T = i;
            return result;
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
