package com.evente.javabase;

import java.util.ArrayList;
import java.util.List;

// -XX:PermSize=8m -XX:MaxPermSize=8m
// Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=8m; support was removed in 8.0
// Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=8m; support was removed in 8.0


// -Xmx2048m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=768m -Xss2m
public class StringOomMock {
    static String  base = "string";
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for (int i=0;i< Integer.MAX_VALUE;i++){
            String str = base + base;
            base = str;
            list.add(str.intern());
        }
    }
}
