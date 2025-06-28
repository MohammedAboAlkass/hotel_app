package com.example.finalproject_ucas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.finalproject_ucas.model.Booking;
import com.example.finalproject_ucas.model.RoomDetailsAdmin;
import com.example.finalproject_ucas.model.Users;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    static String db_name = "HOTEL" ;
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, db_name, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStat = "CREATE TABLE USERS( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT ," +
                "EMAIL TEXT UNIQUE, " +
                "PASSWORD TEXT ," +
                "ADMIN INTEGER)";

        db.execSQL(sqlStat);
        db.execSQL("INSERT INTO USERS (username, email, password, admin) " +
                "VALUES ('ADMIN', 'admin@admin.com', 'ADMIN', 1)");

        db.execSQL("CREATE TABLE ROOMS (" +
                "room_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "room_number TEXT," +
                "room_type TEXT," +
                "price_per_night REAL," +
                "availability INTEGER," +
                "image_uri TEXT," +
                "descrip TEXT" +
                ")");

        db.execSQL("CREATE TABLE BOOKING (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_user INTEGER," +
                "room_id INTEGER," +
                "check_in_date TEXT," +
                "check_out_date TEXT," +
                "guests INTEGER," +
                "status TEXT," +
                "FOREIGN KEY(id_user) REFERENCES Users(id)," +
                "FOREIGN KEY(room_id) REFERENCES Rooms(room_id)" +
                ")");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//            db.execSQL("ALTER TABLE USERS ADD COLUMN admin INTEGER");

    }

    public boolean register(Users users){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", users.getName());
        values.put("email", users.getEmail());
        values.put("password",users.getPassword() );
        values.put("admin",users.getAdmin() );
        long result = db.insert("USERS", null, values);
        return result != -1;

    }
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE email = ? AND password = ?",
                new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public int getUserType(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT admin FROM USERS WHERE email = ? AND password = ?",
                new String[]{email, password});

        int userType = -1;  // -1 = لو المستخدم غير موجود
        if (cursor.moveToFirst()) {
            userType = cursor.getInt(0);  // ياخذ قيمة admin من أول عمود بالـ cursor
        }
        cursor.close();
//        db.execSQL("INSERT INTO ROOMS (room_number, room_type, price_per_night, availability, image_uri) VALUES " +
//                "('101', 'Single Room', 50.0, 1, '" + 0 + "')," +
//                "('102', 'Double Room', 75.0, 1, '" + 0 + "')," +
//                "('201', 'Suite', 120.0, 1, '" + 0 + "')");
        return userType;
    }
    public boolean addBooking(int userid, int roomId, String checkIn, String checkOut, int guests, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_user", userid);
        values.put("room_id", roomId);
        values.put("check_in_date", checkIn);
        values.put("check_out_date", checkOut);
        values.put("guests", guests);
        values.put("status", status);

        long result = db.insert("BOOKING", null, values);
        return result != -1;
    }

    public void cancelBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "Cancelled");
        db.delete("BOOKING", "booking_id = ?", new String[]{String.valueOf(bookingId)});
    }

    public ArrayList<Booking> getBookingsByUser(int p_id) {
        ArrayList<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT b.booking_id, r.room_type, b.check_in_date, b.check_out_date, b.guests, b.status " +
                        "FROM BOOKING b JOIN ROOMS r ON b.room_id = r.room_id " +
                        "WHERE b.id_user = ?",
                new String[]{String.valueOf(p_id)}
        );

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String roomType = c.getString(1);
                String checkIn = c.getString(2);
                String checkOut = c.getString(3);
                int guests = c.getInt(4);
                String status = c.getString(5);

                list.add(new Booking(id, roomType, checkIn, checkOut, guests, status));
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }
    public void deleteRoom(int roomId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ROOMS", "room_id = ?", new String[]{String.valueOf(roomId)});
    }

    public boolean addRoom(RoomDetailsAdmin room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String imageUri = String.valueOf(room.getImageUri());

        values.put("room_number", room.getRoomNumber());
        values.put("room_type", room.getRoomType());
        values.put("price_per_night", room.getPricePerNight());
        values.put("availability", room.getAvailability());
        values.put("image_uri", room.getImageUri().toString());

        long result = db.insert("ROOMS", null, values);
        return result != -1;  // لو -1 معناها فشل الإضافة
    }
public ArrayList<RoomDetailsAdmin> getAllRooms() {
    ArrayList<RoomDetailsAdmin> roomList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM ROOMS", null);
    if (cursor.moveToFirst()) {
        do {
            int roomId = cursor.getInt(cursor.getColumnIndexOrThrow("room_id"));
            String roomNumber = cursor.getString(cursor.getColumnIndexOrThrow("room_number"));
            String roomType = cursor.getString(cursor.getColumnIndexOrThrow("room_type"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price_per_night"));
            int availability = cursor.getInt(cursor.getColumnIndexOrThrow("availability"));
            String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("image_uri"));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow("descrip"));
            RoomDetailsAdmin room = new RoomDetailsAdmin(roomId, roomNumber, roomType, price, availability, imageUri, desc);
            roomList.add(room);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return roomList;
}
    public boolean updateRoom(RoomDetailsAdmin room) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("room_type", room.getRoomType());
        values.put("price_per_night", room.getPricePerNight());
        values.put("room_number", room.getRoomNumber());
        values.put("descrip", room.getDesc());
        values.put("image_uri", room.getImageUri());
        int updated = database.update("ROOMS", values, "room_id=?", new String[]{String.valueOf(room.getRoomId())});

        return updated > 0;
    }


}
