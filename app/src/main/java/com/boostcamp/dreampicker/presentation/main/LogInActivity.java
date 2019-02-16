package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailRemoteData;
import com.boostcamp.dreampicker.databinding.ActivityLogInBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class LogInActivity extends BaseActivity<ActivityLogInBinding> {
    private CompositeDisposable disposable = new CompositeDisposable();

    private static final int REQUEST_SIGN_IN = 200;

    private GoogleSignInClient mGoogleSignInClient;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Inject
    UserRepository repository;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGoogleSignIn();
        initView();
    }

    private void setUpGoogleSignIn() {
        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, options);
    }

    private void initView() {
        binding.btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        binding.btnSignIn.setOnClickListener(__ -> startSignUpActivity());
    }

    private void startSignUpActivity() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGN_IN) {
            try {
                final GoogleSignInAccount account = GoogleSignIn
                        .getSignedInAccountFromIntent(data)
                        .getResult(ApiException.class);
                if (account != null) {
                    getSignInUser(account);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void getSignInUser(GoogleSignInAccount account) {
        AuthCredential credential =
                GoogleAuthProvider.getCredential(account.getIdToken(), null);

        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    final FirebaseUser user = authResult.getUser();
                    final UserDetailRemoteData newUser =
                            new UserDetailRemoteData(user.getDisplayName(),
                                    user.getPhotoUrl() == null ? null : user.getPhotoUrl().toString(),
                                    0);

                    disposable.add(repository
                            .insertNewUser(user.getUid(), newUser)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::startMainActivity));
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }

    private void startMainActivity() {
        startActivity(MainActivity.getLaunchIntent(this));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
