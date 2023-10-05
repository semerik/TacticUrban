package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";

    // Nombre de la tabla y columnas
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_UBICATION = "ubication";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FULL_NAME + " TEXT NOT NULL, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_GENDER + " TEXT NOT NULL, " +
                    COLUMN_BIRTHDATE + " TEXT NOT NULL, " +
                    COLUMN_UBICATION + " TEXT NOT NULL);";


    public SQLite_OpenHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, version);
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Metodo para abrir base de datos
    public void openBD(){
            this.getWritableDatabase();
    }
    //Metodo para abrir base de cerrar
    public void closeBD(){
            this.close();
    }

    public void insertUser(String fullName, String userName, String eMail,String password,  String gender, String birthdate, String ubication){

        ContentValues values = new ContentValues();

        values.put("full_name",fullName);
        values.put("username",userName);
        values.put("email",eMail);
        values.put("password",password);
        values.put("gender",gender);
        values.put("birthdate",birthdate);
        values.put("ubication",ubication);
        this.getWritableDatabase().insert(TABLE_NAME,null,values);


    }

    public Cursor selectUser(String user, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        // Columnas que deseas recuperar de la tabla
        String[] projection = {
                COLUMN_ID,
                COLUMN_FULL_NAME,
                COLUMN_USERNAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD,
                COLUMN_GENDER,
                COLUMN_BIRTHDATE,
                COLUMN_UBICATION
        };

        // Clausula WHERE para filtrar por usuario y contraseña
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {user, password};

        // Realizar la consulta
        Cursor cursor = db.query(
                TABLE_NAME,      // Nombre de la tabla
                projection,      // Columnas que deseas recuperar
                selection,       // Clausula WHERE
                selectionArgs,   // Valores para la clausula WHERE
                null,            // GROUP BY (no se usa)
                null,            // HAVING (no se usa)
                null             // ORDER BY (no se usa)
        );

        return cursor;
    }


}
