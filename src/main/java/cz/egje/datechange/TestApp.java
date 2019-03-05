package cz.egje.datechange;


import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestApp {

    public static void main(String[] args) {
        try {
            System.out.println(System.currentTimeMillis());
            System.out.println(new Date());
            System.out.println(Calendar.getInstance().getTime());
            System.out.println(new GregorianCalendar().getTime());
            System.out.println(ManagementFactory.getRuntimeMXBean().getStartTime());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
