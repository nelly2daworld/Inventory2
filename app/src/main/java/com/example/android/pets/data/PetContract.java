package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class PetContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private PetContract() {}

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class PetEntry implements BaseColumns {

        public final static String TABLE_NAME = "vegetables";


        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_VEGETABLE_VARIETY ="name";


        public final static String COLUMN_VEGETABLE_PRICE = "price";

        public final static String COLUMN_VEGETABLE_QUANTITY = "quantity";

        public final static String COLUMN_VEGETABLE_SUPPLIER= "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE= 1;
        public static final int GENDER_FEMALE= 2;
    }

}
