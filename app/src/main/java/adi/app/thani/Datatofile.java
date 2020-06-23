package adi.app.thani;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

class Datatofile {
    int talamLen = 0;
    HashMap<Integer, Boolean> beats;

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

    public HashMap<Integer, Integer> makeTalamMap(String talamName, int jathi) {
        int addPos = 0;
        HashMap<String, String> talamMap = new HashMap<String, String>();
        talamMap.put("Triputa Talam", "LDD");
        talamMap.put("Rupaka Talam", "DL");
        talamMap.put("Dhruva Talam", "LDLL");
        talamMap.put("Eka Talam", "L");
        talamMap.put("Jhampa Talam", "LAD");
        talamMap.put("Matya Talam", "LDL");
        talamMap.put("Ata Talam", "LLDD");
        int[] jathis = {R.mipmap.realhand, R.mipmap.realpinky, R.mipmap.realring, R.mipmap.realmiddle, R.mipmap.index, R.mipmap.thumb, R.mipmap.realpinky, R.mipmap.realring, R.mipmap.realmiddle};
        int[] numbers = {R.mipmap.twentynine, R.mipmap.twentyeight, R.mipmap.twentyseven, R.mipmap.twentysix, R.mipmap.twentyfive, R.mipmap.twentyfour, R.mipmap.twentythree, R.mipmap.twentytwo, R.mipmap.twentyone, R.mipmap.twenty, R.mipmap.nineteen, R.mipmap.eighteen, R.mipmap.seventeen, R.mipmap.sixteen, R.mipmap.fifteen, R.mipmap.fourteen, R.mipmap.thirteen, R.mipmap.twelve, R.mipmap.eleven, R.mipmap.ten, R.mipmap.nine, R.mipmap.eight, R.mipmap.seven, R.mipmap.six, R.mipmap.five, R.mipmap.four, R.mipmap.three, R.mipmap.two, R.mipmap.one};
        HashMap<Integer, Integer> retMap = new HashMap<Integer, Integer>();
        String talamString = talamMap.get(talamName);
        char[] talamArr = talamString.toCharArray();
        System.out.println(talamString);
        for (int i = 0; i < talamArr.length; i++) {
            switch (talamArr[i]) {
                case 'L':
                    for (int j = 0; j < jathi; j++) {
                        retMap.put(addPos + j, jathis[j]);
                    }
                    addPos += jathi;
                    break;
                case 'A':
                    retMap.put(addPos, R.mipmap.realhand);
                    addPos++;
                    break;
                case 'D':
                    retMap.put(addPos, R.mipmap.realhand);
                    retMap.put(addPos + 1, R.mipmap.realturn);
                    addPos += 2;
                    break;
            }
        }
        talamLen = addPos;
        for (int i = 0; i < talamLen; i++) {
            retMap.put(addPos + i, numbers[(numbers.length - addPos) + i]);
        }
        return retMap;
    }

    public HashMap<Integer, Integer> makeChapuMap(int chapuLength) {
        HashMap<Integer, Integer> retMap = new HashMap<Integer, Integer>();
        beats = new HashMap<Integer, Boolean>();
        switch (chapuLength) {
            case 3:
                retMap.put(0, R.mipmap.realhand);
                retMap.put(1, R.mipmap.realhand);
                retMap.put(2, R.mipmap.hello);
                retMap.put(3, R.mipmap.three);
                retMap.put(4, R.mipmap.two);
                retMap.put(5, R.mipmap.one);
                beats.put(0, true);
                beats.put(1, true);
                beats.put(2, false);
                talamLen = 3;
                break;
            case 5:
                retMap.put(0, R.mipmap.realhand);
                retMap.put(1, R.mipmap.hello);
                retMap.put(2, R.mipmap.realhand);
                retMap.put(3, R.mipmap.realhand);
                retMap.put(4, R.mipmap.hello);
                retMap.put(5, R.mipmap.five);
                retMap.put(6, R.mipmap.four);
                retMap.put(7, R.mipmap.three);
                retMap.put(8, R.mipmap.two);
                retMap.put(9, R.mipmap.one);
                beats.put(0, true);
                beats.put(1, false);
                beats.put(2, true);
                beats.put(3, true);
                beats.put(4, false);
                talamLen = 5;
                break;
            case 7:
                retMap.put(0, R.mipmap.realhand);
                retMap.put(1, R.mipmap.hello);
                retMap.put(2, R.mipmap.hello);
                retMap.put(3, R.mipmap.realhand);
                retMap.put(4, R.mipmap.hello);
                retMap.put(5, R.mipmap.realhand);
                retMap.put(6, R.mipmap.hello);
                retMap.put(7, R.mipmap.seven);
                retMap.put(8, R.mipmap.six);
                retMap.put(9, R.mipmap.five);
                retMap.put(10, R.mipmap.four);
                retMap.put(11, R.mipmap.three);
                retMap.put(12, R.mipmap.two);
                retMap.put(13, R.mipmap.one);
                beats.put(0, true);
                beats.put(1, false);
                beats.put(2, false);
                beats.put(3, true);
                beats.put(4, false);
                beats.put(5, true);
                beats.put(6, false);
                talamLen = 7;
                break;
            case 9:
                retMap.put(0, R.mipmap.realhand);
                retMap.put(1, R.mipmap.hello);
                retMap.put(2, R.mipmap.realhand);
                retMap.put(3, R.mipmap.hello);
                retMap.put(4, R.mipmap.realhand);
                retMap.put(5, R.mipmap.hello);
                retMap.put(6, R.mipmap.realhand);
                retMap.put(7, R.mipmap.realhand);
                retMap.put(8, R.mipmap.hello);
                retMap.put(9, R.mipmap.nine);
                retMap.put(10, R.mipmap.eight);
                retMap.put(11, R.mipmap.seven);
                retMap.put(12, R.mipmap.six);
                retMap.put(13, R.mipmap.five);
                retMap.put(14, R.mipmap.four);
                retMap.put(15, R.mipmap.three);
                retMap.put(16, R.mipmap.two);
                retMap.put(17, R.mipmap.one);
                beats.put(0, true);
                beats.put(1, false);
                beats.put(2, true);
                beats.put(3, false);
                beats.put(4, true);
                beats.put(5, false);
                beats.put(6, true);
                beats.put(7, true);
                beats.put(8, false);
                talamLen = 9;
                break;
        }
        return retMap;
    }

    public int getLength() {
        return talamLen;
    }

    public HashMap<Integer, Boolean> getChapuBeats() {
        return beats;
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
