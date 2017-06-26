package csvtodgraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raghu
 */
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
            insert_query += " <end_date> \"";
            insert_query += f.getEnd_date();
            insert_query += "\"^^<xs:date> .\n";

            insert_query += "\t\t_:";
            insert_query += f.getId();
            insert_query += " <no_of_days> \"";
            insert_query += f.getNo_of_days();
            insert_query += "\"^^<xs:int> .\n";

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
        fd.setEnd_date(attributes[2].trim());
        //Of Type Int
        fd.setNo_of_days(attributes[3].trim());
        fd.setInterest_rate(attributes[4].trim());
        //Of Type Double
        fd.setPrincipal(attributes[5].trim());
        fd.setPaid(attributes[6].trim());
        fd.setPercentage_paid(attributes[7].trim());
        return fd;
    }
    
}
