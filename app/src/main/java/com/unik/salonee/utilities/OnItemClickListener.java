package com.unik.salonee.utilities;


import android.content.Context;

import com.unik.salonee.models.CartServicesModel;

public interface OnItemClickListener {
    void onItemClick(CartServicesModel cartServicesModel, int position, Context context);
}
