package com.boostcamp.dreampicker.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class FirebaseManager {

    @Nullable
    public static String getCurrentUserId() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user == null ? null : user.getUid();
    }
}
