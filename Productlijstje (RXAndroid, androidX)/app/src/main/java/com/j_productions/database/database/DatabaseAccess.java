package com.j_productions.database.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class DatabaseAccess {


    public static final String TAG = "DatabaseAccess";


    //region GET queries
    //get all
    public static Observable<Cursor> getAll(Context context, String table) {
        DatabaseHelper connection = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = connection.getReadableDatabase();

        return makeObservable(getAll(db, table))
                .subscribeOn(Schedulers.computation());
    }

    private static Callable<Cursor> getAll(final SQLiteDatabase db, final String table) {
        return new Callable<Cursor>() {
            @Override
            public Cursor call() throws Exception {
                Cursor data = db.query(
                        table,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Contract.ProductsColumns.COLUMN_PRODUCT_NAME
                );
                data.getCount();

                return data;
            }
        };
    }

    //endregion


    //region INSERT
    public static Observable<Long> insert(Context context, String table, String nullColumnHack, ContentValues values) {
        DatabaseHelper connection = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = connection.getWritableDatabase();

        return makeObservable(insert(db, table, nullColumnHack, values))
                .subscribeOn(Schedulers.computation());
    }

    private static Callable<Long> insert(final SQLiteDatabase db, final String table, final String nullColumnHack, final ContentValues values) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long inserted_rows = db.insert(table, nullColumnHack, values);
                return inserted_rows;
            }
        };
    }


    /*
     ** General
     */
    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.defer(new Callable<ObservableSource<T>>() {
            @Override
            public ObservableSource<T> call() throws Exception {
                return new ObservableSource<T>() {
                    @Override
                    public void subscribe(@NonNull Observer<? super T> observer) {
                        try {
                            observer.onNext(func.call());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
            }

        });
    }


    //endregion


    //region UPDATE
    public static Observable<Long> update(Context context, String table, String[] columns, String[] columnValues, String key, String value) {
        DatabaseHelper connection = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = connection.getWritableDatabase();

        return makeObservable(update(db, table, columns, columnValues, key, value))
                .subscribeOn(Schedulers.computation());
    }

    private static Callable<Long> update(final SQLiteDatabase db, final String table, final String[] columns, final Object[] columnValues,
                                         final String key, final String value) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long changedRows = -1;

                ContentValues values = new ContentValues();
                try {
                    int counter = 0;
                    for (String column : columns) {
                        Type type = columnValues[counter].getClass();

                        if (type == String.class) {
                            values.put(column, (String) columnValues[counter]);
                        } else if (type == Integer.class) {
                            values.put(column, (Integer) columnValues[counter]);
                        } else if (type == Boolean.class) {
                            values.put(column, (Boolean) columnValues[counter]);
                        } else if (type == Double.class) {
                            values.put(column, (Double) columnValues[counter]);
                        }

                        counter++;
                    }

                    changedRows = db.update(
                            table,
                            values,
                            key + " = ?",
                            new String[]{value}
                    );
                } catch (Exception ex) {
                    Log.d(getClass().getName(), ex.getMessage());
                }

                return changedRows;
            }
        };
    }

    //endregion


    //region DELETE
    public static Observable<Long> delete(Context context, String table, String key, String value) {
        DatabaseHelper connection = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = connection.getWritableDatabase();

        return makeObservable(delete(db, table, key, value))
                .subscribeOn(Schedulers.computation());
    }

    private static Callable<Long> delete(final SQLiteDatabase db, final String table, final String key, final String value) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long changedRows = db.delete(
                        table,
                        key + " = ?",
                        new String[]{value}
                );

                return changedRows;
            }
        };
    }

    //endregion
}
