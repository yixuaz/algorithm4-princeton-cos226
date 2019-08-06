package week6.hashtables;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import week6.hashtables.HashWithWrongHashCodeAndEqual.*;

import static org.junit.Assert.*;


public class HashWithWrongHashCodeAndEqualTest {
    @Test
    public void testOnlyHashCode() {
        Set<OlympicAthlete1> set = new HashSet<>();
        OlympicAthlete1 athlete1 = new OlympicAthlete1("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());
        OlympicAthlete1 athlete2 = new OlympicAthlete1("peter");
        set.add(athlete2);
        System.out.println(set.size());
        OlympicAthlete1 athlete3 = new OlympicAthlete1("peter3");
        set.add(athlete3);
        System.out.println(set.size());
    }

    @Test
    public void testOnlyEquals() {
        Set<OlympicAthlete2> set = new HashSet<>();
        OlympicAthlete2 athlete1 = new OlympicAthlete2("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());
        OlympicAthlete2 athlete2 = new OlympicAthlete2("peter");
        set.add(athlete2);
        System.out.println(athlete1.equals(athlete2));
        System.out.println(set.size());
        OlympicAthlete2 athlete3 = new OlympicAthlete2("peter3");
        set.add(athlete3);
        System.out.println(set.size());
    }

    @Test
    public void testEqualsNotUseObject() {
        Set<OlympicAthlete3> set = new HashSet<>();
        OlympicAthlete3 athlete1 = new OlympicAthlete3("peter");
        set.add(athlete1);
        set.add(athlete1);
        System.out.println(set.size());
        OlympicAthlete3 athlete2 = new OlympicAthlete3("peter");
        set.add(athlete2);
        System.out.println(athlete1.equals(athlete2));
        System.out.println(set.size());
        OlympicAthlete3 athlete3 = new OlympicAthlete3("peter3");
        set.add(athlete3);
        System.out.println(set.size());
    }

}