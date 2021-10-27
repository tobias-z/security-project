package com.insession.securityproject.domain.user;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Whitelist {

    private static ArrayList<Character> whiteListCharacters() {
        ArrayList<Character> allChars = new ArrayList<>();

        allChars.add('æ');
        allChars.add('ø');
        allChars.add('å');
        allChars.add('Æ');
        allChars.add('Ø');
        allChars.add('Å');

        for(char cu = 'A'; cu <= 'Z'; cu++) {
            allChars.add(cu);
        }
        for (char cl = 'a'; cl <= 'z'; cl++) {
            allChars.add(cl);
        }
        for(char i = '0'; i <= '9'; i++) {
            allChars.add(i);
        }
        return allChars;
    }

    private static boolean validate(String check, ArrayList<Character> listVar) {
        for (int i = 0; i <= check.length() - 1; i++) {
            char c = check.charAt(i);
            if (!listVar.contains(c)) {
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

}
