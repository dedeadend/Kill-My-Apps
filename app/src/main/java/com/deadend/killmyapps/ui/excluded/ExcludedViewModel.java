package com.deadend.killmyapps.ui.excluded;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExcludedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ExcludedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}