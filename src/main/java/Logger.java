import java.util.Date;

public class Logger {
    protected static int num = 1;
    private static Logger instance;
    private final String logfileName = "log.txt";

    public void log(String msg) {
        WorkWithFiles.appendString("[" + new Date() + " " + num++ + "] " + msg + "\n", logfileName);
    }

    private Logger() {}

    public static Logger getInstance() {
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }
}
