package part1.week6.hashtables;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import part1.week6.hashtables.HashWithWrongHashCodeAndEqual.*;


public class HashWithWrongHashCodeAndEqualTest {
    @Test
    public void testOnlyHashCode() {
        Set<OlympicAthlete1> set = new HashSet<>();
        OlympicAthlete1 athlete1 = new OlympicAthlete1("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());
        Assert.assertEquals(1, set.size());
        OlympicAthlete1 athlete2 = new OlympicAthlete1("peter");
        Assert.assertEquals(athlete2.hashCode(), athlete1.hashCode());
        set.add(athlete2);
        System.out.println(set.size());
        Assert.assertEquals(2, set.size());
        OlympicAthlete1 athlete3 = new OlympicAthlete1("peter3");
        Assert.assertNotEquals(athlete2.hashCode(), athlete3.hashCode());
        set.add(athlete3);
        System.out.println(set.size());
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void testOnlyEquals() {
        Set<OlympicAthlete2> set = new HashSet<>();
        OlympicAthlete2 athlete1 = new OlympicAthlete2("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());
        Assert.assertEquals(1, set.size());
        OlympicAthlete2 athlete2 = new OlympicAthlete2("peter");
        set.add(athlete2);
        System.out.println(athlete1.equals(athlete2));
        Assert.assertEquals(athlete1, athlete2);
        System.out.println(set.size());
        Assert.assertEquals(2, set.size());
        OlympicAthlete2 athlete3 = new OlympicAthlete2("peter3");
        set.add(athlete3);
        System.out.println(set.size());
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void testEqualsNotUseObject() {
        Set<OlympicAthlete3> set = new HashSet<>();
        OlympicAthlete3 athlete1 = new OlympicAthlete3("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());
        Assert.assertEquals(1, set.size());
        OlympicAthlete3 athlete2 = new OlympicAthlete3("peter");
        set.add(athlete2);
        System.out.println(athlete1.equals(athlete2));
        Assert.assertTrue(athlete1.equals(athlete2));
        Assert.assertNotEquals(athlete1, athlete2);
        System.out.println(set.size());
        Assert.assertEquals(2, set.size());
        OlympicAthlete3 athlete3 = new OlympicAthlete3("peter3");
        set.add(athlete3);
        System.out.println(set.size());
        Assert.assertEquals(3, set.size());
    }

}