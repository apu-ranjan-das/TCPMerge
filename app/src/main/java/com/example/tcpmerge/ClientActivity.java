package com.example.tcpmerge;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tcpmerge.roomdatabase.SocketData;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {
    //Spinner
    String[] deviceIP;
    private Spinner spinner;
    private TextView textView;
    private boolean isFirstSelection = true;


    private WebSocketClient webSocketClient;
    private ArrayList<String> messages;
    private ArrayAdapter<String> adapter;
    List<String> values;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //Spinner
        deviceIP = getResources().getStringArray(R.array.device_ip);
        spinner = findViewById(R.id.spinnerID);
        textView = findViewById(R.id.spinnerTextID);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.sample_view, R.id.textviewSampleId, deviceIP);
        spinner.setAdapter(adapter2);


        values=new ArrayList<>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("isFirstSelection", "onItemSelected: ----> " +spinner.getSelectedItem());

                if (spinner.getSelectedItem().toString().equalsIgnoreCase("Select IP")){

                }else {

                    if (!values.contains(spinner.getSelectedItem().toString())) {
                        values.add(spinner.getSelectedItem().toString());
                        textView.setText(values.toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        messages = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        ListView listView = findViewById(R.id.messages);
        listView.setAdapter(adapter);


        EditText messageInput = findViewById(R.id.message);
        Button sendButton = findViewById(R.id.send_button);

        try {
            webSocketClient = new WebSocketClient(new URI("ws://192.168.100.1:5555")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    runOnUiThread(() -> {
                        messages.add("Connected");
                        adapter.notifyDataSetChanged();

                    });
                }

                @Override
                public void onMessage(String message) {
                    runOnUiThread(() -> {
                        //messages.add("Server: " + message);
                        messages.add("Message: " + message);
                        adapter.notifyDataSetChanged();
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    runOnUiThread(() -> {
                        messages.add("Disconnected");
                        adapter.notifyDataSetChanged();
                    });
                }

                @Override
                public void onError(Exception ex) {
                    runOnUiThread(() -> {
                        messages.add("Error: " + ex.getMessage());
                        adapter.notifyDataSetChanged();
                    });
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        sendButton.setOnClickListener(v -> {
            try {
                String message = messageInput.getText().toString();

//                JSONObject messageJson = new JSONObject();


                SocketData socketData = new SocketData(values, message);

                //Data class to Json


                if (!message.isEmpty()) {
                    webSocketClient.send(new Gson().toJson(socketData));
                    messageInput.setText("");
                    runOnUiThread(() -> {
                        messages.add("Client: " + message);
                        adapter.notifyDataSetChanged();
                    });
                }
            } catch (Exception e) {
                Toast.makeText(ClientActivity.this, "Please connect the device with Server!", Toast.LENGTH_SHORT).show();
            }

        });


    }//On Create

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocketClient.close();
    }
}