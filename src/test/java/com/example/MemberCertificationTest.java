package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.MemberCertificationTest.CertifiedTestItems.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class MemberCertificationTest {

    private static MemberCertification certification;
    private static final String EXPECTED_EMAIL_TEST = "oshima@gmail.com";
    private static final String EXPECTED_BIRTHDAY_TEST = "1990/01/01";
    private static final String EXPECTED_NAME_TEST = "大嶋 一哉";
    private static final String EXPECTED_TEL_TEST = "090-1111-2222";

    @AllArgsConstructor
    @Getter
    enum CertifiedTestItems {
        MAIL("mail"),
        BIRTH_DATE("birthDate"),
        NAME("name"),
        TEL("tel");

        private final String value;
    }


    @Before
    public void setUp() {
        certification = new MemberCertification();
    }

    static class MapFixture {
        Map<String, String> value; // テストデータ
        boolean expected; // 期待値

        MapFixture(Map<String, String> value, boolean expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    static class Fixture {
        String value; // テストデータ
        boolean expected; // 期待値

        Fixture(String value, boolean expected) {
            this.value = value;
            this.expected = expected;
        }
    }

    @RunWith(Theories.class)
    public static class 全ての認証が成功する場合 {
        public static Map<String, String> value = new LinkedHashMap<String, String>() {
            {
                put(MAIL.getValue(),EXPECTED_EMAIL_TEST);
                put(BIRTH_DATE.getValue(),EXPECTED_BIRTHDAY_TEST);
                put(NAME.getValue(),EXPECTED_NAME_TEST);
                put(TEL.getValue(),EXPECTED_TEL_TEST);
            }
        };

        @DataPoint
        public static MapFixture FIXTURES =
                new MapFixture(value, true);

        @Theory
        public void executeAuthentication_認証が成功する場合はtrueが返却される(MapFixture fixture) throws Exception {
            boolean actual = certification.executeAuthentication(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class 一つでも認証が失敗する場合 {
        public static Map<String, String> value = new LinkedHashMap<String, String>() {
            {
                put(MAIL.getValue(),EXPECTED_EMAIL_TEST);
                put(BIRTH_DATE.getValue(),EXPECTED_BIRTHDAY_TEST);
                put(NAME.getValue(),EXPECTED_NAME_TEST);
                put(TEL.getValue(),"hogehogehoge");
            }
        };

        @DataPoint
        public static MapFixture FIXTURES =
                new MapFixture(value, false);

        @Theory
        public void executeAuthentication_認証が失敗した場合はfalseが返却される(MapFixture fixture) throws Exception {
            boolean actual = certification.executeAuthentication(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class アドレス_正しい場合 {
        @DataPoint
        public static Fixture FIXTURE =
            new Fixture("oshima@gmail.com", true);


        @Theory
        public void checkEmail_名前が正しい場合はtrueが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkEmail(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class アドレス_間違っている場合 {
        @DataPoints
        public static Fixture[] FIXTURES = {
                new Fixture("hogehoge@hoge.jp", false),
                new Fixture("", false)
        };


        @Theory
        public void checkEmail_名前が間違っている場合はfalseが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkEmail(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class 生年月日_正しい場合 {
        @DataPoints
        public static Fixture[] FIXTURES = {
                new Fixture("1990/01/01", true),
                new Fixture("1990/1/1", true)
        };

        @Theory
        public void checkBirthday_生年月日が正しい場合はtrueが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkBirthday(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual,  is(expected));
        }
    }


    @RunWith(Theories.class)
    public static class 生年月日_型違い_例外発生 {
        @DataPoints
        public static Fixture[] FIXTURES = {
                new Fixture("199011", false),
                new Fixture("", false)
        };

        @Theory
        @Test(expected = ParseException.class)
        public void checkBirthday_生年月日がキャスト不能な場合は例外を送出する(Fixture fixture) throws Exception {
            certification.checkBirthday(fixture.value);
        }
    }


    @RunWith(Theories.class)
    public static class 名前_正しい場合 {
        @DataPoint
        public static Fixture FIXTURE =
                new Fixture("大嶋 一哉", true);


        @Theory
        public void checkName_名前が正しい場合はtrueが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkName(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class 名前_間違っている場合 {
        @DataPoints
        public static Fixture[] FIXTURES = {
                new Fixture("中野 拓", false),
                new Fixture("", false)
        };


        @Theory
        public void checkName_名前が間違っている場合はfalseが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkName(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class 電話番号_正しい場合 {
        @DataPoints
        public static Fixture[] FIXTURE = {
                new Fixture("090-1111-2222", true),
                new Fixture("09011112222", true),
        };


        @Theory
        public void checkTel_電話番号が正しい場合はtrueが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkTel(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class 電話番号_間違っている場合 {
        @DataPoints
        public static Fixture[] FIXTURES = {
                new Fixture("0120-03-99", false),
                new Fixture("", false)
        };


        @Theory
        public void checkTel_電話番号が間違っている場合はfalseが返却される(Fixture fixture) throws Exception {
            boolean actual = certification.checkTel(fixture.value);
            boolean expected = fixture.expected;

            assertThat(actual, is(expected));
        }
    }
}