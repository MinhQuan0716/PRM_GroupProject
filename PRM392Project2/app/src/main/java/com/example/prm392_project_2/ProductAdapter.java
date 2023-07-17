package com.example.prm392_project_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project_2.dtos.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product> listProduct;
    private Context context;
    public ProductAdapter (ArrayList<Product> listProduct,Context context){
        this.listProduct=listProduct;
        this.context=context;
    }
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        //Inflate the custom layout
        View view =layoutInflater.inflate(R.layout.listproduct,parent,false);
        //Return view holder instance
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product=listProduct.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText(String.valueOf(product.getPrice()));
        holder.txtProductDescription.setText(product.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product clickedProduct = listProduct.get(position);
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", clickedProduct.getId());
                context.startActivity(intent);
            }
        });


        Picasso.get().load(product.getImgPath()).into(holder.productImage);
    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView txtProductName;
     TextView txtProductPrice;
     TextView txtProductDescription;
     ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductDescription =itemView.findViewById(R.id.productDescription);
            txtProductName=itemView.findViewById(R.id.totalPriceId);
            txtProductPrice=itemView.findViewById(R.id.productPrice);
            productImage =itemView.findViewById(R.id.prouctimageView);
        }

    }

}
