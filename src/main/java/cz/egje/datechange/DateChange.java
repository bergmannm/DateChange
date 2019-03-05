package cz.egje.datechange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DateChange {

    long dateFix = 0;

    private synchronized long getDateFix() {
        if (dateFix == 0) {
            String newDate = System.getProperty("changeDate");
            if (newDate == null || newDate.equals("")) {
                dateFix = 86400000L * 365;
            } else {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(date);
                    cal2.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
                    cal2.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
                    cal2.set(Calendar.SECOND, cal.get(Calendar.SECOND));
                    cal2.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
                    dateFix = cal2.getTimeInMillis() - cal.getTimeInMillis();
                } catch (ParseException e) {
                    System.err.println("Failed to parse date " + newDate + ", setting date one year in the future");
                }
            }
        }
        return dateFix;
    }

    @Around("call(long java.lang.System.currentTimeMillis())")
    public Object systemCtm(ProceedingJoinPoint thisJoinPoint) {
        try {
            Object ret = thisJoinPoint.proceed();
            ret = ((Long) ret) + getDateFix();
            return ret;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around("call(java.util.Date.new())")
    public Object newDate(ProceedingJoinPoint thisJoinPoint) {
        try {
            Date ret = (Date) thisJoinPoint.proceed();
            ret.setTime(ret.getTime() + getDateFix());
            return ret;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around("call(java.util.Calendar java.util.Calendar.getInstance()) && !within(cz.egje.datechange.DateChange)")
    public Object calendarGetInstance(ProceedingJoinPoint thisJoinPoint) {
        try {
            Calendar ret = (Calendar) thisJoinPoint.proceed();
            ret.setTimeInMillis(ret.getTimeInMillis() + getDateFix());
            return ret;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around("call(java.util.GregorianCalendar.new())")
    public Object newGregCal(ProceedingJoinPoint thisJoinPoint) {
        try {
            Calendar ret = (Calendar) thisJoinPoint.proceed();
            ret.setTimeInMillis(ret.getTimeInMillis() + getDateFix());
            return ret;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    @Around("call(long java.lang.management.RuntimeMXBean.getStartTime())")
    public Object getStartTime(ProceedingJoinPoint thisJoinPoint) {
        try {
            long ret = (Long) thisJoinPoint.proceed();
            ret = ((Long) ret) + getDateFix();
            return ret;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
