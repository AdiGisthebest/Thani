package adi.app.thani;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    int i = 0;
    ArrayAdapter adapter;
    LinkedList<String> recordList = new LinkedList<String>();
    HashMap<String,Integer> bpm;
    HashMap<String,String> talam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bpm = Readfromfile.read(this).bpm;
        talam = Readfromfile.read(this).talam;
        recordList = Readfromfile.read(this).recorder;
        setContentView(R.layout.activity_main);
        i = Readfromfile.readInt(this);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,recordList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                PopupMenu menu = new PopupMenu(MainActivity.this,view);
                menu.getMenuInflater().inflate(R.menu.onlistclick,menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final String suffix = parent.getItemAtPosition(position).toString();
                        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + suffix + ".mp4";
                        switch((String)item.getTitle()) {
                            case "Play Audio":
                                System.out.println("Play Audio");
                                Intent play = new Intent(MainActivity.this,Play.class);
                                play.putExtra(Intent.EXTRA_TEXT,bpm.get(suffix));
                                play.putExtra(Intent.EXTRA_COMPONENT_NAME,suffix);
                                play.putExtra(Intent.EXTRA_INDEX,talam.get(suffix));
                                startActivity(play);
                            break;
                            case "Delete":
                                File thingToDel = new File(path);
                                if (thingToDel.delete()) {
                                    Toast.makeText(MainActivity.this,"File Successfully Deleted",Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(MainActivity.this,"File Deletion Failed",Toast.LENGTH_SHORT);
                                }
                                adapter.remove(suffix);
                                adapter.notifyDataSetChanged();
                            break;
                            case "Share":
                                System.out.println(path);
                                File file = new File(path);
                                Uri fileuri = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file);
                                Intent data = new Intent();
                                List<ResolveInfo> resInfoList = MainActivity.this.getPackageManager().queryIntentActivities(data, PackageManager.MATCH_DEFAULT_ONLY);
                                for (ResolveInfo resolveInfo : resInfoList) {
                                    String packageName = resolveInfo.activityInfo.packageName;
                                    MainActivity.this.grantUriPermission(packageName, fileuri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                }
                                data.setAction(Intent.ACTION_SEND);
                                data.putExtra(Intent.EXTRA_STREAM,fileuri);
                                data.setType("video/mp4");
                                startActivity(Intent.createChooser(data,null));
                            break;
                            case "Rename":
                                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popview = inflater.inflate(R.layout.rename, null);
                                final PopupWindow pop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                Button button = popview.findViewById(R.id.button);
                                final EditText name = popview.findViewById(R.id.editText3);
                                name.setText(suffix);
                                final int bpmattr = bpm.get(suffix);
                                bpm.remove(suffix);
                                final String talamattr = talam.get(suffix);
                                talam.remove(suffix);
                                pop.setFocusable(true);
                                pop.showAtLocation(view, Gravity.CENTER, 50, 50);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pop.dismiss();
                                        File file1 = new File(path);
                                        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + name.getText().toString() + ".mp4");
                                        file1.renameTo(file2);
                                        bpm.put(name.getText().toString(), bpmattr);
                                        talam.put(name.getText().toString(), talamattr);
                                        adapter.remove(suffix);
                                        adapter.add(name.getText().toString());
                                    }
                                });
                        }
                        return false;
                    }
                });
            }
        });
        listView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MainActivity.this,v);
                menu.getMenuInflater().inflate(R.menu.popup,menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent hi = new Intent(MainActivity.this,Record.class);
                        hi.putExtra(Intent.EXTRA_TEXT,item.getTitle());
                        String[] hi17 = new String[0];
                        hi.putExtra(Intent.EXTRA_REFERRER, recordList.toArray(hi17));
                        hi.putExtra(Intent.EXTRA_INDEX,i);
                        startActivityForResult(hi,0);
                        return false;
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Datatofile.fileWrite(recordList, bpm, talam, this, i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                i = data.getIntExtra(Intent.EXTRA_TEXT, 0);
                int bpmItem = data.getIntExtra(Intent.ACTION_PACKAGE_REMOVED, 0);
                String talamItem = data.getStringExtra(Intent.EXTRA_PACKAGE_NAME);
                String name = data.getStringExtra(Intent.EXTRA_STREAM);
                boolean override = data.getBooleanExtra(Intent.EXTRA_REFERRER_NAME, false);
                if (override) {
                    adapter.remove(name);
                }
                bpm.put(name,bpmItem);
                talam.put(name,talamItem);
                recordList.add(name);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
