import Exceptions.EndGameException;
import Exceptions.InvalidRollValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class GameTest {

    private Game game;


    @BeforeEach
    void startupTests() {
        game = new Game();
    }

    @ParameterizedTest
    @MethodSource("provideFullGameRolls")
    void aGameHave10Frame(List<Integer> Rolls, boolean isValid) {
        if (isValid) {
            assertDoesNotThrow(() -> Rolls.forEach(game::roll));
        } else {
            assertThrows(EndGameException.class, () -> Rolls.forEach(game::roll));
        }
    }

    private static Stream<Arguments> provideFullGameRolls() {
        return Stream.of(
                Arguments.of(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), false),// all Roll no strike invalid
                Arguments.of(List.of(10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 0, 1), false), // all strike but last no strike/spare
                Arguments.of(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), true), //all Roll no strike valid
                Arguments.of(List.of(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10), true), // all strike valid
                Arguments.of(List.of(10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 10, 1), true) // all strike and last Roll spare
        );
    }

    @ParameterizedTest
    @MethodSource("provideFrameRolls")
    void EveryFrameHave10Pins(int firstRoll, int secondRoll, boolean isValid) {
        if (isValid) {
            assertDoesNotThrow(() -> {
                game.roll(firstRoll);
                game.roll(secondRoll);
            });
        } else {
            assertThrows(InvalidRollValueException.class, () -> {
                game.roll(firstRoll);
                game.roll(secondRoll);
            });
        }
    }

    private static Stream<Arguments> provideFrameRolls() {
        return Stream.of(
                Arguments.of(-1, 0, false), // no Roll can have first Roll negative
                Arguments.of(0, -1, false), // no Roll can have second Roll negative
                Arguments.of(0, 0, true), // zero is a valid Roll
                Arguments.of(0, 11, false), // the sum of the two Roll cannot be major of 10
                Arguments.of(11, 0, false), // the sum of the two Roll cannot be major of 10
                Arguments.of(5, 6, false), // the sum of the two Roll cannot be major of 10
                Arguments.of(1, 0, true), // only one hit is valid
                Arguments.of(0, 1, true), // only one hit is valid
                Arguments.of(10, 1, true), // strike is valid (following Roll not relevant for the frame)
                Arguments.of(2, 8, true) // spare is valid
        );
    }

    @ParameterizedTest
    @MethodSource("provideSpareRolls")
    void aSpareIsRegisterIfAll10PinAreDownOnSecondShot(int firstRoll, int secondRoll, boolean isSpare) {
        game.roll(firstRoll);
        game.roll(secondRoll);
        if (isSpare) {
            assertTrue(game.isSpareRoll());
        } else {
            assertFalse(game.isSpareRoll());
        }
    }

    private static Stream<Arguments> provideSpareRolls() {
        return Stream.of(
                Arguments.of(0, 0, false),
                Arguments.of(0, 9, false),
                Arguments.of(9, 0, false),
                Arguments.of(0, 10, true),
                Arguments.of(1, 9, true),
                Arguments.of(9, 1, true),
                Arguments.of(10, 0, false) // è uno strike
        );
    }

    @Test
    void aStrikeIsRegisterIfAll10PinAreDownOnFirstShot() {
        game.roll(10);
        assertTrue(game.isStrikeRoll());
    }

    @Test
    void afterASpareBonusIsTheNextRoll() {
        game.roll(1);
        game.roll(9);
        assertTrue(game.isSpareRoll());
        assertEquals(10, game.score());
        game.roll(4);
        assertEquals(18, game.score());
    }

    @Test
    void afterAStrikeBonusAreTheNextTwoRoll() {
        game.roll(10);
        assertTrue(game.isStrikeRoll());
        game.roll(3);
        assertEquals(16, game.score());
        game.roll(7);
        assertEquals(30, game.score());
    }

    @Test
    void onTenthFrameIfStrikeHaveMax30point() {
        lastFrame();
        game.roll(10);
        game.roll(10);
        game.roll(10);
        assertEquals(30,game.score());
    }

    @Test
    void onTenthFrameIfSparHaveMax30point() {
        lastFrame();
        game.roll(0);
        game.roll(10);
        game.roll(10);
        assertEquals(20,game.score());
    }

    @Test
    void onTenthFrameNoSpareOrStrike() {
        lastFrame();
        game.roll(2);
        game.roll(3);
        assertEquals(5,game.score());
    }

    @Test
    void onTenthFrameIfNotStrikeOrSpareNoBonusRollIsAllowed() {
        lastFrame();
        game.roll(2);
        game.roll(2);
        assertThrows(EndGameException.class, () ->  game.roll(0));

    }

    private void lastFrame() {
        for (int i = 0; i < 18; i++) {
            game.roll(0);
        }
    }
}
