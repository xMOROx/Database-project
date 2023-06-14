package org.agh.edu.pl.carrentalrestapi.utils;

public class StringConvert {
   public static String convertToCamelCase(String str) {
        StringBuilder builder = new StringBuilder(str);
        boolean isLastSpace = true;

        for (int i = 0; i < builder.length(); i++) {
             char ch = builder.charAt(i);

             if (isLastSpace && ch >= 'a' && ch <= 'z') {

                builder.setCharAt(i, (char) (ch + ('A' - 'a')));
                isLastSpace = false;
             } else isLastSpace = ch == ' ';
        }

        return builder.toString();
   }

}
