package com.shouman.apps.thirdwayv.calculator.ui.main;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class MainViewModelTest {

    @RunWith(Parameterized.class)
    public static class NumbersParamTests {

        private MainViewModel mainViewModel;
        private char inputNumber;
        private String expectedResult;

        public NumbersParamTests(char inputNumber, String expectedResult) {
            this.inputNumber = inputNumber;
            this.expectedResult = expectedResult;
        }

        @Rule
        public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

        @Before
        public void initTest() {
            mainViewModel = new MainViewModel();
        }

        @Parameterized.Parameters
        public static List<Object[]> primeNumbers() {
            return Arrays.asList(new Object[][]{
                    {'1', "1.0"}, {'2', "2.0"},
                    {'3', "3.0"}, {'4', "4.0"},
                    {'5', "5.0"}, {'6', "6.0"},
                    {'7', "7.0"}, {'8', "8.0"},
                    {'9', "9.0"}
            });
        }

        @Test
        public void testCaseUsingParams() throws InterruptedException {
            mainViewModel.addNumberToInput(inputNumber);

            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expectedResult);
        }
    }

    @RunWith(Parameterized.class)
    public static class OperatorParamTests {

        private MainViewModel mainViewModel;
        private char inputOperator;
        private String expectedResult;

        public OperatorParamTests(char inputNumber, String expectedResult) {
            this.inputOperator = inputNumber;
            this.expectedResult = expectedResult;
        }

        @Rule
        public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

        @Before
        public void initTest() {
            mainViewModel = new MainViewModel();
        }

        @Parameterized.Parameters
        public static List<Object[]> primeNumbers() {
            return Arrays.asList(new Object[][]{
                    {'+', "6.0"}, {'-', "4.0"},
                    {'x', "5.0"}, {'÷', "5.0"}
            });
        }

        @Test
        public void testCaseUsingParams() throws InterruptedException {
            mainViewModel.addNumberToInput('5');
            mainViewModel.addNumberToInput(inputOperator);
            mainViewModel.addNumberToInput('1');
            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expectedResult);
        }
    }


    public static class ComponentSingleTests {

        private MainViewModel mainViewModel;
        @Rule
        public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

        @Before
        public void initTest() {
            mainViewModel = new MainViewModel();
        }


        @Test
        public void test_addZeroToScreenNoNumbers() throws InterruptedException {
            String expected = "0.0";

            mainViewModel.addZeroToInput();

            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expected);
        }

        @Test
        public void test_addZeroToScreenWithNumbers() throws InterruptedException {
            String expected = "12000.0";
            mainViewModel.addNumberToInput('1');
            mainViewModel.addNumberToInput('2');
            mainViewModel.addZeroToInput();
            mainViewModel.addZeroToInput();
            mainViewModel.addZeroToInput();

            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expected);
        }

        @Test
        public void test_addOperatorToInputWithNoNumbers() throws InterruptedException {
            String expected = "0.0";
            mainViewModel.addOperator('+');

            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expected);
        }

        @Test
        public void test_addOperatorToScreenWithPreviousOperator() throws InterruptedException {
            String expected = "1-5x";
            mainViewModel.addNumberToInput('1');
            mainViewModel.addOperator('-');
            mainViewModel.addNumberToInput('5');
            mainViewModel.addOperator('+');
            mainViewModel.addOperator('-');
            mainViewModel.addOperator('x');

            StringBuilder actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getScreenCurrentStringLiveData());

            assertNotNull(actual);
            assertEquals(actual.toString(), expected);
        }

        @Test
        public void test_addDotToScreenWhileEmpty() throws InterruptedException {

            String expected = "0.";

            mainViewModel.addDot();

            StringBuilder actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getScreenCurrentStringLiveData());

            assertNotNull(actual);
            assertEquals(actual.toString(), expected);
        }

        @Test
        public void test_addDotToScreenWithNumberWithoutDot() throws InterruptedException {

            String expected = "1.22";

            mainViewModel.addNumberToInput('1');
            mainViewModel.addDot();
            mainViewModel.addNumberToInput('2');
            mainViewModel.addNumberToInput('2');

            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expected);
        }

        @Test
        public void test_addDotToScreenWithNumberWithDot() throws InterruptedException {

            String expected = "1.22";

            mainViewModel.addNumberToInput('1');
            mainViewModel.addDot();
            mainViewModel.addNumberToInput('2');
            mainViewModel.addNumberToInput('2');
            mainViewModel.addDot();

            String actual = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getResultLiveData());

            assertNotNull(actual);
            assertEquals(actual, expected);
        }

    }

}