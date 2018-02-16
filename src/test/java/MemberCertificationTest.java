import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
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

        // 全部一致ならtrue
        assertEquals (true, MemberCertification.executeAuthentication(inputs));
        // 一つでも一致しなかったらfalse
        inputs.put(MemberCertification.CertifiedItems.TEL.getValue(), "0120-33-33");
        assertEquals (false, MemberCertification.executeAuthentication(inputs));
    }

    @org.junit.jupiter.api.Test
    void checkEmail() {
        assertEquals (true, MemberCertification.checkEmail("oshima@gmail.com"));
        assertEquals (false, MemberCertification.checkEmail("hogehoge@gmail.com"));
    }

    @org.junit.jupiter.api.Test
    void checkBirthday() throws ParseException {
        assertEquals (true, MemberCertification.checkBirthday("1990/01/01"));
        assertEquals (true, MemberCertification.checkBirthday("1990/01/1"));
        assertEquals (true, MemberCertification.checkBirthday("1990/1/01"));
        assertEquals (true, MemberCertification.checkBirthday("1990/1/1"));
        assertEquals (false, MemberCertification.checkBirthday("1990/03/3"));
        assertEquals (false, MemberCertification.checkBirthday("1990/3/3"));
        try {
            assertEquals (false, MemberCertification.checkBirthday("hoge"));
        } catch (ParseException e) {
            assertThat(e.getMessage(), equalTo("Unparseable date: \"hoge\""));
        }
    }

    @org.junit.jupiter.api.Test
    void checkName() {
        assertEquals (true, MemberCertification.checkName("大嶋 一哉"));
        assertEquals (false, MemberCertification.checkName("大嶋一哉"));
        assertEquals (false, MemberCertification.checkName("中野 拓"));
    }

    @org.junit.jupiter.api.Test
    void checkTel() {
        assertEquals (true, MemberCertification.checkTel("090-1111-2222"));
        assertEquals (true, MemberCertification.checkTel("09011112222"));
        assertEquals (false, MemberCertification.checkTel("0123-999-999"));
        assertEquals (false, MemberCertification.checkTel("0123999999"));
    }
}