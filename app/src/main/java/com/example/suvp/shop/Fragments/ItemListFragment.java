package com.example.suvp.shop.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.suvp.shop.R;

import java.util.ArrayList;

import DataBase.ManagedObjects.Product;
import General.CustomProductItemListAdapter;
import General.CustomProductListAdapter;

/**
 * Created by suvp on 3/17/2016.
 */
public class ItemListFragment extends ListFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       // setListAdapter(new CustomProductItemListAdapter(getActivity(),  R.layout.list_fragment_layout, -1));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }
}
