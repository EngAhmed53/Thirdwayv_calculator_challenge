package com.shouman.apps.thirdwayv.calculator.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Stack;

public class MainViewModel extends ViewModel {

    @NonNull
    private final MutableLiveData<StringBuilder> screenCurrentStringLiveData;


    public MainViewModel() {
        screenCurrentStringLiveData = new MutableLiveData<>();
    }

    public void addNumberToInput(char num) {
        StringBuilder previousStringOnScreen = getScreenCurrentString();
        StringBuilder newScreenString = addNumToScreen(previousStringOnScreen, num);
        handelInputChar(newScreenString);
    }

    public void addZeroToInput() {
        StringBuilder previousStringOnScreen = getScreenCurrentString();
        StringBuilder newScreenString = addZeroToScreen(previousStringOnScreen);
        handelInputChar(newScreenString);
    }

    public void addOperator(char operator) {
        StringBuilder previousStringOnScreen = getScreenCurrentString();
        StringBuilder newScreenString = addOperatorToScreen(previousStringOnScreen, operator);
        handelInputChar(newScreenString);
    }

    public void clearOneByOne() {

    }

    public void clearAll() {

    }

    public void equal() {

    }

    public void addDot() {
        StringBuilder previousStringOnScreen = getScreenCurrentString();
        StringBuilder newScreenString = addDotToScreen(previousStringOnScreen);
        handelInputChar(newScreenString);
    }

    public void undo() {

    }

    public void redo() {

    }

    private StringBuilder getScreenCurrentString() {
        return screenCurrentStringLiveData
                .getValue() == null ? new StringBuilder() : screenCurrentStringLiveData.getValue();
    }

    @NonNull
    private StringBuilder addNumToScreen(@NonNull StringBuilder previousStringOnScreen, char newChar) {

        String[] previousStringArray =
                previousStringOnScreen
                        .toString()
                        .split("(?<=[-+x÷])|(?=[-+x÷])");

        if (previousStringArray[previousStringArray.length - 1].equals("0")) {
            previousStringOnScreen.replace(
                    previousStringOnScreen.length() - 1,
                    previousStringOnScreen.length(),
                    String.valueOf(newChar));
        } else {
            previousStringOnScreen.append(newChar);
        }

        return previousStringOnScreen;
    }


    @NonNull
    private StringBuilder addZeroToScreen(@NonNull StringBuilder previousStringOnScreen) {

        String[] previousStringArray =
                previousStringOnScreen
                        .toString()
                        .split("(?<=[-+x÷])|(?=[-+x÷])");


        if (!previousStringArray[previousStringArray.length - 1].equals("0")) {
            previousStringOnScreen.append('0');
        }

        return previousStringOnScreen;

    }

    @NonNull
    private StringBuilder addOperatorToScreen(@NonNull StringBuilder previousStringOnScreen, char newChar) {

        String[] previousStringArray =
                previousStringOnScreen
                        .toString()
                        .split("(?<=[-+x÷])|(?=[-+x÷])");


        String lastIndexValue = previousStringArray[previousStringArray.length - 1];
        if (lastIndexValue.equals("")) return new StringBuilder();
        if (isOperator(lastIndexValue)) {
            previousStringOnScreen.replace(
                    previousStringOnScreen.length() - 1,
                    previousStringOnScreen.length(),
                    String.valueOf(newChar));
        } else {
            previousStringOnScreen.append(newChar);
        }
        return previousStringOnScreen;
    }

    @NonNull
    private StringBuilder addDotToScreen(@NonNull StringBuilder previousStringOnScreen) {

        String[] previousStringArray =
                previousStringOnScreen
                        .toString()
                        .split("(?<=[-+x÷])|(?=[-+x÷])");


        String lastIndexValue = previousStringArray[previousStringArray.length - 1];

        if (isOperator(lastIndexValue) || lastIndexValue.equals("")) {
            return previousStringOnScreen.append("0.");
        } else if (!lastIndexValue.contains(".")) {
            return previousStringOnScreen.append(".");
        } else {
            return previousStringOnScreen;
        }
    }


    private Double calculate(@Nullable String s) {
        int len;
        if (s == null || (len = s.length()) == 0) {
            return 0.0;
        }
        Stack<Double> stack = new Stack<>();
        StringBuilder num = new StringBuilder();
        char sign = '+';
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') {
                num.append(s.charAt(i));
            }

            if ((!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.') || i == len - 1) {

                try {

                    if (sign == '-') {
                        stack.push(Double.parseDouble(num.toString()) * -1);
                    }
                    if (sign == '+') {
                        stack.push(Double.parseDouble(num.toString()));
                    }
                    if (sign == 'x') {
                        stack.push(stack.pop() * Double.parseDouble(num.toString()));
                    }
                    if (sign == '÷') {
                        stack.push(stack.pop() / Double.parseDouble(num.toString()));
                    }
                    sign = s.charAt(i);
                    num.setLength(0);

                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        }

        double ans = 0;
        for (double i : stack) {
            ans += i;
        }
        return ans;
    }


    private void handelInputChar(StringBuilder newScreenString) {
        setScreenCurrentString(newScreenString);
    }

    private void setScreenCurrentString(@NonNull StringBuilder screenCurrentString) {
        this.screenCurrentStringLiveData.setValue(screenCurrentString);
    }


    @NonNull
    public LiveData<StringBuilder> getScreenCurrentStringLiveData() {
        return screenCurrentStringLiveData;
    }

    @NonNull
    public LiveData<String> getResultLiveData() {
        return Transformations.map(screenCurrentStringLiveData, (currentStringOnScreen ->
                String.valueOf(calculate(currentStringOnScreen.toString()))
        ));
    }

    private boolean isOperator(@NonNull String lastChar) {
        return lastChar.equals("÷") || lastChar.equals("x")
                || lastChar.equals("+") || lastChar.equals("-");
    }
}