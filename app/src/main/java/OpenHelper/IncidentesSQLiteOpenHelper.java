package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncidentesSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "IncidentesDatabase.db";

    // Nombre de la tabla y columnas
    public static final String TABLE_NAME = "incidente";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOCALIZACION = "localizacion";
    public static final String COLUMN_TIPO_INCIDENTE = "tipo_incidente";
    public static final String COLUMN_DESCRIPCION = "descripcion";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LOCALIZACION + " TEXT NOT NULL, " +
                    COLUMN_TIPO_INCIDENTE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPCION + " TEXT NOT NULL);";

    public IncidentesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar las actualizaciones de la base de datos si es necesario.
        // Por ejemplo, si necesitas cambiar la estructura de la tabla en futuras versiones.
    }

    // Método para abrir la base de datos
    public void openBD() {
        this.getWritableDatabase();
    }

    // Método para cerrar la base de datos
    public void closeBD() {
        this.close();
    }

    // Método para insertar un incidente
    public void insertIncidente(String localizacion, String tipoIncidente, String descripcion) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCALIZACION, localizacion);
        values.put(COLUMN_TIPO_INCIDENTE, tipoIncidente);
        values.put(COLUMN_DESCRIPCION, descripcion);

        this.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    // Método para obtener todos los incidentes
    public Cursor getAllIncidentes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_LOCALIZACION,
                COLUMN_TIPO_INCIDENTE,
                COLUMN_DESCRIPCION
        };

        return db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }
}
