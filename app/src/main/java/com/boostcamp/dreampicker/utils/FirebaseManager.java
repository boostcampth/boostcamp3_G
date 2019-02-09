package com.boostcamp.dreampicker.utils;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.LegacyUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class FirebaseManager {

    @Nullable
    public static String getCurrentUserId() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user == null ? null : user.getUid();
    }

    @Nullable
    public static LegacyUser getCurrentUser() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null) {
            return null;
        } else {
            final LegacyUser user = new LegacyUser();
            user.setId(firebaseUser.getUid());
            user.setName(firebaseUser.getDisplayName());
            final Uri uri = firebaseUser.getPhotoUrl();
            if(uri != null) {
                user.setProfileImageUrl(firebaseUser.getPhotoUrl().toString());
            }
            return user;
        }
    }
}
