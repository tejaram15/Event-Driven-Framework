package csvtodgraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import field;
/**
 *
 * @author raghu
 */
public class field {
    String id;
    String start_date,end_date;//For display enter date.toString();
    int no_of_days,interest_rate;
    double Principal,Paid,Percentage_paid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrincipal() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        String Prin = df.format(this.Principal);
        return Prin;
    }

    public void setPrincipal(String Principal) {
        this.Principal = Double.parseDouble(Principal);
    }

    public String getPercentage_paid() {
        return Double.toString(Percentage_paid);
    }

    public void setPercentage_paid(String Percentage_paid) {
        this.Percentage_paid = Double.parseDouble(Percentage_paid);
    }

    public String getPaid() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        String Pd = df.format(this.Paid);
        return Pd;
    }

    public void setPaid(String Paid) {
        this.Paid = Double.parseDouble(Paid);
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
            this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
            this.end_date = end_date;
    }

    public String getNo_of_days() {
        return Integer.toString(no_of_days);
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = Integer.parseInt(no_of_days);
    }

    public String getInterest_rate() {
        return Integer.toString(interest_rate);
    }

    public void setInterest_rate(String interest_rate) {
        this.interest_rate = Integer.parseInt(interest_rate);
    }
}
public class CSVtoDgraph {

    public static void main(String[] args) throws IOException {
        List<field> data;
        data = readfromcsv("TestCsvData.csv");
        String insert_query = "curl localhost:8080/query -XPOST -d $\'\nmutation{\n\tset{\n\t\t_:main_branch <name> \"Vijaya Bank\" .\n";
        for(field f:data){                        
            insert_query += "\t\t_:";
            insert_query += f.getId();
            insert_query += " <start_date> \"";
            insert_query += f.getStart_date();
            insert_query += "\"^^<xs:date> .\n";

            insert_query += "\t\t_:";
            insert_query += f.getId();
            insert_query += " <interest_rate> \"";
            insert_query += f.getInterest_rate();
            insert_query += "\"^^<xs:int> .\n";
            
            insert_query += "\t\t_:";
            insert_query += f.getId();
            insert_query += " <principal> \"";
            insert_query += f.getPrincipal();
            insert_query += "\"^^<xs:float> .\n";

            insert_query += "\t\t_:";
            insert_query += f.getId();
            insert_query += " <paid> \"";
            insert_query += f.getPaid();
            insert_query += "\"^^<xs:float> .\n";

            insert_query += "\t\t_:";
            insert_query += f.getId();
            insert_query += " <percent_paid> \"";
            insert_query += f.getPercentage_paid();
            insert_query += "\"^^<xs:float> .\n";

            insert_query += "\t\t_:main_branch <associates> _:";
            insert_query += f.getId();
            insert_query += " .\n";

//            System.out.println(f.getStart_date()+" , "+f.getEnd_date());
        }
        insert_query += "\t}\n}\'";
        System.out.println(insert_query);
//        try (PrintStream out = new PrintStream(new FileOutputStream("filename.txt"))) {
//            out.print(insert_query);
//        }
    }

    private static List<field> readfromcsv(String filename) throws IOException {
        List<field> data = new ArrayList<>();
        Path path = Paths.get(filename);
        BufferedReader br;
        br = Files.newBufferedReader(path,StandardCharsets.US_ASCII);
        String line = br.readLine();
        int i=1;
        while(line!=null && i<=1000){
            //System.out.println(line);
            String[] attributes = line.split(",");
            field fd = createfield(attributes);
            data.add(fd);
            line = br.readLine();
            i++;
        }
        return data;
    }

    private static field createfield(String[] attributes) {
        field fd = new field();
        //Of Types String
        String uniqueID = String.valueOf(attributes[0].trim());
        fd.setId(uniqueID);
        //Of Type Date
        fd.setStart_date(attributes[1].trim());
        //Of Type Int
        fd.setInterest_rate(attributes[2].trim());
        //Of Type Double
        fd.setPrincipal(attributes[3].trim());
        fd.setPaid(attributes[4].trim());
        fd.setPercentage_paid(attributes[5].trim());
        return fd;
    }
    
}
