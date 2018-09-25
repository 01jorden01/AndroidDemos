package com.j_productions.database.database;

import android.provider.BaseColumns;

public class Contract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    private static final String TEXT_TYPE = " TEXT";

    //interface uitbreiden
    public interface ProductsColumns extends BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_NAME = "productname";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_REMARK = "remark";
    }

    public static abstract class ProductsDB implements ProductsColumns {

        public static final String CREATE_TABLE = "create table "
                + TABLE_NAME + "(" + _ID
                + " integer primary key autoincrement, "
                + COLUMN_PRODUCT_NAME + " text not null, "
                + COLUMN_QUANTITY + " text not null, "
                + COLUMN_PRICE + " real, "
                + COLUMN_REMARK + " text not null "
                + ");";


        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
