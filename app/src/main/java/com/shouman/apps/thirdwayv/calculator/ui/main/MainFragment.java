package com.shouman.apps.thirdwayv.calculator.ui.main;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shouman.apps.thirdwayv.calculator.R;
import com.shouman.apps.thirdwayv.calculator.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {

    private MainFragmentBinding mBinding;

    @NonNull
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MainFragmentBinding.inflate(inflater);


        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

}