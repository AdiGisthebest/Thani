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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    int i = 0;
    ArrayAdapter adapter;
    LinkedList<String> recordList = new LinkedList<String>();
    String path;
    HashMap<String, Boolean> audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordList = Readfromfile.read(this).recorder;
        setContentView(R.layout.activity_main);
        audio = Readfromfile.read(this).audio;
        i = Readfromfile.read(this).i;
        Button fab = findViewById(R.id.floatingActionButton);
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
                        if (audio.get(suffix)) {
                            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + suffix + ".mp3";
                        } else {
                            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + suffix + ".mp4";
                        }

                        switch ((String) item.getTitle()) {
                            case "Play":
                                Intent play = new Intent(MainActivity.this, Play.class);
                                play.putExtra(Intent.EXTRA_COMPONENT_NAME, suffix);
                                play.putExtra("Audio", audio.get(suffix));
                                startActivity(play);
                                break;
                            case "Delete":
                                File thingToDel = new File(path);
                                if (thingToDel.delete()) {
                                    if (audio.get(suffix)) {
                                        Toast.makeText(MainActivity.this, "Audio Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Video Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (audio.get(suffix)) {
                                        Toast.makeText(MainActivity.this, "Audio Deletion Failed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Video Deletion Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                audio.remove(suffix);
                                adapter.remove(suffix);
                                adapter.notifyDataSetChanged();
                            break;
                            case "Share":
                                File file = new File(path);
                                Uri fileuri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                                Intent data = new Intent();
                                List<ResolveInfo> resInfoList = MainActivity.this.getPackageManager().queryIntentActivities(data, PackageManager.MATCH_DEFAULT_ONLY);
                                for (ResolveInfo resolveInfo : resInfoList) {
                                    String packageName = resolveInfo.activityInfo.packageName;
                                    MainActivity.this.grantUriPermission(packageName, fileuri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                }
                                data.setAction(Intent.ACTION_SEND);
                                data.putExtra(Intent.EXTRA_STREAM, fileuri);
                                if (audio.get(suffix)) {
                                    data.setType("audio/mpeg3");
                                } else {
                                    data.setType("video/mp4");
                                }
                                startActivity(Intent.createChooser(data, null));
                                break;
                            case "Rename":
                                rename(suffix);
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
        try {
            Datatofile.fileWrite(recordList, this, audio, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                i = data.getIntExtra(Intent.EXTRA_TEXT, 0);
                String name = data.getStringExtra(Intent.EXTRA_STREAM);
                boolean override = data.getBooleanExtra(Intent.EXTRA_REFERRER_NAME, false);
                audio.put(name, data.getBooleanExtra("Audio", false));
                if (override) {
                    adapter.remove(name);
                }
                recordList.add(name);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void rename(final String suffix) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popview = inflater.inflate(R.layout.rename, null);
        final PopupWindow pop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = popview.findViewById(R.id.button);
        final EditText name = popview.findViewById(R.id.editText3);
        name.setText(suffix);
        pop.setFocusable(true);
        final boolean audioVal = audio.get(suffix);
        audio.remove(suffix);
        pop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordList.contains(name.getText().toString()) && !suffix.equals(name.getText().toString())) {
                    Toast.makeText(MainActivity.this, "This file already exists please enter a new name", Toast.LENGTH_SHORT).show();
                } else {
                    pop.dismiss();
                    File file1 = new File(path);
                    File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + name.getText().toString() + ".mp4");
                    file1.renameTo(file2);
                    audio.put(name.getText().toString(), audioVal);
                    adapter.remove(suffix);
                    adapter.add(name.getText().toString());
                }
            }
        });
    }
}
