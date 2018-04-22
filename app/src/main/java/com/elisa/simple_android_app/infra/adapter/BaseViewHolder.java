package com.elisa.simple_android_app.infra.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.elisa.simple_android_app.BR;


/**
 * Created by nSafari on 1/2/2018.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    protected final ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }
}
