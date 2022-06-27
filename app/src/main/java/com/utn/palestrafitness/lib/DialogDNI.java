package com.utn.palestrafitness.lib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.utn.palestrafitness.R;

public class DialogDNI extends AppCompatDialogFragment {

    boolean estado = false;

    private DialogListener listener;

    public boolean getEstado() {
        return estado;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.display_dni, null);
        builder.setView(view)
                .setTitle("DNI duplicado")
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    estado = false;
                    listener.getsState(estado);
                })
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    estado = true;
                    listener.getsState(estado);
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (DialogListener) context;
    }

    public interface DialogListener {

        void getsState(boolean state);
    }

}
