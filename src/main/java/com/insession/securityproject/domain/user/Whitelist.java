package com.insession.securityproject.domain.user;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Whitelist {

    private String password;
    private Array characters;

    public Whitelist() {
    }

    public static ArrayList whiteListCharacters() {
        ArrayList allChars = new ArrayList<>();

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

    public static boolean passwordWhitelist(String password) {
        Boolean passWhitelist = false;
        for (int i = 0; i <= password.length() - 1; i++) {
            char c = password.charAt(i);
            if (whiteListCharacters().contains(c)) {
                passWhitelist = true;
            } else {
                passWhitelist = false;
            }
        }
        return passWhitelist;
    }
}
