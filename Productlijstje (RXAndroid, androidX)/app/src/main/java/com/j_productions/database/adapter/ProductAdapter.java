package com.j_productions.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j_productions.database.R;
import com.j_productions.database.model.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    private List<Product> productLijst;
    private Context context;


    public ProductAdapter(@NonNull Context context, List<Product> productList) {
        this.context = context;
        this.productLijst = productList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        double price = productLijst.get(position).getPrice();
        String priceAsString = new Double(price).toString();

        holder.txtPrice.setText(priceAsString);

        holder.txtRemark.setText(productLijst.get(position).getRemark());
        holder.txtNaam.setText(productLijst.get(position).getProductname());
    }

    @Override
    public int getItemCount() {
        return productLijst.size();
    }

    public void removeItem(int position) {
        productLijst.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Product item, int position) {
        productLijst.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public void removeItemFromList(Product prod) {
        int currPosition = productLijst.indexOf(prod);
        notifyItemRemoved(currPosition);
    }


    // VIEWHOLDER  ---> item voor lijst
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtNaam;
        TextView txtPrice;
        TextView txtRemark;
        CardView cv;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            cv = view.findViewById(R.id.card_view);
            txtPrice = view.findViewById(R.id.textviewPrice);
            txtRemark = view.findViewById(R.id.textViewRemark);
            txtNaam = view.findViewById(R.id.textViewNaam);
        }
    }
}
