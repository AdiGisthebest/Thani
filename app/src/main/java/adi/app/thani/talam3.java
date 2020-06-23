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

public class talam3 extends Activity {
    Intent retval;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talam3);
        retval = new Intent();
        LinkedList<String> ekaList = new LinkedList<String>();
        ekaList.add("Khanda Jati Eka Talam");
        ekaList.add("Tisra Jati Eka Talam");
        ekaList.add("Misra Jati Eka Talam");
        ekaList.add("Chatusra Jati Eka Talam");
        ekaList.add("Sankeerna Jati Eka Talam");
        LinkedList<String> chapuList = new LinkedList<String>();
        chapuList.add("Khanda Chapu Talam");
        chapuList.add("Tisra Chapu Talam");
        chapuList.add("Misra Chapu Talam");
        chapuList.add("Sankeerna Chapu Talam");
        ListView eka = findViewById(R.id.Eka);
        ArrayAdapter<String> ekaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ekaList);
        eka.setAdapter(ekaAdapter);
        eka.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                retval.putExtra("Family", "Eka Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        ListView Chapu = findViewById(R.id.Chapu);
        ArrayAdapter<String> chapuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chapuList);
        Chapu.setAdapter(chapuAdapter);
        Chapu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String suffix = parent.getItemAtPosition(position).toString();
                String[] arr = suffix.split(" ");
                retval.putExtra("Length", 7);
                switch (arr[0]) {
                    case "Misra":
                        retval.putExtra("Length", 7);
                        break;
                    case "Tisra":
                        retval.putExtra("Length", 3);
                        break;
                    case "Khanda":
                        retval.putExtra("Length", 5);
                        break;
                    case "Chatusra":
                        retval.putExtra("Length", 4);
                        break;
                    case "Sankeerna":
                        retval.putExtra("Length", 9);
                        break;
                }
                retval.putExtra("Chapu", true);
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        FloatingActionButton prev = findViewById(R.id.floatingActionButton5);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, retval);
                retval.putExtra("finish", true);
                finish();
            }
        });
    }
}
