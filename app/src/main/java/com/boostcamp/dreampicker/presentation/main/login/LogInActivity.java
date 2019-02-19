package com.boostcamp.dreampicker.presentation.main.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.databinding.ActivityLogInBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.main.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class LogInActivity extends BaseActivity<ActivityLogInBinding> {
    private final CompositeDisposable disposable = new CompositeDisposable();

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Inject
    GoogleSignInClient googleSignInClient;

    @Inject
    UserRepository repository;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding.btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        binding.btnSignIn.setOnClickListener(__ -> startSignUpActivity());
    }

    private void startSignUpActivity() {
        disposable.add(TedRxOnActivityResult.with(this)
                .startActivityForResult(googleSignInClient.getSignInIntent())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    try {
                        final GoogleSignInAccount account = GoogleSignIn
                                .getSignedInAccountFromIntent(result.getData())
                                .getResult(ApiException.class);
                        if (account != null && account.getIdToken() != null) {
                            getUserIdToken(account.getIdToken());
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                })
        );
    }

    private void getUserIdToken(String idToken){
        repository.signIn(idToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> startMainActivity(),
                        error -> error.printStackTrace());
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
