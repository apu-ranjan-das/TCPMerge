package com.example.tcpmerge.roomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.tcpmerge.R;
import com.example.tcpmerge.ServerActivity;

public class AddActivity extends AppCompatActivity {
    EditText t1,t2,t3,t4;
    Button b1;
   // Button b2;
    TextView lbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.serverButton).setOnClickListener(v -> {
            Intent intent = new Intent(AddActivity.this, ServerActivity.class);
            startActivity(intent);
        });



        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);

        b1=findViewById(R.id.b1);
       // b2=findViewById(R.id.b2);
        lbl=findViewById(R.id.lbl);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"cart_db").allowMainThreadQueries().build();
                ProductDao productDao=db.ProductDao();
                Boolean check=productDao.is_exist(t1.getText().toString());
                if(check==false)
                {
                    String pid=t1.getText().toString();
                    String pname=t2.getText().toString();
                    int price=Integer.parseInt(t3.getText().toString());
                    int qnt=Integer.parseInt(t4.getText().toString());
                    productDao.insertrecord(new Product(pid,pname,price,qnt));
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    lbl.setText("Device Inserted Successfully");
                }
                else
                {
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    lbl.setText("Device Already inserted.");
                }
            }
        });

        findViewById(R.id.b2).setOnClickListener(v -> {
            Intent intent = new Intent(AddActivity.this, NoteData.class);
            startActivity(intent);
        });

    }//On Create
}