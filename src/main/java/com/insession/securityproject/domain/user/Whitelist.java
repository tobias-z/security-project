package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.entities.UserEntity;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class Whitelist {

    private String password;
    private String userName;
    private String email;
    private Array characters;

    public Whitelist(UserEntity userEntity) {
        this.password = userEntity.getPassword();
        this.userName = userEntity.getUserName();
    }

    public Whitelist() {
    }

    public static ArrayList whiteListCharacters() {
        ArrayList allChars = new ArrayList<>();

        for(char cu = 'A'; cu <= 'Z'; cu++) {
            allChars.add(cu);
        }
        for (char cl = 'a'; cl <= 'z'; cl++) {
            allChars.add(cl);
        }
        for(int i = 0; i <= 9; i++) {
            allChars.add(i);
        }
        return allChars;
    }

    public static void passwordWhitelist(String password) {
        char[] arr = whiteListCharacters().toString().toCharArray();
        char[] pass = password.toCharArray();

        Boolean bool = false;
        for (int i = 0; i <= pass.length-1; i++) {
            char c = password.charAt(i);
            System.out.println(c);
            if (whiteListCharacters().contains(pass[i])) {
                System.out.println("WOW");
            } else {
                System.out.println("IKKE-WOW");
            }
            bool = true;
        }
    }


    public static void main(String[] args) {
        Whitelist whitelist = new Whitelist();
        passwordWhitelist("en2tre");
    }


}
