package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

class Datatofile {
    public static void fileWrite(LinkedList<String> ll, HashMap<String, Integer> bpm, HashMap<String, String> talam, Context context) {
        try {
            File file;
            File file1;
            FileWriter fw;
            file1 = new File(context.getFilesDir(), "File1.txt");
            file1.delete();
            file = new File(context.getFilesDir(), "File1.txt");
            fw = new FileWriter(file, false);
            fw = new FileWriter(file, true);
            ListIterator iter = ll.listIterator();
            while (iter.hasNext()) {
                String curr = iter.next().toString();
                System.out.println(talam.get(curr) + " from stop");
                fw.append(curr + " " + bpm.get(curr).toString() + " " + talam.get(curr) + "\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
