package com.shouman.apps.thirdwayv.calculator.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

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

    }

    public void clearOneByOne() {

    }

    public void clearAll() {

    }

    public void equal() {

    }

    public void addDot() {

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


    private static Double calculate(@Nullable String s) {
        if (s != null && s.length() > 0) return Double.parseDouble(s);
        return 0.0;
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

}