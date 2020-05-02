package com.droidtech.waphotos.ui.whatsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.droidtech.waphotos.R;

public class WhatsappFragment extends Fragment {

    private WhatsappViewModel whatsappViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        whatsappViewModel =
                ViewModelProviders.of(this).get(WhatsappViewModel.class);
        View root = inflater.inflate(R.layout.fragment_whatsapp, container, false);


        return root;
    }
}