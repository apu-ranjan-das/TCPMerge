package com.example.tcpmerge;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tcpmerge.roomdatabase.SocketData;
import com.google.gson.Gson;

import java.util.ArrayList;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.List;

public class ServerActivity extends AppCompatActivity {
    private WebSocketServer webSocketServer;
    private ArrayList<String> messages;
    private ArrayAdapter<String> adapter;
    private List<WebSocket> connectedWebSocketList;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        messages = new ArrayList<>();
        connectedWebSocketList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        ListView listView = findViewById(R.id.messages);
        listView.setAdapter(adapter);


        EditText messageInput = findViewById(R.id.message);
        Button sendButton = findViewById(R.id.send_button);

        // Initialize WebSocket server
        webSocketServer = new WebSocketServer(new InetSocketAddress(5555)) {
            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
                runOnUiThread(() -> {
                    count++;
//                    Log.d("TAG", "onOpen: ----------->" + conn.getRemoteSocketAddress().getAddress().toString());
                    messages.add("Client Connected: " + count);
                    adapter.notifyDataSetChanged();


                    connectedWebSocketList.add(webSocket);

//                    if (conn.getRemoteSocketAddress() != null) {
//                        if (conn.getRemoteSocketAddress().getAddress().toString().contains("192.168.100.3")) {
//                            conn.send("Hello PRABAL DA");
//
//                        } else if (conn.getRemoteSocketAddress().toString().contains("192.168.100.2")) {
//                            conn.send("Hello APU");
//
//                        }
//                    }
                });
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                runOnUiThread(() -> {
                    count--;
                    connectedWebSocketList.remove(conn);

                    messages.add("Client Disconnected: " + count);
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                runOnUiThread(() -> {
                    messages.add("Client: " + message + " ");
                    adapter.notifyDataSetChanged();
                   // Log.d("TAG", "data: ------------>" + webSocketServer.getConnections().size());

                   // Log.d("TAG", "onMessage: --msg-->" + message);

                    try {

                        //Json to Data class

                        SocketData socketData = new Gson().fromJson(message, SocketData.class);


                        if (socketData != null) {
                         //   Log.d("TAG", "onMessage: ------>" + socketData.ipAddress + " " + socketData.message);

                            if (socketData.ipAddress != null) {
//                                connectedWebSocketList --- ip1 ip2 ip3    adresses- ip6,ip2,ip1

                                for (int i = 0; i < connectedWebSocketList.size(); i++) {

                                 //   Log.d("TAG", "onMessage: --out-->"+ connectedWebSocketList.get(i).getRemoteSocketAddress().getAddress().toString());

                                    if (socketData.ipAddress.contains(connectedWebSocketList.get(i).getRemoteSocketAddress().getAddress().toString().replace("/", ""))) {
                                        connectedWebSocketList.get(i).send(socketData.message);
                                      //  Log.d("TAG", "onMessage: --condi-->");
                                    }

                                }


                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    Log.d("TAG", "onMessage: ------getAddress------->" + getSocketByAddress(message, webSockets).getRemoteSocketAddress().getAddress());

//                    for (int i = 0; i < webSockets.size(); i++) {
//
//                        Log.d("TAG", "onMessage: ---ip ->" + webSockets.get(i).getRemoteSocketAddress().getAddress().toString() + "  ist " + webSockets.get(i).getRemoteSocketAddress().getAddress().toString().equalsIgnoreCase(message) + " " + message);
//                        if (webSockets.get(i).getRemoteSocketAddress().getAddress().toString().contains(message)) {
//
//                            Log.d("TAG", "onMessage: --------if");
//                            webSockets.get(i).send("Probal da valani?" + i);
//
//                        }
//
//
//                    }


//                    webSocketServer.broadcast(message);
//                    messageInput.setText("");
//                    runOnUiThread(() -> {
//                        messages.add("Server: " + message);
//                        adapter.notifyDataSetChanged();
//                    });
//Multi

                });
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                runOnUiThread(() -> {
                    messages.add("Error: " + ex.getMessage());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ServerActivity.this, "Enter after two minutes to start the Server again.", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onStart() {
                runOnUiThread(() -> {
                    messages.add("Server Started");
                    adapter.notifyDataSetChanged();
                });
            }
        };

        webSocketServer.start();

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                webSocketServer.broadcast(message);
                messageInput.setText("");
                runOnUiThread(() -> {
                    messages.add("Server: " + message);
                    adapter.notifyDataSetChanged();
                });
            }
        });

    }//On Create


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            webSocketServer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}