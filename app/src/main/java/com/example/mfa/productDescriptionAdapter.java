package com.example.mfa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class productDescriptionAdapter extends ArrayAdapter<Product> {

    Context context;
    List<Product> arrayListProduct;

    public productDescriptionAdapter(@NonNull Context context, List<Product> arrayListProduct) {
        super(context, R.layout.list_item_layout,arrayListProduct);

        this.context = context;
        this.arrayListProduct = arrayListProduct;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,null,true);

        TextView sname = view.findViewById(R.id.sname);
        TextView prname = view.findViewById(R.id.prname);
        TextView prdesc = view.findViewById(R.id.prdesc);
        TextView prprice = view.findViewById(R.id.prprice);

        sname.setText(arrayListProduct.get(position).getShopName());
        prname.setText(arrayListProduct.get(position).getProductName());
        prdesc.setText(arrayListProduct.get(position).getProductDescription());
        prprice.setText("price=" + arrayListProduct.get(position).getProductPrice() + "/" );

        return view;
    }
}
