package com.example.prm392_project_2.cartutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project_2.CartFragment;
import com.example.prm392_project_2.MainActivity;
import com.example.prm392_project_2.R;
import com.example.prm392_project_2.dtos.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> cartItems;
    private  CartDAO cartDAO;
    private Context context;
    public CartAdapter(List<CartItem> cartItems,CartDAO cartDAO,Context context) {
        this.cartItems = cartItems;
        this.cartDAO=cartDAO;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView priceTextView;
        private TextView quantityTextView;
        private ImageView productImageView;
        private ImageButton removeFromCart;

        public ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            productImageView = itemView.findViewById(R.id.cartImageView);
            removeFromCart = itemView.findViewById(R.id.removeButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartItem cartItem = cartItems.get(position);
        Product product = cartItem.getProduct();

        holder.productNameTextView.setText(product.getProductName());
        holder.priceTextView.setText("Price: $" + product.getPrice());
        holder.quantityTextView.setText("Quantity: " + cartItem.getQuantity());
        Picasso.get().load(product.getImgPath()).into(holder.productImageView);
        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartItem cartItem1=cartItems.get(position);
                cartItems.remove(cartItem1);
                new DeleteCartItemTask(cartDAO).execute(cartItem1);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("nav", 2);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    private static class DeleteCartItemTask extends AsyncTask<CartItem, Void, Void> {
        private CartDAO cartDAO;

        public DeleteCartItemTask(CartDAO cartDAO) {
            this.cartDAO = cartDAO;
        }

        @Override
        protected Void doInBackground(CartItem... cartItems) {
            cartDAO.delete(cartItems[0]);
            return null;
        }
    }
}
