package com.example.prm392_project_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prm392_project_2.cartutil.CartAdapter;
import com.example.prm392_project_2.cartutil.CartDAO;
import com.example.prm392_project_2.cartutil.CartDatabase;
import com.example.prm392_project_2.cartutil.CartItem;
import com.example.prm392_project_2.dtos.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<CartItem> listCart;
    private SharedPreferences sharedPreferences;
    private CartDatabase cartDatabase;
    private CartDAO cartItemDao;
    private RecyclerView cartView;
    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartView = (RecyclerView) view.findViewById(R.id.cartList1);
        Intent intent = getActivity().getIntent();
        Product product = (Product) intent.getSerializableExtra("productData");

        // Initialize Room Database
        cartDatabase = Room.databaseBuilder(getActivity(),
                CartDatabase.class, "cart-db").build();
        cartItemDao = cartDatabase.cartDao();
        // Retrieve cart items asynchronously
        new Thread(() -> {
            listCart = new ArrayList<>(cartItemDao.getAll());
            if(listCart==null) {listCart=new ArrayList<>();}
            boolean productExists = false;
            int existingProductIndex = -1;
            if (product != null) {


                for (int i = 0; i < listCart.size(); i++) {
                    if (listCart.get(i).getProduct().equals(product)) {
                        listCart.get(i).setQuantity(listCart.get(i).getQuantity() + 1);
                        productExists = true;
                        existingProductIndex = i;
                        break;
                    }
                }
            }
            if (!productExists) {

                listCart.add(new CartItem(product, 1));
                cartDatabase.cartDao().insert(new CartItem(product,1));
            } else {
                // Update the existing cart item in the database
                cartDatabase.cartDao().update(listCart.get(existingProductIndex));
            }
            // Update UI on the main thread

            getActivity().runOnUiThread(() -> {
                CartAdapter adapter = new CartAdapter(listCart,cartItemDao,getActivity());
                cartView.setAdapter(adapter);
                cartView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.notifyDataSetChanged();
            });
        }).start();
        return view;
    }
}