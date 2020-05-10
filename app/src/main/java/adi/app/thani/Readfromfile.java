package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

class Readfromfile {
    public static LinkedList<String> read(Context context) {
        LinkedList<String> retval = new LinkedList<String>();
        try {
            String line;
            int preserve = 0;
            File path = new File(context.getFilesDir().toString() + File.separator + "File1.txt");
            Scanner scan = new Scanner(path);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                retval.add(line);
            }
        } catch (IOException e) {
        }
        return retval;
    }

    public static int readInt(Context context) {
        int retval = 0;
        File intFile = new File(context.getFilesDir(), "Num.txt");
        try {
            Scanner scan = new Scanner(intFile);
            String retInt = scan.nextLine();
            retval = Integer.parseInt(retInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }
}
