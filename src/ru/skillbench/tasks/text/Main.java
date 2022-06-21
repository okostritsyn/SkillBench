package ru.skillbench.tasks.text;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactCardImpl test = new ContactCardImpl();

        String sb = """
                BEGIN:VCARD\r
                FN:Forrest Gump\r
                ORG:Bubba Gump Shrimp Co.\r
                BDAY:24-11-1991\r
                GENDER:F\r
                TEL;TYPE=VOICE:1234567890\r
                END:VCARD\r
                """;
        Scanner scanner = new Scanner(sb);
        test.getInstance(scanner);
        System.out.println(test.getBirthday());
    /*
        java.lang.AssertionError: getBirthday() failed with this text:
        FN:Андрей Петров
        ORG:Фриланс
        BDAY:24-11-1991
        expected:<java.util.GregorianCalendar[time=690930000000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="GMT+03:00",offset=10800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1991,MONTH=10,WEEK_OF_YEAR=48,WEEK_OF_MONTH=5,DAY_OF_MONTH=24,DAY_OF_YEAR=328,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=4,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=10800000,DST_OFFSET=0]> but was:<java.util.GregorianCalendar[time=?,areFieldsSet=false,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="GMT+03:00",offset=10800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1991,MONTH=10,WEEK_OF_YEAR=26,WEEK_OF_MONTH=4,DAY_OF_MONTH=24,DAY_OF_YEAR=172,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=10,HOUR_OF_DAY=10,MINUTE=46,SECOND=51,MILLISECOND=573,ZONE_OFFSET=10800000,DST_OFFSET=0]>
        at org.junit.Assert.fail(Assert.java:88)
        at org.junit.Assert.failNotEquals(Assert.java:834)
        at org.junit.Assert.assertEquals(Assert.java:118)
        at ru.skillbench.tasks.text.ContactCardTest.getBirthday(ContactCardTest.java:165)
	*/
    }
}
