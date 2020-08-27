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
    public static class ComponentParamTests {

        private MainViewModel mainViewModel;
        private char inputNumber;
        private String expectedResult;

        public ComponentParamTests(char inputNumber, String expectedResult) {
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



    }

}