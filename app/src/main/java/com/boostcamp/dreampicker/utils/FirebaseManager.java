package com.boostcamp.dreampicker.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class FirebaseManager {

    /**
     * ID 만 필요한 곳에서 사용됨 */
    @Nullable
    public static String getCurrentUserId(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user == null ? null : user.getUid();
    }

    /**
     * 내 프로필 화면에서 사용됨 */
    @Nullable
    public static FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
