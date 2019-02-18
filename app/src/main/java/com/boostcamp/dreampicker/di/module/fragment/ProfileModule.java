package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.profile.ProfileViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ProfileModule {
    @FragmentScoped
    @Binds
    abstract ViewModelProvider.Factory feedViewModelFactory(ProfileViewModelFactory factory);
}