package com.boostcamp.dreampicker.data.remote.google;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public class GoogleAccountClient implements AccountApi {
    @NonNull
    private final GoogleSignInClient googleSignInClient;
    @NonNull
    private final FirebaseAuth firebaseAuth;

    @Inject
    GoogleAccountClient(@NonNull GoogleSignInClient googleSignInClient) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.googleSignInClient = googleSignInClient;
    }

    @NonNull
    @Override
    public Single<FirebaseUser> signIn(@NonNull String userToken) {
        AuthCredential credential =
                GoogleAuthProvider.getCredential(userToken, null);

        return Single.create(emitter -> FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnSuccessListener(authResult ->
                        emitter.onSuccess(authResult.getUser()))
                .addOnFailureListener(Throwable::printStackTrace));
    }

    @NonNull
    @Override
    public Completable signOut() {
        return Completable.create(emitter -> {
            firebaseAuth.signOut();
            googleSignInClient.signOut()
                    .addOnSuccessListener(__ -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }
}
