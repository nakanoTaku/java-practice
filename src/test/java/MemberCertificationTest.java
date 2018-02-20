import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class MemberCertificationTest {

    private static MemberCertification certification;

    @Before
    public void setUp() {
        certification = new MemberCertification();
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