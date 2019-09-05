package part1.week1.analysis;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class EggDropStrategyTest {

    @Test
    public void basicTest() {
        for (int i = 10; i <= 100; i++) {
            EggDropBuilding building = new EggDropBuilding(i, 10);
            check(building);
        }
    }

    @Test
    public void randomTest() {
        Random r = new Random();
        int N = 100000;
        for (int i = 0; i < 10000; i++) {
            int h = 1 + r.nextInt(N), t = 1 + r.nextInt(Math.max(1, h - 1));
            System.out.println("building " + h + "," + t);
            EggDropBuilding building = new EggDropBuilding(h, t);
            check(building);
        }
    }

    private void check(EggDropBuilding building) {
        for (EggDropStrategy strategy : EggDropStrategy.values()) {
            EggDropStrategy.Result result = strategy.findT(building);
            EggDropStrategy.Result restriciton = strategy.getRestrict(building);
            Assert.assertTrue(String.format("%s egg cost %d is over limit %d", strategy.name(), result.eggCost, restriciton.eggCost),
                    result.eggCost <= restriciton.eggCost);
            Assert.assertTrue(String.format("%s toss cost %d is over limit %d", strategy.name(), result.tossCost, restriciton.tossCost),
                    result.tossCost <= restriciton.tossCost);
            Assert.assertTrue(String.format("%s T is not correct", strategy.name()), building.guess(result.T));
        }
    }

}