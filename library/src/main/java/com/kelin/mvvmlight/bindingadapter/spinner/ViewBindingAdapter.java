package com.kelin.mvvmlight.bindingadapter.spinner;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kelin.mvvmlight.R;
import com.kelin.mvvmlight.command.ReplyCommand;

/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {

    @BindingAdapter({"spinner_items"})
    public static void spinnerItems(Spinner spinner, final ObservableList<String> viewModelList) {
        if (viewModelList != null && !viewModelList.isEmpty()) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(spinner.getContext(), R.layout.myspinner);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            for (String name : viewModelList) {
                spinnerArrayAdapter.add(name);
            }
            spinner.setAdapter(spinnerArrayAdapter);
        }
    }

    @BindingAdapter({"after_select"})
    public static void afterSelect(Spinner spinner,final ReplyCommand<String> command) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                command.execute(spinner.getAdapter().getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                command.execute("");
            }
        });
    }

}
