package com.example.barberr;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barberr.custom_adapters.RecyclerItemClickListener;
import com.example.barberr.custom_adapters.shop_imgs_adapter;
import com.example.barberr.userdetails.Shop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ownerefragmentprofile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ownerefragmentprofile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AlertDialog alertDialog,alertDialog2;
    AlertDialog.Builder alertDialogBuilder;


    private static final int pick_image=1;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage Storage;
    StorageReference reference;

    Button logoutbtn,deletebtn,re_authsendmailbtn,add_shop_service_image_btn_alertbox,add_shop_images_btn;
    ImageView profileimg,profileimg2,profileimg3;
    private Activity activity;
    EditText editshopname,editownername,editpassword,editaddress,editmobile,editmail,reath_password,reauth_mail;
    ImageButton browsebtn,editbutton,savebutton,re_authcancelbtn,cancelbtn_addshopimg_alertbox,seeallimages_imagebtn;
    ActivityResultLauncher<String> launcher;
    ProgressDialog progressDialog;
    ProgressBar Loadimg;
    TextView changepassword;
    View re_authbox,add_shop_pics_alertbox;
    AlertDialog.Builder alertDialogbuilder,alertdialogBuilder2;
    AlertDialog resetmailbox;
    RecyclerView shop_services_list_in_alertbox,shop_images_list_in_alertbox;
    CircleImageView owner_profile_pic;
    String selected_btn_in_alertbox;
    String ref;

    shop_imgs_adapter adapter,adapter2;
    static int i,j;
    private Uri Image_Uri;
    ArrayList<Uri> Shop_images_uri,Shop_services_uri;
    //this is for changing shops email and password

    String mail;

    public Ownerefragmentprofile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ownerefragmentprofile.
     */
    // TODO: Rename and change types and number of parameters
    public static Ownerefragmentprofile newInstance(String param1, String param2) {
        Ownerefragmentprofile fragment = new Ownerefragmentprofile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Getting Info...");
        progressDialog.setMessage("Take a Sip..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


      mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ownerefragmentprofile, container, false);

        LayoutInflater li = LayoutInflater.from(getContext());
        re_authbox = li.inflate(R.layout.re_authbox, null);
        alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        alertDialogBuilder
                .setCancelable(false);
        alertDialogBuilder.setView(re_authbox);

        alertDialog = alertDialogBuilder.create();

        re_authcancelbtn = (ImageButton) re_authbox.findViewById(R.id.reauth_cancelbtn);
        re_authsendmailbtn = (Button) re_authbox.findViewById(R.id.reauth_sendmailbtn);
        reath_password = (EditText) re_authbox.findViewById(R.id.reauth_password);
        reauth_mail = (EditText) re_authbox.findViewById(R.id.reauth_mail);


        logoutbtn= (Button) view.findViewById(R.id.logoutbtn);
       deletebtn= (Button) view.findViewById(R.id.deletebtnn);


        editbutton= (ImageButton) view.findViewById(R.id.editbutton);
        savebutton=(ImageButton)view.findViewById(R.id.savebtn);

        editshopname=(EditText) view.findViewById(R.id.editname);

        editownername=(EditText) view.findViewById(R.id.editownername);
        editmail=(EditText) view.findViewById(R.id.editmail);

        seeallimages_imagebtn=view.findViewById(R.id.seeall_images_btn);

        editaddress=(EditText) view.findViewById(R.id.editaddress);
        editmobile=(EditText) view.findViewById(R.id.editmobile);
       // editpassword=(EditText) view.findViewById(R.id.editpassword);
        profileimg=(ImageView)view.findViewById(R.id.profile_image1);
        profileimg2=(ImageView)view.findViewById(R.id.profile_image2);
        profileimg3=(ImageView)view.findViewById(R.id.profile_image3);

       // profileimg.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        changepassword=(TextView) view.findViewById(R.id.changepassword_owner_profile);


Shop_images_uri=new ArrayList<>();
Shop_services_uri=new ArrayList<>();



        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        add_shop_pics_alertbox = li.inflate(R.layout.add_shopimages_alertbox, null);
        alertdialogBuilder2 = new AlertDialog.Builder(
                getContext());
        alertdialogBuilder2
                .setCancelable(false);
        alertdialogBuilder2.setView(add_shop_pics_alertbox);
        alertDialog2=alertdialogBuilder2.create();






        seeallimages_imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog2.show();
            }
        });


        shop_services_list_in_alertbox=add_shop_pics_alertbox.findViewById(R.id.shopservicesview_recycler);
        shop_images_list_in_alertbox=add_shop_pics_alertbox.findViewById(R.id.shopimagesview_recycler);
        add_shop_service_image_btn_alertbox=add_shop_pics_alertbox.findViewById(R.id.add_shop_service_img_button);
        add_shop_images_btn=add_shop_pics_alertbox.findViewById(R.id.add_shopimg_button);
        cancelbtn_addshopimg_alertbox=add_shop_pics_alertbox.findViewById(R.id.addimg_alertboc_cancelbtn);
        browsebtn=add_shop_pics_alertbox.findViewById(R.id.browseimg_add_images_alertbox);
        Loadimg = (ProgressBar) add_shop_pics_alertbox.findViewById(R.id.Loadimg_add_images_alertbox);
        owner_profile_pic=add_shop_pics_alertbox.findViewById(R.id.owner_profile_image);


cancelbtn_addshopimg_alertbox.setVisibility(View.VISIBLE);

      //  update_shop_image_list();

        browsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
                startActivityForResult(intent,pick_image);
                selected_btn_in_alertbox="browse_owner_pic";
            }
        });
        add_shop_images_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
                startActivityForResult(intent,pick_image);
                selected_btn_in_alertbox="add_shop_images_btn";
            }
        });
        add_shop_service_image_btn_alertbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
                startActivityForResult(intent,pick_image);
                selected_btn_in_alertbox="add_shop_service_image_btn_alertbox";
            }
        });


        Task<DataSnapshot> ds= database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_profile_pic").get();
       ds.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
           @Override
           public void onSuccess(DataSnapshot dataSnapshot) {
               String owner_pic=dataSnapshot.getValue(String.class);
               Picasso.get().load(owner_pic).into(owner_profile_pic);
           }
       });


        database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_Servces_Images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int firstshowed3imges=1;
                Log.d("PPOO","7");
                Shop_services_uri.clear();
                i=(int)snapshot.getChildrenCount();
                Log.d("TPPP in data change","i="+i);
                if(i!=0) {

                    Log.d("PPOO","8");
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        if(i>=3) {
                            if (firstshowed3imges <= 3) {
                                if (firstshowed3imges == 1) {
                                    Picasso.get().load(postSnapshot.getValue(String.class)).into(profileimg);
                                }
                                if (firstshowed3imges == 2) {
                                    Picasso.get().load(postSnapshot.getValue(String.class)).into(profileimg2);
                                }
                                if (firstshowed3imges == 3) {
                                    Picasso.get().load(postSnapshot.getValue(String.class)).into(profileimg3);
                                }
                                firstshowed3imges++;
                            }
                        }
                        String img = postSnapshot.getValue(String.class);
                        Shop_services_uri.add(Uri.parse(img));
                        Log.d("PPOO","chedk="+Uri.parse(img));
                    }
                    Log.d("PPOO","10");
                    Log.d("TRTR", "imgs size in ondatachange =" + Shop_services_uri.size());

                }else{
                    Log.d("PPOO","11");
                    alertDialog2.show();

                }

                Log.d("PPOO","12");
                adapter = new shop_imgs_adapter(Shop_services_uri, getContext());
                shop_services_list_in_alertbox.setAdapter(adapter);
                shop_services_list_in_alertbox.setLayoutManager(new GridLayoutManager(getContext(),2));
                progressDialog.dismiss();
                Log.d("PPOO","13");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TRTR", "errrrorrrrrrrrrrrrrrrrrr");
            }
        });
        database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Shop_images_uri.clear();
                j=(int)snapshot.getChildrenCount();
                if(j!=0) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String img = postSnapshot.getValue(String.class);
                        Shop_images_uri.add(Uri.parse(img));
                        Log.d("PPpp","9");
                    }
                    Log.d("PPpp","10");
                    Log.d("PPpp", "imgs size of shopimg in ondatachange =" + Shop_images_uri.size());

                }else{
                    Log.d("PPpp","11");
                    alertDialog2.show();

                }

                Log.d("PPpp","12");
                adapter2 = new shop_imgs_adapter(Shop_images_uri, getContext());
                shop_images_list_in_alertbox.setAdapter(adapter2);
                shop_images_list_in_alertbox.setLayoutManager(new GridLayoutManager(getContext(),2));
                progressDialog.dismiss();
                Log.d("PPpp","13");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TRTR", "errrrorrrrrrrrrrrrrrrrrr");
            }
        });
        cancelbtn_addshopimg_alertbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i<5||j<3){
                    Toast.makeText(getContext(),"Upload minimum 5 services images and 3 shop images",Toast.LENGTH_LONG).show();
                }else{
                    alertDialog2.dismiss();
                }

            }
        });

        shop_services_list_in_alertbox.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), shop_services_list_in_alertbox, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"Image selected i= "+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

                alertDialogBuilder=new AlertDialog.Builder(getContext()).setTitle("Delete Image")
                        .setMessage("Are You sure you want to delete this Image ? ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.d("TPPP","i="+position);


                                FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_Servces_Images").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                                            Log.d("PPpp","childsnapforservices="+childSnapshot.getValue(String.class));
                                            if(childSnapshot.getValue(String.class).equals(Shop_services_uri.get(position).toString())){
                                                ref=childSnapshot.getRef().toString().replace("https://barberr-bb08f-default-rtdb.firebaseio.com/Shops/"+mAuth.getCurrentUser().getUid()+"/Images/Shop_Servces_Images/","");
                                               ref=ref.replace("25","");
                                                Log.d("PPpp","ref==="+ref+"\nlistval[pos]=");
                                            }
                                        }
                                        Log.d("TPPP","REFFF=="+ref);
                           FirebaseStorage.getInstance().getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid()).child(ref).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_Servces_Images").child(ref).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void unused) {
                                           Toast.makeText(getContext(),"Image deleted successfully",Toast.LENGTH_LONG).show();
                                       }
                                   });
                               }
                           })     ;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }
        }));
        shop_images_list_in_alertbox.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), shop_images_list_in_alertbox, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"Image selected i= "+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

                alertDialogBuilder=new AlertDialog.Builder(getContext()).setTitle("Delete Image")
                        .setMessage("Are You sure you want to delete this Image ? ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.d("TPPP","i="+position);


                                FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_images").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                                            Log.d("PPpp","childsnapforservices="+childSnapshot.getValue(String.class));
                                            if(childSnapshot.getValue(String.class).equals(Shop_images_uri.get(position).toString())){
                                                ref=childSnapshot.getRef().toString().replace("https://barberr-bb08f-default-rtdb.firebaseio.com/Shops/"+mAuth.getCurrentUser().getUid()+"/Images/Shop_images/","");
                                                ref=ref.replace("25","");
                                                Log.d("PPpp","ref==="+ref+"\nlistvalofshopimag[pos]=");
                                            }
                                        }
                                        Log.d("TPPP","REFFF=="+ref);
                                        FirebaseStorage.getInstance().getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid()).child(ref).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_images").child(ref).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getContext(),"Image deleted successfully",Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        })     ;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }
        }));


        database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shop ownerdetail = snapshot.getValue(Shop.class);
                Log.d("aayege","111111");
                assert ownerdetail != null;
                editshopname.setText(ownerdetail.getShop_name());
                editownername.setText(ownerdetail.getOwner_name());
                editaddress.setText(ownerdetail.getShop_address());
                editaddress.setMaxLines(5);

                editmail.setText(ownerdetail.getShop_mail());
                mail=editmail.getText().toString();
                Log.d("TAGGGGG",mail);
                editmobile.setText(ownerdetail.getShop_mobile_no());
              //  Picasso.get().load(Uri.parse(ownerdetail.getShop_profile_pic())).into(profileimg);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();



                        progressDialog.dismiss();

                        editshopname.setEnabled(false);
                        editmail.setEnabled(false);
                        editownername.setEnabled(false);
                        editaddress.setEnabled(false);
                       // editpassword.setEnabled(false);
                        editmobile.setEnabled(false);
                    }
                }, 0);   //5 seconds


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        re_authcancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
        re_authsendmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reauth_mail.getText().toString().equals("") && !reath_password.getText().toString().equals("")) {
                    alertDialog.dismiss();
                    mail = reauth_mail.getText().toString();
                    String password = reath_password.getText().toString();
                    mAuth=FirebaseAuth.getInstance();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(mail, password);

                    Objects.requireNonNull(mAuth.getCurrentUser()).reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Log.d("TAGkk", "User re-authenticated.");
                                        mAuth.sendPasswordResetEmail(editmail.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("TAGkk", "Email sent.");

                                                            progressDialog.setTitle("please Sign-in again...");
                                                            progressDialog.setMessage("mail has been sent.. , checkout inbox");
                                                            progressDialog.setCancelable(false);
                                                            progressDialog.show();

                                                            Handler handler = new Handler();
                                                            handler.postDelayed(new Runnable() {
                                                                public void run() {
                                                                    // yourMethod();

                                                                    progressDialog.dismiss();
                                                                    mAuth.signOut();
                                                                    startActivity(new Intent(getContext(), Ownerlogin.class));
                                                                    getActivity().finish();

                                                                }
                                                            }, 3000);   //5 seconds


                                                        }else{
                                                            progressDialog.dismiss();
                                                            Toast.makeText(getContext(),"Password Updation Failed",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getContext(), "Invalid Credentials!!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                } else {
                    Toast.makeText(getContext(), "Enter all Fields!!", Toast.LENGTH_LONG).show();

                }
            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_name").setValue(editshopname.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_mail").setValue(editmail.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_mobile_no").setValue(editmobile.getText().toString());
               // database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_password").setValue(editpassword.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("owner_name").setValue(editownername.getText().toString());
                database.getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_address").setValue(editaddress.getText().toString());


                editshopname.setEnabled(false);
                editmail.setEnabled(false);
               // editpassword.setEnabled(false);
                editmobile.setEnabled(false);
                editownername.setEnabled(false);
                editaddress.setEnabled(false);

                FirebaseUser user = mAuth.getCurrentUser();

                if(!mail.equals(editmail.getText().toString())){


                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(editmail.getText().toString()).matches()) {

                        assert user != null;

                        Log.d("TAGkk editmail", editmail.getText().toString());
                        Log.d("TAGkk mail",mail);


                        alertDialogbuilder = new AlertDialog.Builder(getContext())
                                .setTitle("Reset Email")
                                .setMessage("Are you sure you want to Update your E-mail Address ? Press Ok if yes ")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(),"Profile Updated Successfully :)",Toast.LENGTH_SHORT).show();

                                        user.updateEmail(editmail.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("user_mail").setValue(editmail.getText().toString());
                                                            mAuth.signOut();
                                                            startActivity(new Intent(getContext(), Ownerlogin.class));
                                                            Toast.makeText(getContext(), "Sign-in Again", Toast.LENGTH_LONG).show();
                                                            getActivity().finish();
                                                        }else{
                                                            Toast.makeText(getContext(), "Email UPdation Failed", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });


                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        resetmailbox.dismiss();
                                        editmail.setText(mail);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        resetmailbox=alertDialogbuilder.create();
                        resetmailbox.show();
                    }else {
                        Toast.makeText(getContext(), " Email Format is Invalid", Toast.LENGTH_SHORT).show();
                        editmail.setText(mail);

                    }

                }
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editshopname.setEnabled(true);
                editmail.setEnabled(true);
               // editpassword.setEnabled(true);
                editmobile.setEnabled(true);
                editownername.setEnabled(true);
                editaddress.setEnabled(true);

                mail=editmail.getText().toString();
//                pass=editpassword.getText().toString();

                Toast.makeText(getContext(),"Data is in Edit Mode !!",Toast.LENGTH_SHORT).show();


            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Logging Out",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(getActivity(),Ownerlogin.class));
                getActivity().finish();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Deleting Your Account ");
                progressDialog.setMessage("Thank You for Visit");
                progressDialog.setCancelable(false);
                progressDialog.show();

                database.getReference().child("Shops").child(mAuth.getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        assert user != null;
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Account Deleted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getActivity(), Ownerlogin.class));
                                            getActivity().finish();
                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Account Deleted Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                });
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_image){
            if(resultCode==RESULT_OK) {
                Cursor returnCursor = getActivity().getContentResolver().query(data.getData(), null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                Long Image_size = (returnCursor.getLong(sizeIndex) / 1000);
                Log.d("TAGp", "Name:" + returnCursor.getString(nameIndex));
                Log.d("TAGp", "Size: " + Image_size);
                if(Image_size<=500){
                if(selected_btn_in_alertbox.equals("browse_owner_pic")){
                        Loadimg.setVisibility(View.VISIBLE);
                        browsebtn.setVisibility(View.GONE);
                    FirebaseStorage.getInstance().getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid()).child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseStorage.getInstance().getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid()).child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String img_uri = String.valueOf(uri);
                                    FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("shop_details").child("shop_profile_pic").setValue(img_uri);
                                    Toast.makeText(getActivity(), "Image uploaded successfully !!", Toast.LENGTH_LONG).show();
                                    Picasso.get().load(img_uri).into(owner_profile_pic);
                                    Loadimg.setVisibility(View.GONE);
                                    browsebtn.setVisibility(View.VISIBLE);

                                }
                            });
                        }
                    });
                }
                if(selected_btn_in_alertbox.equals("add_shop_service_image_btn_alertbox")){
                    progressDialog.setTitle("Uploading Image...");
                    progressDialog.setMessage("Take a Sip..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                //Toast.makeText(getActivity(),"Please select minimun 5 Images of your Shop and Services",Toast.LENGTH_LONG).show();
                Log.d("TRTR", "inactires else body i=" + i);

                Log.d("TRTRRRR", data.getData().toString());
                StorageReference Shop_Image_folder = FirebaseStorage.getInstance().getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid());

                Shop_Image_folder.child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Shop_Image_folder.child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String img_uri = String.valueOf(uri);
                                FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_Servces_Images").child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).setValue(img_uri);
                                i++;
                                Toast.makeText(getActivity(), "Image uploaded successfully !!", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                //    update_shop_image_list();
                            }
                        });
                    }
                });


            }
                if(selected_btn_in_alertbox.equals("add_shop_images_btn")){
                    progressDialog.setTitle("Uploading Image...");
                    progressDialog.setMessage("Take a Sip..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    //Toast.makeText(getActivity(),"Please select minimun 5 Images of your Shop and Services",Toast.LENGTH_LONG).show();
                    Log.d("TRTR", "inactires else body i=" + i);

                    Log.d("TRTRRRR", data.getData().toString());
                    StorageReference Shop_Image_folder = FirebaseStorage.getInstance().getReference().child("Shopimages").child(mAuth.getCurrentUser().getUid());

                    Shop_Image_folder.child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Shop_Image_folder.child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String img_uri = String.valueOf(uri);
                                    FirebaseDatabase.getInstance().getReference("Shops").child(mAuth.getCurrentUser().getUid()).child("Images").child("Shop_images").child(data.getData().toString().replace("content://com.android.providers.media.documents/document/", "")).setValue(img_uri);
                                    i++;
                                    Toast.makeText(getActivity(), "Image uploaded successfully !!", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    //    update_shop_image_list();
                                }
                            });
                        }
                    });

                }

                }
                else {
                    Toast.makeText(getContext(),"Image Size Must be Less than 500Kb!!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}