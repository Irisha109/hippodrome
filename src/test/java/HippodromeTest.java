import org.junit.jupiter.api.Test;

import org.apache.commons.collections4.CollectionUtils;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    public void checkExceptionWithListNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", ex.getMessage());
    }

    @Test

    public void checkExceptionWithEmptyList() {
        List<Horse> horseList = new ArrayList<>();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(horseList));
        assertEquals("Horses cannot be empty.", ex.getMessage());
    }


    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse #" + i, i+1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
        //assertTrue(CollectionUtils.isEqualCollection(horses, hippodrome.getHorses()));

    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (int i = 0; i < horses.size(); i++){
            Mockito.verify(horses.get(i)).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            horses.add(new Horse("Horse #" + i, i+1, i+3));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse horseWinner = horses.stream().max(Comparator.comparingDouble(Horse::getDistance)).get();

        assertSame(horseWinner, hippodrome.getWinner());
       // assertEquals(horseWinner, hippodrome.getWinner());
    }
}