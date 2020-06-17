package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

class Datatofile {
    public static void fileWrite(LinkedList<String> ll, Context context, HashMap<String, Boolean> audio, Integer i) throws IOException {
        Container con = new Container();
        con.audio = audio;
        con.recorder = ll;
        con.i = i;
        FileOutputStream stream = null;
        ObjectOutputStream data = null;
        try {
            stream = new FileOutputStream(new File(context.getFilesDir(), "Data.data"));
            data = new ObjectOutputStream(stream);
            data.writeObject(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
            data.close();
        }
    }

    /*public HashMap<Integer,Integer> makeTalamMap(String TalamString){
        int addPos = 0;
        HashMap<Integer,Integer> retMap = new HashMap<Integer,Integer>();
        char [] talamArr = TalamString.toCharArray();
        for(int i = 0; i < talamArr.length; i++) {
            switch(talamArr[i]) {
                case 'L':
                    retMap.put(i,R.mipmap.realhand);

                break;
                case 'D':
                break;
                case 'A':
                break;
            }
        }
        return retMap;
    }*/
    public static boolean contains(String[] arr, String contain) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(contain)) {
                return true;
            }
        }
        return false;
    }
}
