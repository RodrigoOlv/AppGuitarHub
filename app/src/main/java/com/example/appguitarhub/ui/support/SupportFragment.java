package com.example.appguitarhub.ui.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appguitarhub.R;
import com.google.firebase.auth.FirebaseAuth;

public class SupportFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_support, container, false);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:rodrigo18498@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Fale conosco");
        startActivity(intent);

        return root;
    }
}