package com.boostcamp.dreampicker.data.common;

import com.boostcamp.dreampicker.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

@SuppressWarnings("SpellCheckingInspection")
public class FirebaseManager {
    @Nullable
    public static String getCurrentUserId() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user == null ? null : user.getUid();
    }

    @Nullable
    public static User getCurrentUser() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            return null;
        } else {
            return new User(
                    firebaseUser.getUid(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getPhotoUrl() == null ? null : firebaseUser.getPhotoUrl().toString());
        }
    }
}
