import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberCertificationTest {

    @org.junit.jupiter.api.Test
    void main() {

    }

    @org.junit.jupiter.api.Test
    void executeAuthentication() throws ParseException {
        Map<String, String> inputs = new LinkedHashMap<>();
        inputs.put(MemberCertification.CertifiedItems.MAIL.getValue(), "oshima@gmail.com");
        inputs.put(MemberCertification.CertifiedItems.BIRTH_DATE.getValue(), "1990/01/01");
        inputs.put(MemberCertification.CertifiedItems.NAME.getValue(), "大嶋 一哉");
        inputs.put(MemberCertification.CertifiedItems.TEL.getValue(), "090-1111-2222");

        MemberCertification mc = new MemberCertification();
        // 全部一致ならtrue
        assertEquals (true, mc.executeAuthentication(inputs));
        // 一つでも一致しなかったらfalse
        inputs.put(MemberCertification.CertifiedItems.TEL.getValue(), "0120-33-33");
        assertEquals (false, mc.executeAuthentication(inputs));
    }

    @org.junit.jupiter.api.Test
    void checkEmail() {
        MemberCertification mc = new MemberCertification();
        assertEquals (true, mc.checkEmail("oshima@gmail.com"));
        assertEquals (false, mc.checkEmail("hogehoge@gmail.com"));
    }

    @org.junit.jupiter.api.Test
    void checkBirthday() throws ParseException {
        MemberCertification mc = new MemberCertification();
        assertEquals (true, mc.checkBirthday("1990/01/01"));
        assertEquals (true, mc.checkBirthday("1990/01/1"));
        assertEquals (true, mc.checkBirthday("1990/1/01"));
        assertEquals (true, mc.checkBirthday("1990/1/1"));
        assertEquals (false, mc.checkBirthday("1990/03/3"));
        assertEquals (false, mc.checkBirthday("1990/3/3"));
        try {
            assertEquals (false, mc.checkBirthday("hoge"));
        } catch (ParseException e) {
            assertThat(e.getMessage(), equalTo("Unparseable date: \"hoge\""));
        }
    }

    @org.junit.jupiter.api.Test
    void checkName() {
        MemberCertification mc = new MemberCertification();
        assertEquals (true, mc.checkName("大嶋 一哉"));
        assertEquals (false, mc.checkName("大嶋一哉"));
        assertEquals (false, mc.checkName("中野 拓"));
    }

    @org.junit.jupiter.api.Test
    void checkTel() {
        MemberCertification mc = new MemberCertification();
        assertEquals (true, mc.checkTel("090-1111-2222"));
        assertEquals (true, mc.checkTel("09011112222"));
        assertEquals (false, mc.checkTel("0123-999-999"));
        assertEquals (false, mc.checkTel("0123999999"));
    }
}