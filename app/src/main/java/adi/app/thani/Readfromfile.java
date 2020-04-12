package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class Readfromfile {
    public static Container read(Context context) {
        Container retval = new Container();
        SimpleDateFormat hi = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        Date duedate;
        try {
            String line;
            int preserve = 0;
            File path = new File(context.getFilesDir().toString() + File.separator + "File1.txt");
            Scanner scan = new Scanner(path);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] linearr = line.split(" ");
                retval.recorder.add(linearr[0]);
                retval.bpm.put(linearr[0], Integer.parseInt(linearr[1]));
                System.out.println(retval.bpm.get(linearr[0]) + " bpm");
                retval.talam.put(linearr[0], (linearr[2] + " " + linearr[3]));
                System.out.println(retval.talam.get(linearr[0]) + " talam");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return retval;
    }
}
