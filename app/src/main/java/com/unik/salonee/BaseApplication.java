package com.unik.salonee;

import android.app.Application;

import com.unik.salonee.models.CartProductsModel;
import com.unik.salonee.models.CartServicesModel;

import java.util.ArrayList;

public class BaseApplication extends Application {

    public static ArrayList<CartServicesModel> cartModelServicesArrayList;
    public static ArrayList<CartProductsModel> cartModelProductsArrayList;

    @Override
    public void onCreate() {
        super.onCreate();
        cartModelServicesArrayList = new ArrayList<>();
        cartModelProductsArrayList = new ArrayList<>();
       /* CheckoutConfig checkoutConfig = new CheckoutConfig(
                this,
                "Ad4_eqyEEZgQ1ta30t874zpfbYWD0b7Mn8t2qARRyKJeJyiIjvzG8S1y4HKFl-EhVOMBqfBIkYToLzf9",
                Environment.SANDBOX,
                "com.giftcode4u.app://paypalpay",//"%s://paypalpay",
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false
                )
        );*/
//        PayPalCheckout.setConfig(checkoutConfig);
    }
}