package com.example.tt.ui.outdated;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OutdatedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OutdatedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is outdated fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}