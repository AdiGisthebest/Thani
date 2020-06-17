package adi.app.thani;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Container implements Serializable {
    LinkedList<String> recorder = new LinkedList<String>();
    HashMap<String, Boolean> audio = new HashMap<String, Boolean>();
    Integer i = new Integer(0);
}
