import com.beust.jcommander.Parameter;

import java.io.File;
import java.util.Collections;
import java.util.List;

class MyTeeArguments {
    @Parameter(names = {"-a", "--append"}, description = "Append to files")
    boolean append = false;

    @Parameter(names = {"-f", "--files"}, description = "List of files to write to",
            listConverter = FileListConverter.class, variableArity = true)
    List<File> files = Collections.emptyList();
}
