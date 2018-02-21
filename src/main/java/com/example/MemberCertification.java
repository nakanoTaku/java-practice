package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static com.example.MemberCertification.CertifiedItems.*;

public class MemberCertification {
    public static final String EXPECTED_EMAIL = "oshima@gmail.com";
    public static final String EXPECTED_BIRTHDAY = "1990/01/01";
    public static final String EXPECTED_NAME = "大嶋 一哉";
    public static final String EXPECTED_TEL = "090-1111-2222";

    @AllArgsConstructor
    @Getter
    public enum CertifiedItems {

        MAIL("mail"),
        BIRTH_DATE("birthDate"),
        NAME("name"),
        TEL("tel");

        private final String value;
    }

    public static final String AUTHENTICATED = "正しく認証されました。";
    public static final String UNAUTHORIZED = "認証情報が正しくありません。";

    private static Map<String, String> messages = new LinkedHashMap<String, String>() {
        {
            put(MAIL.getValue(),"メールアドレスを入力してください。");
            put(BIRTH_DATE.getValue(),"生年月日を入力してください。");
            put(NAME.getValue(),"名前を入力してください。");
            put(TEL.getValue(),"電話番号を入力してください。");
        }
    };

    public static void main(String args[]) {
        System.out.println("再認証を行います。");
        Map<String, String> inputs = new LinkedHashMap<>();
        try {
            messages.forEach((k, v) -> {
                System.out.println(v);
                Scanner sc = new Scanner(System.in);
                inputs.put(k, sc.nextLine());
            });
            String msgResult = executeAuthentication(inputs) ? AUTHENTICATED : UNAUTHORIZED;
            System.out.println(msgResult);
        } catch (ParseException e) {
            System.out.println(UNAUTHORIZED);
            e.printStackTrace();
        }
    }

    public static boolean executeAuthentication(Map<String, String> input) throws ParseException {
        Boolean[] results = {
                checkEmail(input.get(MAIL.getValue())),
                checkBirthday(input.get(BIRTH_DATE.getValue())),
                checkName(input.get(NAME.getValue())),
                checkTel(input.get(TEL.getValue()))
        };
        return Arrays.stream(results).allMatch(Boolean::valueOf);
    }

    public static boolean checkEmail(String email) {
        return EXPECTED_EMAIL.equals(email);
    }

    static boolean checkBirthday(String birthDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return (sdf.parse(EXPECTED_BIRTHDAY).compareTo(sdf.parse(birthDate)) == 0);
    }

    static boolean checkName(String name) {
        return EXPECTED_NAME.equals(name);
    }

    static boolean checkTel(String tel) {
        return EXPECTED_TEL.replaceAll("-","").equals(tel.replaceAll("-",""));
    }
}