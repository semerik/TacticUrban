package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String COLUMN_DATE = "fecha";
    public static final String COLUMN_USER = "user";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LOCALIZACION + " TEXT NOT NULL, " +
                    COLUMN_TIPO_INCIDENTE + " TEXT NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_USER + " TEXT NOT NULL, " +
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
    public void insertIncidente(String localizacion, String tipoIncidente, String descripcion,String date, String user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCALIZACION, localizacion);
        values.put(COLUMN_TIPO_INCIDENTE, tipoIncidente);
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_USER, user);
        values.put(COLUMN_DATE, date);

        this.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    // Método para obtener todos los incidentes
    public Cursor getAllIncidentes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID + " AS _id",
                COLUMN_LOCALIZACION,
                COLUMN_TIPO_INCIDENTE,
                COLUMN_DESCRIPCION,
                COLUMN_USER,
                COLUMN_DATE
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

    //método que obtiene los incidentes por usuario
    public Cursor getIncidentesByUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID + " AS _id",
                COLUMN_LOCALIZACION,
                COLUMN_TIPO_INCIDENTE,
                COLUMN_DESCRIPCION,
                COLUMN_USER,
                COLUMN_DATE
        };

        String selection = COLUMN_USER + " = ?";
        String[] selectionArgs = { username };

        return db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }




}
