package com.example.amit.contactmanager;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    FloatingActionButton faba;
    CoordinatorLayout rootLayout;
    SearchView mSearchView;
    private boolean FAB_Status = false;
    protected Boolean isFabOpen = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    EditText nameText, phoneText, emailText, addressText;
    Button addContactBtn;
    List<Contact> Contacts = new ArrayList<Contact>();
    ArrayList<Contact> arrayList=new ArrayList<Contact>();
    ListView contactListView;
    int check;
    Boolean flag;
    String scheck;
    String srchrslt;
    SearchView sv;
    ContactListAdapter cl;
    ArrayAdapter<Contact> adapter;
    ImageView contactImageImgView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/user.png");

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setPadding(0,getStatusBarHeight(),0,0);
        contactListView = (ListView) findViewById(R.id.listView);
        //contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
        rootLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        /* SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);*/
        dbHandler = new DatabaseHandler(getApplicationContext());
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
         flag= Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT?true:false;
       // setStatusBarTranslucent(flag);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
      //  actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
       // sv=(SearchView) findViewById(R.id.searchView1);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3=(FloatingActionButton) findViewById(R.id.fab3);
       // faba=(FloatingActionButton)findViewById(R.id.faba);
        show_fab_1 = AnimationUtils.loadAnimation(this.getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(this.getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(this.getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(this.getApplication(), R.anim.fab2_hide);
         contactListView.setOnTouchListener(new View.OnTouchListener(){

             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 if(isFabOpen)
                 animateFAB();
                 /*if (FAB_Status) {
                     hideFAB();
                     FAB_Status = false;
                 }*/
                 return false;
             }
         });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  animateFAB();
              /*  if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }*/
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "Floating Action Button 1", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,Groupby.class);
                i.putExtra("grpid",2);
                startActivity(i);
               // animateFAB();
               // Toast.makeText(MainActivity.this, "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "Floating Action Button 1", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,Groupby.class);
                i.putExtra("grpid",1);
                startActivity(i);
               // animateFAB();
               // Toast.makeText(MainActivity.this, "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,Groupby.class);
                i.putExtra("grpid",0);
                startActivity(i);
                //animateFAB();
                //Toast.makeText(MainActivity.this, "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });
       /* sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                // TODO Auto-generated method stub
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });*/

     /*   addContactBtn = (Button) findViewById(R.id.btnAddContact);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(nameText.getText()), String.valueOf(phoneText.getText()), String.valueOf(emailText.getText()), String.valueOf(addressText.getText()), imageUri);
                dbHandler.createContact(contact);
                Contacts.add(contact);
                populateList();
                Toast.makeText(getApplicationContext(), nameText.getText().toString() + " has been added to your contacts", Toast.LENGTH_SHORT).show();
            }
        });*/
//       scheck=nameText.getText().toString();
//        srchrslt=dbHandler.check(scheck);
      /* nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {
               // MainActivity.this.adapter.getFilter().filter(cs);
                String text = nameText.getText().toString().toLowerCase(Locale.getDefault());
                cl.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //addContactBtn.setEnabled(!nameText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
              //  String text = nameText.getText().toString().toLowerCase(Locale.getDefault());
              //  cl.filter(text);

            }
        });*/

       /* contactImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }
        });*/


        List<Contact> addableContacts = dbHandler.getAllContacts();
        int contactCount = dbHandler.getContactsCount();

        for (int i = 0; i < contactCount; i++){
            Contacts.add(addableContacts.get(i));
            arrayList.add(addableContacts.get(i));
        }

        if (!addableContacts.isEmpty()){
            populateList();
        }
    }
    public void animateFAB(){

        if(isFabOpen){

           // fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
           // Log.d("Raj", "close");

        } else {

          //  fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
           // Log.d("Raj","open");

        }
    }
    private void sort(){

    }


    /*protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }*/

    public int getStatusBarHeight(){
        int result=0;
        int resourceId=getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId>0){
            result=getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    protected void populateList(){
         adapter = new ContactListAdapter();
       // cl=new ContactListAdapter(MainActivity.this,arrayList);
        contactListView.setAdapter(adapter);
      //  contactListView.setAdapter(cl);
       /* nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {
                // MainActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //addContactBtn.setEnabled(!nameText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = nameText.getText().toString().toLowerCase(Locale.getDefault());
                cl.filter(text);

            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

       // MenuItem searchItem = menu.findItem(R.id.add);

        return super.onCreateOptionsMenu(menu);
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       switch (item.getItemId())
        {
         case R.id.add:
         Intent i=new Intent(MainActivity.this,AddContact.class);
         startActivity(i);
         return true;
         default:
            return super.onOptionsItemSelected(item);
         }
    }

  /*  private void setupSearchView() {
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;

    }*/


//    private void addContact(String name, String phone, String email, String address){
//        Contacts.add(new Contact(name, phone, email, address));
//    }


    private class ContactListAdapter extends ArrayAdapter<Contact> {
        Context mContext;
        LayoutInflater inflater;
      private   List<Contact> contact=null;
        private ArrayList<Contact> arrayList;
        public ContactListAdapter(){

            super(MainActivity.this, R.layout.listview_item, Contacts);
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

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);
    }
    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);
       }
}