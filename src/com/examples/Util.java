package com.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amit Thapar on 09/05/2015.
 */
public class Util {

    public static String covertDatetoJSONDate(String web_date)
    {
        String substring = "-";
        String mainString = web_date;
        int a = 0;
        int b = 0;
        int c = 0 ;
        List dateList = new ArrayList();
        for(int i=0; i<mainString.length(); i++ )
        {
            //2015-05-01T00:00:00Z
            //8-May-2015
            Character x = mainString.charAt(i);

            if ((x == '-')&& dateList.size() == 0)
            {
                String data = mainString.substring(0, a);
                b = a+1;
                dateList.add(data);
            }
            else if ((x == '-')&& dateList.size() == 1)
            {
                String data = mainString.substring(b, a+1);
                c = a+2;
                dateList.add(data);
            }
            else if ( i == mainString.length()-1 )
            {
                String data = mainString.substring(c, i+1);
                dateList.add(data);
            }

            else
            {
                a++;
            }
        }
        String date = (String)dateList.get(0);
        if(date.length()==1)
        {
            date = "0"+date;
        }
        String month = (String)dateList.get(1);
        String monthVal = covertMonth(month);
        String year = (String)dateList.get(2);
        String jsonDate = dateList.get(2)+"-"+monthVal+"-"+date+"T00:00:00Z";
        return jsonDate;
    }

    public static String covertMonth(String month)
    {
        String monthValue = "";
        if (month.equalsIgnoreCase("May"))
        {
            monthValue = "05";
        }
        return monthValue;
    }



    public static Date parseDateTime(String dateString) {
        if (dateString == null) return null;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        if (dateString.contains("T")) dateString = dateString.replace('T', ' ');
        if (dateString.contains("Z")) dateString = dateString.replace("Z", "+0000");
        else
            dateString = dateString.substring(0, dateString.lastIndexOf(':')) + dateString.substring(dateString.lastIndexOf(':')+1);
        try {
            return fmt.parse(dateString);
        }
        catch (ParseException e) {
            return null;
        }
    }


    public  static String convertStreamToString(InputStream is) {
        ByteArrayOutputStream oas = new ByteArrayOutputStream();
        copyStream(is, oas);
        String t = oas.toString();
        try {
            oas.close();
            oas = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }

    public  static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }


}
