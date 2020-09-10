package com.evente.algorithm.test;

import com.evente.algorithm.service.impl.StackSLinked;

public class StackSLinkedDemo {
    public static void main(String[] args) {

        //String result  = decimal2Oc(2007);
        //System.out.println("result = " + result);

        boolean b = bracketMatch("1 * (2 + 3)[");
        System.out.println("b = " + b);

    }


    private static String decimal2Oc(int i){
        StackSLinked<String> stackSLinked = new StackSLinked<>();

        int mod = 1;
        int oc = 8;

        while (i != 0){
            mod = i % oc;
            i = i / 8;
            stackSLinked.push(mod + "");
        }

        StringBuilder sb = new StringBuilder();

        while (!stackSLinked.isEmpty()){
            sb.append(stackSLinked.pop());
        }

        return sb.toString();

    }

    private static boolean bracketMatch(String str){
        StackSLinked<Integer> stackSLinked = new StackSLinked<>();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            switch (c){
                case '(' :
                case '[' :
                case '{' :
                    stackSLinked.push(c + 0);
                    break;
                case ')' :
                    if (!stackSLinked.isEmpty() &&
                            stackSLinked.pop() == '(')
                        break;
                    else
                        return false;
                case ']' :
                    if (!stackSLinked.isEmpty() &&
                            stackSLinked.pop() == '[')
                        break;
                    else
                        return false;
                case '}' :
                    if (!stackSLinked.isEmpty() &&
                            stackSLinked.pop() == '{')
                        break;
                    else
                        return false;
            }
        }
        if (stackSLinked.isEmpty())
            return true;
        else
            return false;
    }

}
