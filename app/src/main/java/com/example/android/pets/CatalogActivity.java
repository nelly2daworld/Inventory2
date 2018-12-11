package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;


/**
 * Displays list of pets that were entered and stored in the app.
 */

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new PetDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_VEGETABLE_VARIETY,
                PetEntry.COLUMN_VEGETABLE_PRICE,
                PetEntry.COLUMN_VEGETABLE_QUANTITY,
                PetEntry.COLUMN_VEGETABLE_SUPPLIER};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                PetEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView clothingView = findViewById(R.id.clothing);
        TextView nameView = findViewById(R.id.name);
        TextView priceView = findViewById(R.id.price);
        TextView quantityView = findViewById(R.id.quantity);
        TextView supplierView = findViewById(R.id.supplier);
        try {
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_VEGETABLE_VARIETY);
            int priceColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_VEGETABLE_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_VEGETABLE_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_VEGETABLE_SUPPLIER);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                String currentQuantity = cursor.getString(quantityColumnIndex);
                String currentSupply = cursor.getString(supplierColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                clothingView.append("\n" + currentID);
                nameView.append("\n" + currentName);
                priceView.append("\n" + currentPrice);
                quantityView.append("\n" + currentQuantity);
                supplierView.append("\n" + currentSupply);
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertVegetable() {
        // Gets the database in write mode
        setContentView(R.layout.activity_catalog);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_VEGETABLE_VARIETY, "Cabbage");
        values.put(PetEntry.COLUMN_VEGETABLE_PRICE, 10);
        values.put(PetEntry.COLUMN_VEGETABLE_SUPPLIER, PetEntry.GENDER_FEMALE  );
        values.put(PetEntry.COLUMN_VEGETABLE_QUANTITY,"Express");

        long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving vegetable", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Vegetable saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteEntry() {
       SQLiteDatabase db = mDbHelper.getWritableDatabase();
       db.execSQL("DELETE FROM vegetables");
        db.close();
        setContentView(R.layout.activity_catalog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertVegetable();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteEntry();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
