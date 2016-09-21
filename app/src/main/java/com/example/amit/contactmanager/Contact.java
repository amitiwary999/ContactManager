package com.example.amit.contactmanager;

import android.net.Uri;

/**
 * Created by amit on 16/9/16.
 */
public class Contact {
    private String _name, _phone, _email, _address,_group;
    private Uri _imageURI;
    private int _id;

    public Contact(int id, String name, String phone, String email, String address, String group, Uri imageURI){
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _address = address;
        _group=group;
        _imageURI = imageURI;
    }

    public int getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getPhone(){
        return _phone;
    }

    public String getEmail(){
        return _email;
    }

    public String getAddress(){
        return _address;
    }
    public String getGroup(){
        return _group;
    }

    public Uri getImageURI(){
        return _imageURI;
    }
}
