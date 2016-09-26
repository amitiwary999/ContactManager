package com.example.amit.contactmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 21/9/16.
 */
public class Groupby extends AppCompatActivity {
    int i;
    ListView lv;
    DatabaseHandler dbHandler;
    List<Contact> Contacts = new ArrayList<Contact>();
    ArrayAdapter<Contact> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent mIntent = getIntent();
        i=mIntent.getIntExtra("grpid",0);
        lv=(ListView)findViewById(R.id.listView);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#ffffff"));
        dbHandler = new DatabaseHandler(getApplicationContext());

        selecygrp();


    }
    public void selecygrp(){
        //Toast.makeText(Groupby.this,"position = "+i,Toast.LENGTH_SHORT).show();
        switch (i){
            case 0:
                List<Contact> addableContacts=dbHandler.getGContact("Home");
                int contactCount = dbHandler.getGCount("Home");
               // Toast.makeText(Groupby.this,"positioncc = "+contactCount,Toast.LENGTH_LONG).show();

                for (int i = 0; i < contactCount; i++){
                    Contacts.add(addableContacts.get(i));
                }

                if (!addableContacts.isEmpty()){
                    populateList();
                }
                break;
            case 1:
                List<Contact> addableContactsc=dbHandler.getGContact("CoWorkers");
                int contactCountc = dbHandler.getGCount("CoWorkers");

                for (int i = 0; i < contactCountc; i++){
                    Contacts.add(addableContactsc.get(i));
                }

                if (!addableContactsc.isEmpty()){
                    populateList();
                }
                break;
            case 2:
                List<Contact> addableContactsf=dbHandler.getGContact("Friends");
                int contactCountf = dbHandler.getGCount("Friends");

                for (int i = 0; i < contactCountf; i++){
                    Contacts.add(addableContactsf.get(i));
                }

                if (!addableContactsf.isEmpty()){
                    populateList();
                }
                break;
        }
       // finish();
    }

    public void populateList(){
        adapter=new ContactListAdapter();
        lv.setAdapter(adapter);
    }


    private class ContactListAdapter extends ArrayAdapter<Contact> {
        Context mContext;
        LayoutInflater inflater;
        private   List<Contact> contact=null;
        private ArrayList<Contact> arrayList;
        public ContactListAdapter(){

            super(Groupby.this, R.layout.listview_item, Contacts);
            //mContext=context;
            //inflater = LayoutInflater.from(mContext);
            //this.contact=contact;
            //this.arrayList=new ArrayList<Contact>();
            // this.arrayList.addAll(contact);
        }
       /* public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            contact.clear();
            if (charText.length() == 0) {
                contact.addAll(arrayList);
            } else {
                for (Contact wp : arrayList) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        contact.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }*/

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);



            Contact currentContact = Contacts.get(position);
            TextView name = (TextView) view.findViewById(R.id.textContactName);
            name.setText(currentContact.getName());

            TextView phone = (TextView) view.findViewById(R.id.textContactPhone);
            phone.setText(currentContact.getPhone());

            TextView email = (TextView)  view.findViewById(R.id.textContactEmail);
            email.setText(currentContact.getEmail());

            TextView address = (TextView) view.findViewById(R.id.textContactAddress);
            address.setText(currentContact.getAddress());
            TextView group=(TextView) view.findViewById(R.id.textContactGroup);
            group.setText(currentContact.getGroup());

            CircularImageView ivContactImage = (CircularImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setBorderWidth(1);
            ivContactImage.addShadow();
            // System.out.print("m"+check);
            //  Toast.makeText(MainActivity.this,"m"+check,Toast.LENGTH_LONG).show();

            ivContactImage.setImageURI(currentContact.getImageURI());

            // ivContactImage.setImageDrawable(getResources().getDrawable(R.drawable.user));


            return view;
        }
    }
}
