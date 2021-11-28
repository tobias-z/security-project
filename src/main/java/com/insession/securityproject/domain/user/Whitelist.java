package com.insession.securityproject.domain.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Whitelist {

    private static ArrayList<Character> whiteListCharacters() {
        ArrayList<Character> allChars = new ArrayList<>();

        allChars.add('æ');
        allChars.add('ø');
        allChars.add('å');
        allChars.add('Æ');
        allChars.add('Ø');
        allChars.add('Å');
        allChars.add('_');
        allChars.add('-');

        for (char cu = 'A'; cu <= 'Z'; cu++) {
            allChars.add(cu);
        }
        for (char cl = 'a'; cl <= 'z'; cl++) {
            allChars.add(cl);
        }
        for (char i = '0'; i <= '9'; i++) {
            allChars.add(i);
        }
        return allChars;
    }

    private static boolean validate(String check, ArrayList<Character> listVar) {
        for (int i = 0; i <= check.length() - 1; i++) {
            char c = check.charAt(i);
            if (!listVar.contains(c)) {
                System.out.println(c);
                return false;
            }
        }
        return true;
    }

    public static boolean validateInput(String check) {
        return validate(check, whiteListCharacters());
    }

    public static boolean validateEmail(String email) {
        ArrayList<Character> listVar = whiteListCharacters();
        listVar.add('@');
        listVar.add('.');
        return validate(email, listVar) && (email.contains("@") && email.contains("."));
    }


    // File extension validation
    private static boolean validateImageFileExtensions(String check) {
        String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(check);

        return m.matches();
    }

    public static boolean validateImageFile(String check) {
        return validateImageFileExtensions(check);
    }
}
