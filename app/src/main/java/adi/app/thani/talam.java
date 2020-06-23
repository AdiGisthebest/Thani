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

public class talam extends Activity {
    Intent retval = new Intent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talam);
        LinkedList<String> ataList = new LinkedList<String>();
        ataList.add("Khanda Jati Ata Talam");
        ataList.add("Tisra Jati Ata Talam");
        ataList.add("Misra Jati Ata Talam");
        ataList.add("Chatusra Jati Ata Talam");
        ataList.add("Sankeerna Jati Ata Talam");
        LinkedList<String> triputaList = new LinkedList<String>();
        triputaList.add("Khanda Jati Triputa Talam");
        triputaList.add("Tisra Jati Triputa Talam");
        triputaList.add("Misra Jati Triputa Talam");
        triputaList.add("Chatusra Jati Triputa Talam");
        triputaList.add("Sankeerna Jati Triputa Talam");
        LinkedList<String> jhampaList = new LinkedList<String>();
        jhampaList.add("Khanda Jati Jhampa Talam");
        jhampaList.add("Tisra Jati Jhampa Talam");
        jhampaList.add("Misra Jati Jhampa Talam");
        jhampaList.add("Chatusra Jati Jhampa Talam");
        jhampaList.add("Sankeerna Jati Jhampa Talam");
        ListView Ata = findViewById(R.id.Ata);
        ArrayAdapter<String> ataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ataList);
        Ata.setAdapter(ataAdapter);
        Ata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                retval.putExtra("Family", "Ata Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        ListView Triputa = findViewById(R.id.Triputa);
        ArrayAdapter<String> triputaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, triputaList);
        Triputa.setAdapter(triputaAdapter);
        Triputa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                retval.putExtra("Family", "Triputa Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        ListView Jhampa = findViewById(R.id.Jhampa);
        ArrayAdapter<String> jhampaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jhampaList);
        Jhampa.setAdapter(jhampaAdapter);
        Jhampa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                retval.putExtra("Family", "Jhampa Talam");
                setResult(RESULT_OK, retval);
                finish();
            }
        });
        FloatingActionButton nextButton = findViewById(R.id.floatingActionButton8);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(talam.this, talam2.class);
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
