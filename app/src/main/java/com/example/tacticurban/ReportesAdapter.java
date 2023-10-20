package com.example.tacticurban;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import OpenHelper.IncidentesSQLiteOpenHelper;

public class ReportesAdapter extends CursorAdapter {

    public ReportesAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_reporte, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tipoIncidenteTextView = view.findViewById(R.id.tipoIncidenteTextView);
        TextView direccionTextView = view.findViewById(R.id.direccionTextView);
        TextView fechaTextView = view.findViewById(R.id.fechaTextView);

        String tipoIncidente = cursor.getString(cursor.getColumnIndexOrThrow(IncidentesSQLiteOpenHelper.COLUMN_TIPO_INCIDENTE));
        String direccion = cursor.getString(cursor.getColumnIndexOrThrow(IncidentesSQLiteOpenHelper.COLUMN_LOCALIZACION));
        String fecha = cursor.getString(cursor.getColumnIndexOrThrow(IncidentesSQLiteOpenHelper.COLUMN_DATE));

        tipoIncidenteTextView.setText(tipoIncidente);
        direccionTextView.setText(direccion);
        fechaTextView.setText(fecha);
    }
}
