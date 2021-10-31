
import java.io.*;

public class WorkWithFiles {

    public static String readString(String outputFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(outputFileName))){
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void writeString(String data, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName, false)) {
            writer.write(data);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void appendString(String data, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName, true)) {
            writer.write(data);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
