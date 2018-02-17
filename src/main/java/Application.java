import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

class Application {
    public static void main(String[] args) {
        MyTeeArguments arguments = new MyTeeArguments();

        try {
            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(args);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            e.usage();
            System.exit(MyTee.ERROR);
        }

        MyTee myTee = new MyTee();
        myTee.writeTee(arguments);

        System.exit(myTee.getExitCode());
    }
}
