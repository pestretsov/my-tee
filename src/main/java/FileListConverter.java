import com.beust.jcommander.IStringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListConverter implements IStringConverter<List<File>> {
    private static final String SPACE_SPLITTER = " ";

    @Override
    public List<File> convert(String files) {
        String[] paths = files.split(SPACE_SPLITTER);
        List<File> fileList = new ArrayList<>();
        for (String path : paths) {
            fileList.add(new File(path));
        }
        return fileList;
    }
}
