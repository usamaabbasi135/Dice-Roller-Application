package com.example.diceroller.usama_abbasi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RollLengthDialogFragment extends DialogFragment {

    public interface OnRollLengthSelectedListener{
        void onRollLengthClick(int which);
    }

    private OnRollLengthSelectedListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.dialog_box_title);
        builder.setItems(R.array.options_array, (dialog,which) ->{
            listener.onRollLengthClick(which);
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        listener = (OnRollLengthSelectedListener) context;
    }
}
