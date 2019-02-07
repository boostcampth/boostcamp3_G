package com.boostcamp.dreampicker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.remote.firebase.UserFirebaseService;
import com.boostcamp.dreampicker.data.source.remote.firebase.request.InsertUserRequest;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.databinding.ActivityLogInBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class LogInActivity extends BaseActivity<ActivityLogInBinding> {
    private CompositeDisposable disposable = new CompositeDisposable();

    private static final int REQUEST_SIGN_IN = 200;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private boolean isTest = false;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGoogleSignIn();
        firebaseAuth = FirebaseAuth.getInstance();
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
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startMainActivity();
        }
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

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            insertNewUser(user);
                        }
                    } else {
                        task.addOnFailureListener(Throwable::printStackTrace);
                    }
                });
    }

    private void insertNewUser(@NonNull FirebaseUser user) {

        final InsertUserRequest newUser = new InsertUserRequest(user.getUid(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPhotoUrl() == null ? null : user.getPhotoUrl().toString());

        disposable.add(UserRepository.getInstance(UserFirebaseService.getInstance())
                .insertNewUser(newUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::startMainActivity));
    }

    private void startMainActivity() {
        startActivity(MainActivity.getLaunchIntent(this));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.clear();
        }
    }
}
