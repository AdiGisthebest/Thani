package adi.app.thani;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class talam2 extends Activity {
    Intent retval;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talam2);
        retval = new Intent();
        LinkedList<String> rupakaList = new LinkedList<String>();
        rupakaList.add("Khanda Jati Rupaka Talam");
        rupakaList.add("Tisra Jati Rupaka Talam");
        rupakaList.add("Misra Jati Rupaka Talam");
        rupakaList.add("Chatusra Jati Rupaka Talam");
        rupakaList.add("Sankeerna Jati Rupaka Talam");
        LinkedList<String> dhruvaList = new LinkedList<String>();
        dhruvaList.add("Khanda Jati Dhruva Talam");
        dhruvaList.add("Tisra Jati Dhruva Talam");
        dhruvaList.add("Misra Jati Dhruva Talam");
        dhruvaList.add("Chatusra Jati Dhruva Talam");
        dhruvaList.add("Sankeerna Jati Dhruva Talam");
        LinkedList<String> matyaList = new LinkedList<String>();
        matyaList.add("Khanda Jati Matya Talam");
        matyaList.add("Tisra Jati Matya Talam");
        matyaList.add("Misra Jati Matya Talam");
        matyaList.add("Chatusra Jati Matya Talam");
        matyaList.add("Sankeerna Jati Matya Talam");
        ListView rupaka = findViewById(R.id.Rupaka);
        ArrayAdapter<String> rupakaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rupakaList);
        rupaka.setAdapter(rupakaAdapter);
        rupaka.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String suffix = parent.getItemAtPosition(position).toString();
                String[] arr = suffix.split(" ");
                switch (arr[0]) {
                    case "Misra":
                        retval.putExtra("Jati", 7);
                        break;
                    case "Tisra":
                        retval.putExtra("Jati", 3);
                        break;
                    case "Khanda":
                        retval.putExtra("Jati", 5);
                        break;
                    case "Chatusra":
                        retval.putExtra("Jati", 4);
                        break;
                    case "Sankeerna":
                        retval.putExtra("Jati", 9);
                        break;
                }
                retval.putExtra("Family", "Rupaka Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        ListView Dhruva = findViewById(R.id.Dhruva);
        ArrayAdapter<String> dhruvaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dhruvaList);
        Dhruva.setAdapter(dhruvaAdapter);
        Dhruva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String suffix = parent.getItemAtPosition(position).toString();
                String[] arr = suffix.split(" ");
                System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                retval.putExtra("Jati", 7);
                switch (arr[0]) {
                    case "Misra":
                        retval.putExtra("Jati", 7);
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        break;
                    case "Tisra":
                        retval.putExtra("Jati", 3);
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        break;
                    case "Khanda":
                        retval.putExtra("Jati", 5);
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        break;
                    case "Chatusra":
                        retval.putExtra("Jati", 4);
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        break;
                    case "Sankeerna":
                        retval.putExtra("Jati", 9);
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        break;
                    default:
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                }
                retval.putExtra("Family", "Dhruva Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        ListView Matya = findViewById(R.id.Matya);
        ArrayAdapter<String> matyaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matyaList);
        Matya.setAdapter(matyaAdapter);
        Matya.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String suffix = parent.getItemAtPosition(position).toString();
                String[] arr = suffix.split(" ");
                switch (arr[0]) {
                    case "Misra":
                        retval.putExtra("Jati", 7);
                        break;
                    case "Tisra":
                        retval.putExtra("Jati", 3);
                        break;
                    case "Khanda":
                        retval.putExtra("Jati", 5);
                        break;
                    case "Chatusra":
                        retval.putExtra("Jati", 4);
                        break;
                    case "Sankeerna":
                        retval.putExtra("Jati", 9);
                        break;
                }
                retval.putExtra("Family", "Matya Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        FloatingActionButton nextButton = findViewById(R.id.floatingActionButton6);
        FloatingActionButton prev = findViewById(R.id.floatingActionButton2);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, retval);
                retval.putExtra("finish", true);
                finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(talam2.this, talam3.class);
                startActivityForResult(startIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                if (!data.getBooleanExtra("finish", false)) {
                    System.out.println(data.getStringExtra("Family"));
                    if (data.getBooleanExtra("Chapu", false)) {
                        retval.putExtra("Chapu", true);
                        retval.putExtra("Length", data.getIntExtra("Length", 7));
                    } else {
                        retval.putExtra("Family", data.getStringExtra("Family"));
                        retval.putExtra("Jati", data.getIntExtra("Jati", 0));
                    }
                    setResult(RESULT_OK, retval);
                    finish();
                }
            }
        }
    }
}
