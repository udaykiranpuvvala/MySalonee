package com.unik.salonee.utilities;


import android.content.Context;

import com.unik.salonee.models.CartProductsModel;

public interface OnItemProductsClickListener {
    void onItemClick(CartProductsModel cartProductsModel, int position, Context context);
}
