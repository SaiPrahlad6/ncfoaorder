package com.unity.ncfoaorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        button=findViewById(R.id.button);
        final ArrayList<String> item_names=new ArrayList<>();
        final String status_payment;
        final String status_cooking;
        final String[] d = new String[1];
        final String bill;
        final String table;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference=db.collection("requests").document(
                        "jrUDwxOKWQXGB9dbgvrA").collection("orders").document("BVjtdUCDbxkGNjpfXr4A");
                System.out.println("Reference"+documentReference);
                Task<DocumentSnapshot> documentSnapshot=documentReference.get();
                System.out.println("Document"+documentSnapshot);

                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Map<String,String>> items= (ArrayList<Map<String, String>>) value.get("items");

                        for(int i=0;i<items.size();i++){
                            System.out.println("Item no is "+i+1+" "+items.get(i));
                            HashMap<String,String> temp= (HashMap<String, String>) items.get(i);
                            item_names.add(temp.get("item_name"));
                        }

                        System.out.println("Ordered Items are "+item_names);
                        Timestamp order=value.getTimestamp("order_on");
                        System.out.println("Time is "+order);
                        Date date=new Date(order.getSeconds()*1000);
                        System.out.println("The Date is "+date);
                        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        String d1 = f.format(date);
                        d[0] =d1;
                        System.out.println("Today date is  "+ d[0]);
                        String status=value.getString("order_status");
                        System.out.println("Status is "+status);

                    }
                });
            }
        });

    }


}