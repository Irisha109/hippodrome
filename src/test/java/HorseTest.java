import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HorseTest {
    private Horse horse = new Horse("Breeze", 5d, 5d);

    @Test
    public void checkExceptionWithNameNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1d, 1d));

        assertEquals("Name cannot be null.", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\f", "\r",
            "\t", "\u2028", "\u2029"})
    public void checkExceptionWithIncorrectName(String name) {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1d, 1d));

        assertEquals("Name cannot be blank.", ex.getMessage());
    }


    @Test
    public void checkExceptionWithNegativeSpeed() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Lucky", -1d, 1d));

        assertEquals("Speed cannot be negative.", ex.getMessage());
    }

    @Test
    public void checkExceptionWithNegativeDistance() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Lucky", 1d, -1d));

        assertEquals("Distance cannot be negative.", ex.getMessage());
    }




    @Test
    void getName() {
        assertEquals("Breeze", horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(5d, horse.getSpeed());
    }

    @Test
    void getDistance() {

        assertAll("Checking when distance parameter exist and default",
                () -> assertEquals(5d, horse.getDistance()),
                () -> { Horse horseWithoutDistance = new Horse("Breeze", 1d);
                    assertEquals(0, horseWithoutDistance.getDistance());
                });
    }

    @Test
    void move() {
        try(MockedStatic<Horse> horseStatic = Mockito.mockStatic(Horse.class)){
            horse.move();

            horseStatic.verify(()-> Horse.getRandomDouble(0.2, 0.9));

        }

    }
    @ParameterizedTest
    @ValueSource(doubles = {0.7, 4.2, 15.6 })
    void checkGetRandomDouble (double randomDouble){
        try(MockedStatic<Horse> horseStatic = Mockito.mockStatic(Horse.class)){
          horseStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(randomDouble);

          Double distance = horse.getDistance() + horse.getSpeed() * randomDouble;
          horse.move();

          assertEquals(distance, horse.getDistance());

        }

    }


}