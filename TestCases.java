import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCases {
    public static void main(String[] args) throws FileNotFoundException {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date StartDate = new Date(),EndDate = new Date();
        try {
            StartDate = ft.parse("2004-02-09");
        } catch (ParseException ex) {
            Logger.getLogger(TestCases.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            EndDate = ft.parse("2017-06-15");
        } catch (ParseException ex) {
            Logger.getLogger(TestCases.class.getName()).log(Level.SEVERE, null, ex);
        }
        String fileWrite = "";//Format of string is
        //ID,StartDate,EndDate,No of Days,Rate,Principal,Paid,Percentage paid
        for(int i=1;i<=1000;i++){
            fileWrite += Integer.toString(i);
            fileWrite += ",";
            
            long random = ThreadLocalRandom.current().nextLong(StartDate.getTime(), EndDate.getTime());
            Date start = new Date(random);
            fileWrite += ft.format(start);
            fileWrite += ",";
            
            random = ThreadLocalRandom.current().nextLong(start.getTime(), EndDate.getTime());
            Date end = new Date(random);
            fileWrite += ft.format(end);
            fileWrite += ",";
            
            int diffInDays;
            diffInDays = (int)( (end.getTime() - start.getTime())/ (1000 * 60 * 60 * 24) );
            fileWrite += Integer.toString(diffInDays);
            fileWrite += ",13,";
            
            int principal = ThreadLocalRandom.current().nextInt(10000, 101010101 + 1);
            fileWrite += Integer.toString(principal);
            fileWrite += ",";
            
            Random rand = new Random();
            float percentage = (float) (rand.nextFloat() * (100.0));
            percentage = (float) ((float) Math.round(percentage * 100.0d)/100.0d);
            double paid = (double) (principal * percentage/100.0);
            paid = (double) ((double) Math.round(paid * 100.0d)/100.0d);
            
            
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(5);
            fileWrite += df.format(paid);
            fileWrite += ",";
            
            fileWrite += Float.toString(percentage);
            fileWrite += "\n";
        }
        //System.out.println(fileWrite);
        try (PrintStream out = new PrintStream(new FileOutputStream("TestCsvData.csv"))) {
            out.print(fileWrite);
        }
    }    
}
