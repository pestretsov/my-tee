import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MyTee {
    public static final int ERROR = 1;

    private static final String READ_ERR_MSG  = "Couldn't read from System.in";
    private static final String WRITE_ERR_MSG = ": Operation not permitted";
    private static final String OPEN_ERR_MSG  = ": Couldn't open file";
    private static final String CLOSE_ERR_MSG = ": Couldn't close file";
    private static final String SYSTEM_OUT = "System.out";

    private static final int BUFFER_SIZE = 8 * 1024;

    private int exitCode = 0;

    public void writeTee(MyTeeArguments args) {
        InputStream inputStream = new BufferedInputStream(System.in);
        Map<String, OutputStream> namedStreamsMap = getStreamsMapFromArguments(args);
        namedStreamsMap.put(SYSTEM_OUT, new BufferedOutputStream(System.out));

        write(inputStream, namedStreamsMap);

        namedStreamsMap.remove(SYSTEM_OUT);
        closeAllStreams(namedStreamsMap);
    }

    public int getExitCode() {
        return exitCode;
    }

    private void write(InputStream inputStream, Map<String, OutputStream> namedStreams) {
        byte[] bytes = new byte[BUFFER_SIZE];

        int bytesRead;
        try {
            while ((bytesRead = inputStream.read(bytes)) != -1) {
                for (Map.Entry<String, OutputStream> namedStream : namedStreams.entrySet()) {
                    try {
                        namedStream.getValue().write(bytes, 0, bytesRead);
                        namedStream.getValue().flush();
                    } catch (IOException e) {
                        reportError(namedStream.getKey() + WRITE_ERR_MSG);
                    }
                }
            }
        } catch (IOException e) {
            reportError(READ_ERR_MSG);
        }
    }

    private void closeAllStreams(Map<String, OutputStream> namedStreams) {
        for (Map.Entry<String, OutputStream> namedStream : namedStreams.entrySet()) {
            try {
                namedStream.getValue().close();
            } catch  (IOException e) {
                reportError(namedStream.getKey() + CLOSE_ERR_MSG);
            }
        }
    }

    private Map<String, OutputStream> getStreamsMapFromArguments(MyTeeArguments args) {
        Map<String, OutputStream> streamsMap = new HashMap<>();
        for (File file : args.files) {
            try {
                OutputStream fileStream = new BufferedOutputStream(new FileOutputStream(file, args.append));
                streamsMap.put(file.getName(), fileStream);
            } catch (FileNotFoundException e) {
                reportError(file.getName() + OPEN_ERR_MSG);
            }
        }

        return streamsMap;
    }

    private void reportError(String msg) {
        System.err.println(msg);
        exitCode = ERROR;
    }
}
