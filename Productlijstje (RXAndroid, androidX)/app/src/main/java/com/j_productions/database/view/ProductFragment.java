package com.j_productions.database.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.j_productions.database.R;
import com.j_productions.database.adapter.ProductAdapter;
import com.j_productions.database.database.Contract;
import com.j_productions.database.database.DatabaseAccess;
import com.j_productions.database.model.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


public class ProductFragment extends Fragment {

    int spanCount;
    TextView textViewRemark;
    private OnFragmentInteractionListener mListener;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private List<Product> productsList;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_products);
        getLocalProducts();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void RecycleViewerOpvullen(final List<Product> productlijst) {
        adapter = new ProductAdapter(getContext(), productsList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        // layoutManager.setAlignContent(AlignContent.CENTER);


        // SpanCount berekenen
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        spanCount = (int) (dpWidth / 222);

        if (spanCount <= 1) {
            recyclerView.setLayoutManager(layoutManager);

        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
        }

        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                //wissen uit DB
                deleteProductFromDB(productsList.get(position));

                // backup item voor UNDO
                final Product deletedProduct = productsList.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                //verwijder van lijst
                adapter.removeItemFromList(deletedProduct);

                //snackbar om ongedaan te maken
                Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(android.R.id.content), deletedProduct.getProductname() + " removed from DB!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Item terugplaatsen in DB
                        insertProductIntoDB(deletedProduct);

                        // UNDO: terug in de lijst plaatsen
                        adapter.restoreItem(deletedProduct, deletedIndex);
                    }
                });
                snackbar.show();

                productsList.remove(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }


    //region DATABASE transaties
    private void deleteProductFromDB(Product p) {
        ContentValues values = new ContentValues();
        values.put(Contract.ProductsColumns.COLUMN_PRODUCT_NAME, p.getProductname());
        //Helper.executeAsyncTask(new DeleteProductTask(context), values);
        DatabaseAccess.delete(this.getContext(), Contract.ProductsColumns.TABLE_NAME, Contract.ProductsDB.COLUMN_PRODUCT_NAME, p.getProductname())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        Snackbar.make(getActivity().findViewById(android.R.id.content), "Product deleted from database!", Snackbar.LENGTH_INDEFINITE).show();
        //cursor nog niet aangepast
        //mCursor.requery();          //deprecated (todo)
    }

    private void insertProductIntoDB(Product p) {
        ContentValues values = new ContentValues();
        values.put(Contract.ProductsColumns.COLUMN_PRICE, p.getPrice());
        values.put(Contract.ProductsColumns.COLUMN_PRODUCT_NAME, p.getProductname());
        values.put(Contract.ProductsColumns.COLUMN_QUANTITY, p.getQuantity());
        values.put(Contract.ProductsColumns.COLUMN_REMARK, p.getRemark());

        DatabaseAccess.insert(this.getContext(), Contract.ProductsColumns.TABLE_NAME, Contract.ProductsDB.COLUMN_PRODUCT_NAME, values)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong < 0) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Something went wrong ;-(", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Product restored", Snackbar.LENGTH_SHORT).show();

                        }
                    }
                });

        //cursor nog niet aangepast
        //mCursor.requery();          //deprecated (todo)
    }


    private void getLocalProducts() {
        DatabaseAccess.getAll(this.getContext(), "products")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Cursor>() {
                    @Override
                    public void accept(Cursor cursor) throws Exception {
                        if (cursor.getCount() > 0) {
                            printProducts(cursor);
                        }
                    }
                });
    }

    private void printProducts(Cursor cursor) {
        productsList = new ArrayList<Product>();
        while (cursor.moveToNext()) {
            String[] columns = new String[]{
                    Contract.ProductsColumns.COLUMN_PRODUCT_NAME,
                    Contract.ProductsColumns.COLUMN_PRICE,
                    Contract.ProductsColumns.COLUMN_QUANTITY,
                    Contract.ProductsColumns.COLUMN_REMARK
            };
            Product product = new Product();
            product.setProductname(cursor.getString(cursor.getColumnIndex(Contract.ProductsColumns.COLUMN_PRODUCT_NAME)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndex(Contract.ProductsColumns.COLUMN_PRICE)));
            product.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contract.ProductsColumns.COLUMN_QUANTITY))));
            product.setRemark(cursor.getString(cursor.getColumnIndex(Contract.ProductsColumns.COLUMN_REMARK)));
            productsList.add(product);
        }
        RecycleViewerOpvullen(productsList);
    }
    //endregion

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
