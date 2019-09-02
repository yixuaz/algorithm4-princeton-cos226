package part1.week1.analysis;

public enum EggDropStrategy {
    Version0 {
        @Override
        Result findT(EggDropBuilding building) {
            int i = 1, n = building.getHeight();
            Result result = new Result();
            for (;i <= n; i++) {
                if (toss(i,result,building)) break;
            }
            result.T = i;
            return result;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(1,building.getT());
        }
    },
    Version1 {
        @Override
        Result findT(EggDropBuilding building) {
            int s = 1, e = building.getHeight();
            Result result = new Result();
            while (s <= e) {
                int mid = s + (e - s) / 2;
                if (toss(mid,result,building)) {
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
            return new Result(log2(building.getHeight()),log2(building.getHeight()));
        }

    },
    Version2 {
        @Override
        Result findT(EggDropBuilding building) {
            Result result = new Result();
            int s = 1, pow = 1;
            for (; !toss(s,result,building); pow <<= 1, s += pow) ;
            int e = s - 1;
            s = s - pow + 1;
            while (s <= e) {
                int mid = s + (e - s) / 2;
                if (toss(mid,result,building)) {
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
            return new Result(log2(building.getT()),2 * log2(building.getT()));
        }
    },
    Version3 {
        @Override
        Result findT(EggDropBuilding building) {
            Result result = new Result();
            int n = building.getHeight(), sqrtN = (int)Math.sqrt(n) + 1;
            int i = sqrtN + 1;
            for (; i < n; i += sqrtN) {
                if (toss(i,result,building)) break;
            }
            int j = i - sqrtN;
            for (; j <= i; j++) {
                if (toss(j,result,building)) break;
            }
            result.T = j;
            return result;
        }

        @Override
        Result getRestrict(EggDropBuilding building) {
            return new Result(2,2 * sqrt(building.getHeight()));
        }

    },
    Version4 {
        @Override
        Result findT(EggDropBuilding building) {
            Result result = new Result();
            int sqrtT = 2;
            for (; !toss(sqrtT * sqrtT,result,building); sqrtT++) ;
            int i = (sqrtT - 1) * (sqrtT - 1);
            for (; i < sqrtT * sqrtT; i++) {
                if (toss(i,result,building)) break;
            }
            result.T = i;
            return result;
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
