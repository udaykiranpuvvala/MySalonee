package com.unik.salonee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.unik.modelapp.utilities.Constants;
import com.unik.salonee.R;
import com.unik.salonee.utilities.Utility;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashDelay();
    }

    private void splashDelay() {
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (Utility.isValueNullOrEmpty(Utility.getSharedPreference(SplashActivity.this, Constants.ONBOARDED))) {
                    i = new Intent(SplashActivity.this, OnBoardScreenActivity.class);
                } else {
                    if(Utility.isValueNullOrEmpty(Utility.getSharedPreference(SplashActivity.this,Constants.USERID))){
                        i = new Intent(SplashActivity.this, LoginActivity.class);
                    }else {
                        i = new Intent(SplashActivity.this, HomeActivity.class);
                    }
                }
                startActivity(i);

                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}




/*
        PayPalButton payPalButton = findViewById(R.id.payPalButton);
        payPalButton.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value("10.00")
                                                        .build()
                                        )
                                        .build()
                        );
                        Order order = new Order(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                            }
                        });
                    }
                }
        );
        payPalButton.setPaymentButtonEligibilityStatusChanged(new PaymentButtonEligibilityStatusChanged() {
            @Override
            public void onPaymentButtonEligibilityStatusChanged(@NotNull PaymentButtonEligibilityStatus paymentButtonEligibilityStatus) {
                Log.i("PaymentButton", String.format("paymentButton : %s", paymentButtonEligibilityStatus));
            }
        });
*/