package efs.task.unittests;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;//assertTrue;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietNotRecommended() {
        // Given
        double weight = 69.5;
        double height = 1.72;

        // When
        boolean mark = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(mark);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        // Given
        double weight = 69.5;
        double height = 0.0;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // When
            FitCalculator.isBMICorrect(weight, height);
        });
    }

    @ParameterizedTest(name = "Weight={0}")
    @ValueSource(doubles = {85.0,90.0,95.0})
    void shouldReturnTrue_whenBMICorrectForMultiInputValues(double weight) {
        // Given
        double height = 1.75;

        // When
        boolean mark = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertTrue(mark);
    }

    @ParameterizedTest(name = "Weight={0}")
    @CsvSource({
            "50.0, 1.5",
            "60.0, 1.6",
            "70.0, 1.7"
    })
    void shouldReturnFalse_whenIsBMICorrectForCsvSource(double weight, double height) {
        // When
        boolean mark = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(mark);
    }

    @ParameterizedTest(name = "Weight={0}, Height={1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenIsBMICorrectForCsvFileResource(double weight, double height) {
        // When
        boolean mark = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(mark);
    }

    @Test
    void shouldReturnLowestBMIUser_whenListOfUsersProvided() {
        // Given

        List<User> userList = TestConstants.TEST_USERS_LIST;

        // When
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // Then
        assertEquals(97.3, userWithWorstBMI.getWeight());
        assertEquals(1.79, userWithWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull_whenListOfUsersEmpty() {
        // Given
        List<User> userList = Collections.emptyList();

        // When
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // Then
        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnExpectedBMIScore_whenCalculatingForListOfUsers() {
        // Given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        // When
        double[] bmiScore = FitCalculator.calculateBMIScore(userList);

        // Then
        assertArrayEquals(TestConstants.TEST_USERS_BMI_SCORE, bmiScore);
    }

}