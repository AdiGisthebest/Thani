package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

class Readfromfile {
    public static Container read(Context context) {
        Container hi = new Container();
        String line;
        FileInputStream stream = null;
        ObjectInputStream data = null;
        File path = new File(context.getFilesDir().toString() + File.separator + "File1.txt");
        try {
            stream = new FileInputStream(new File(context.getFilesDir(), "Data.data"));
            data = new ObjectInputStream(stream);
            hi = (Container) data.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hi;
    }
}
