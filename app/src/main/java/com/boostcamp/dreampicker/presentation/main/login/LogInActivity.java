package com.boostcamp.dreampicker.presentation.main.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

import androidx.lifecycle.ViewModelProviders;
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
    Context context;

    @Inject
    LoginViewModelFactory factory;
    @Inject
    UserRepository repository;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initView();
        observeError();
    }

    private void initViewModel() {
        binding.setVm(ViewModelProviders.of(this, factory).get(LoginViewModel.class));
        binding.getVm().getIsLoggedIn().observe(this, isLoggedIn -> {
            if(isLoggedIn){
                startMainActivityAndFinish();
            }
        });
    }

    private void initView() {
        binding.btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        binding.btnSignIn.setOnClickListener(__ -> startSignUpActivity());
    }

    private void startSignUpActivity() {
        disposable.add(TedRxOnActivityResult.with(this)
                .startActivityForResult(googleSignInClient.getSignInIntent())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> processSignInResultIntent(result.getData()),
                        error -> Toast.makeText(context, R.string.sign_in_fail, Toast.LENGTH_SHORT).show()));
    }

    private void processSignInResultIntent(Intent intent){
        try {
            final GoogleSignInAccount account = GoogleSignIn
                    .getSignedInAccountFromIntent(intent)
                    .getResult(ApiException.class);
            if (account != null && account.getIdToken() != null) {
                binding.getVm().processLogin(account.getIdToken());
            }
        } catch (ApiException e) {
            Toast.makeText(context, R.string.sign_in_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainActivityAndFinish() {
        startActivity(MainActivity.getLaunchIntent(this));
        finish();
    }

    private void observeError(){
        binding.getVm().getError().observe(this, error ->
                showToast(getString(R.string.comment_error_message, error.getMessage())));
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
