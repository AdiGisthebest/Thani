package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.ListIterator;

class Datatofile {
    public static void fileWrite(LinkedList<String> ll, Context context, Integer i) {
        try {
            File file;
            File file1;
            FileWriter fw;
            FileWriter fw2;
            file = new File(context.getFilesDir(), "File1.txt");
            file.delete();
            file = new File(context.getFilesDir(), "File1.txt");
            file1 = new File(context.getFilesDir(), "Num.txt");
            file1.delete();
            file1 = new File(context.getFilesDir(), "Num.txt");
            fw2 = new FileWriter(file1, true);
            fw2.append(i.toString());
            fw2.flush();
            fw2.close();
            fw = new FileWriter(file, true);
            ListIterator iter = ll.listIterator();
            while (iter.hasNext()) {
                String curr = iter.next().toString();
                fw.append(curr + "\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean contains(String[] arr, String contain) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(contain)) {
                return true;
            }
        }
        return false;
    }
}
