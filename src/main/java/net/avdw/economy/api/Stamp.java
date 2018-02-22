package net.avdw.economy.api;

import java.text.SimpleDateFormat;
import java.util.Date;

class Stamp
{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    final Date date;
    final String simpleName;

    Stamp(Date date, String simpleName)
    {
        this.date = date;
        this.simpleName = simpleName;
    }

    @Override
    public String toString()
    {
        return String.format("%s,%s", new Object[]
        {
            sdf.format(date), simpleName
        });
    }
}
