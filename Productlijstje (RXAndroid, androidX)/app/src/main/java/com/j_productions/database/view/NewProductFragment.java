package com.j_productions.database.view;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j_productions.database.R;
import com.j_productions.database.database.Contract;
import com.j_productions.database.database.DatabaseAccess;
import com.j_productions.database.model.Product;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


public class NewProductFragment extends Fragment {

    TextView txtName;
    TextView txtQuantity;
    TextView txtPrice;
    TextView txtRemark;

    private Product newProduct = new Product();
    private OnFragmentInteractionListener mListener;

    public NewProductFragment() {
        // Required empty public constructor
    }

    static private <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
    //endregion

    public static NewProductFragment newInstance(String param1, String param2) {
        return new NewProductFragment();
    }

    //region TryParse (net als in C#)
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_product, container, false);

        txtName = v.findViewById(R.id.txtName);
        txtPrice = v.findViewById(R.id.txtPrice);
        txtQuantity = v.findViewById(R.id.txtQuantity);
        txtRemark = v.findViewById(R.id.txtRemark);


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waardesVoorNewItemInstellen();
                saveProductToDb();
                resetProduct();


//                Snackbar.make(getActivity().findViewById(android.R.id.content),
//                        "Product added to the database", Snackbar.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void waardesVoorNewItemInstellen() {
        newProduct.setProductname(txtName.getText().toString());
        newProduct.setRemark(txtRemark.getText().toString());

        if (tryParseDouble(txtPrice.getText().toString())) {
            newProduct.setPrice(Double.parseDouble(txtPrice.getText().toString()));
        }

        if (tryParseInt(txtQuantity.getText().toString())) {
            newProduct.setQuantity(Integer.parseInt(txtQuantity.getText().toString()));
        }

    }

    private void saveProductToDb() {
        ContentValues values = new ContentValues();
        values.put(Contract.ProductsColumns.COLUMN_PRICE, newProduct.getPrice());
        values.put(Contract.ProductsColumns.COLUMN_PRODUCT_NAME, newProduct.getProductname());
        values.put(Contract.ProductsColumns.COLUMN_QUANTITY, newProduct.getQuantity());
        values.put(Contract.ProductsColumns.COLUMN_REMARK, newProduct.getRemark());

        DatabaseAccess.insert(this.getContext(), Contract.ProductsColumns.TABLE_NAME, Contract.ProductsDB.COLUMN_PRODUCT_NAME, values)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong < 0) {
                            Log.d(getClass().getName(), "Error inserting data into the database.");
                        } else {
                            Toast toast = Toast.makeText(getContext(), "Product successfully saved", Toast.LENGTH_LONG);
                            toast.show();

                            Log.d("ViewModel", "saved to sqlite");
                        }
                    }
                });
    }

    private void resetProduct() {
        txtName.setText(null);
        txtQuantity.setText(null);
        txtPrice.setText(null);
        txtRemark.setText(null);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}