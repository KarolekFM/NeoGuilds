package net.karolek.neoguilds.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");

    private DateUtil() {
    }

    public static String formatDate(long value) {
        return dateFormat.format(new Date(value));
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if ((m.group() != null) && (!m.group().isEmpty())) {
                    for (int i = 0; i < m.groupCount(); i++)
                        if ((m.group(i) != null) && (!m.group(i).isEmpty())) {
                            found = true;
                            break;
                        }
                    if (found) {
                        if ((m.group(1) != null) && (!m.group(1).isEmpty()))
                            years = Integer.parseInt(m.group(1));
                        if ((m.group(2) != null) && (!m.group(2).isEmpty()))
                            months = Integer.parseInt(m.group(2));
                        if ((m.group(3) != null) && (!m.group(3).isEmpty()))
                            weeks = Integer.parseInt(m.group(3));
                        if ((m.group(4) != null) && (!m.group(4).isEmpty()))
                            days = Integer.parseInt(m.group(4));
                        if ((m.group(5) != null) && (!m.group(5).isEmpty()))
                            hours = Integer.parseInt(m.group(5));
                        if ((m.group(6) != null) && (!m.group(6).isEmpty()))
                            minutes = Integer.parseInt(m.group(6));
                        if ((m.group(7) != null) && (!m.group(7).isEmpty()))
                            seconds = Integer.parseInt(m.group(7));
                    }
                }
            }
            if (!found)
                return -1L;
            Calendar c = new GregorianCalendar();
            if (years > 0)
                c.add(1, years * (future ? 1 : -1));
            if (months > 0)
                c.add(2, months * (future ? 1 : -1));
            if (weeks > 0)
                c.add(3, weeks * (future ? 1 : -1));
            if (days > 0)
                c.add(5, days * (future ? 1 : -1));
            if (hours > 0)
                c.add(11, hours * (future ? 1 : -1));
            if (minutes > 0)
                c.add(12, minutes * (future ? 1 : -1));
            if (seconds > 0)
                c.add(13, seconds * (future ? 1 : -1));
            Calendar max = new GregorianCalendar();
            max.add(1, 10);
            if (c.after(max))
                return max.getTimeInMillis();

            return c.getTimeInMillis();
        } catch (Exception e) {
        }
        return -1L;
    }

}
