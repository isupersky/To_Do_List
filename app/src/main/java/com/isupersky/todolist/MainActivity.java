package com.isupersky.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> trial = new ArrayList<>();
    private ArrayAdapter<String> myAdapter= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,trial);
        ListView ls = findViewById(R.id.someList);
        ls.setAdapter(myAdapter);
        readFile();

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trial.remove(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Item deleted", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        writeFile();
    }

    public void buttonClick(View view){
        TextView tv = findViewById(R.id.textInput);
        String str = tv.getText().toString();
        if (str.length() != 0) {
            trial.add(str);
            myAdapter.notifyDataSetChanged();
            tv.setText("");
        }
        else
            Toast.makeText(this, "Please Enter Something to Add", Toast.LENGTH_SHORT).show();

    }

    private void readFile(){
        try {
            Scanner sc = new Scanner(openFileInput("index.txt"));
            while (sc.hasNext()) {
                trial.add(sc.nextLine());
            }
            myAdapter.notifyDataSetChanged();
        }
        catch(FileNotFoundException fnfe){
            Toast.makeText(this,"No previous data found",Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile(){
        //  File file = new File(getResources().openRawResource(R.raw.input).toString());
        try {
            PrintStream pw = new PrintStream(openFileOutput("index.txt",MODE_PRIVATE));
            for(int i =0; i <trial.size();i++){
                pw.println(trial.get(i));
            }
            pw.close();

        }
        catch(IOException ioe){
            Toast.makeText(this, "Error saving Data", Toast.LENGTH_SHORT).show();
        }
    }


}
