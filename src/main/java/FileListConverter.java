import com.beust.jcommander.IStringConverter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileListConverter implements IStringConverter<List<File>> {
    private static final String SPACE_SPLITTER = " ";

    @Override
    public List<File> convert(String files) {
        String[] paths = files.split(SPACE_SPLITTER);
        return Arrays.stream(paths)
                .map(File::new)
                .collect(Collectors.toList());
    }
}
