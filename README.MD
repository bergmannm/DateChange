# Project Title

Change java date via AspectJ 

## Getting Started

Use system property changeDate to configure which date java should use. Based on this value, current java time is shifted by number of whole days. Current hour, minute, second is preserved.

Values returned from
- System.currentTimeMills()
- Calendar.getInstance()
- new GregorianCalendar()
- new Date()
- RuntimeMXBean.getStartTime()
are updated.

## Deployment

Command to perform load-time weaving in your app

```
java -DchangeDate=2050-06-06 -javaagent:aspectjweaver.jar -cp datechange-1.0.jar;aspectjweaver.jar cz.egje.datechange.TestApp
```
