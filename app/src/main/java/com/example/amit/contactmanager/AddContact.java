package com.example.amit.contactmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 17/9/16.
 */
public class AddContact extends AppCompatActivity {
    EditText nameText, phoneText, emailText, addressText;
    Button addContactBtn;
    ImageView contactImageImgView;
    Uri imageUri = Uri.parse("android.resource://com.example.amit.contactmanager/drawable/user");
   // Uri imageUri;
    DatabaseHandler dbHandler;
    List<Contact> Contacts = new ArrayList<Contact>();
    MainActivity mana;
    ListView contactListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontact);
        contactListView = (ListView) findViewById(R.id.listView);
        mana=new MainActivity();
        nameText = (EditText) findViewById(R.id.textName);
        phoneText = (EditText) findViewById(R.id.textPhone);
        emailText = (EditText) findViewById(R.id.textEmail);
        addressText = (EditText) findViewById(R.id.textAddress);
        contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());
        addContactBtn = (Button) findViewById(R.id.btnAddContact);
        //addContactBtn = (Button) findViewById(R.id.btnAddContact);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(nameText.getText()), String.valueOf(phoneText.getText()), String.valueOf(emailText.getText()), String.valueOf(addressText.getText()), imageUri);
                dbHandler.createContact(contact);
                Contacts.add(contact);
                populateList();
                Toast.makeText(getApplicationContext(), nameText.getText().toString() + " has been added to your contacts", Toast.LENGTH_SHORT).show();
            }
        });

        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addContactBtn.setEnabled(!nameText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        contactImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });

    }

    protected void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }
    public void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == 1 && resCode == RESULT_OK && null != data) {
            if (ContextCompat.checkSelfPermission(AddContact.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddContact.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},12345);
                return;
            }
            mana.check=1;
            System.out.print(mana.check);
            Toast.makeText(AddContact.this,"m"+mana.check,Toast.LENGTH_LONG).show();
            imageUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
            //ImageView imageView = (ImageView) findViewById(R.id.imgView);
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(imageUri);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            contactImageImgView.setImageBitmap(bmp);
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter(){
            super(AddContact.this, R.layout.listview_item, Contacts);
        }


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

            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI(currentContact.getImageURI());


            return view;
        }
    }
}
