package com.droidtech.waphotos.ui.whatsapp_business;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WhatsappBusinessViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WhatsappBusinessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is whatsapp business fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}