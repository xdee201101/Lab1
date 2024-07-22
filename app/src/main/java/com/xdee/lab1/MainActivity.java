package com.xdee.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button btn_them, btn_sua, btn_xoa, btn_hien_thi;
    TextView tv_result;
    FirebaseFirestore database;
    String id = "";
    ToDo toDo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_them = findViewById(R.id.btn_them);
        btn_sua = findViewById(R.id.btn_sua);
        btn_xoa = findViewById(R.id.btn_xoa);
        btn_hien_thi = findViewById(R.id.btn_hien_thi);
        tv_result = findViewById(R.id.tv_result);
        database = FirebaseFirestore.getInstance();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertFirebase(tv_result);
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateFirebase(tv_result);
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFirebase(tv_result);
            }
        });
        btn_hien_thi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDataFromFirebase(tv_result);
            }
        });
    }

    public void InsertFirebase(TextView tv_result) {
        id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "title 1", "content 1");
        HashMap<String, Object> mapToDo = toDo.convertToHashMap();
        database.collection("Lab1")
                .document(id)
                .set(mapToDo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tv_result.setText("Thêm thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tv_result.setText(e.getMessage());
                    }
                });
    }

    public void UpdateFirebase(TextView tv_result) {
        id = "313a9909-63dc-4921-993f-c8ea9afcba8b";
        toDo = new ToDo(id, "title 2", "content 2");
        database.collection("Lab1")
                .document(toDo.getId())
                .update(toDo.convertToHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tv_result.setText("Sửa thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tv_result.setText(e.getMessage());
                    }
                });
    }

    public void DeleteFirebase(TextView tv_result) {
        id = "313a9909-63dc-4921-993f-c8ea9afcba8b";
        database.collection("Lab1")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tv_result.setText("Xóa thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tv_result.setText(e.getMessage());
                    }
                });
    }

    String strResult = "";

    public ArrayList<ToDo> SelectDataFromFirebase(TextView tv_result) {
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("Lab1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            strResult = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ToDo toDo1 = document.toObject(ToDo.class);
                                strResult += "ID = " + toDo1.getId() + "\n";
                                list.add(toDo1);
                            }
                            tv_result.setText(strResult);
                        } else {
                            tv_result.setText("Đọc dữ liệu thất bại");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tv_result.setText(e.getMessage());
                    }
                });
        return list;
    }
}