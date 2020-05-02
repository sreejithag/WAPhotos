package com.droidtech.waphotos.ui.whatsapp_business;

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

public class WhatsappBusinessFragment extends Fragment {

    private WhatsappBusinessViewModel whatsappBusinessViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        whatsappBusinessViewModel =
                ViewModelProviders.of(this).get(WhatsappBusinessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_whatsapp_business, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        whatsappBusinessViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}