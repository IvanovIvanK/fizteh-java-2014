package ru.fizteh.fivt.students.ivan_ivanov.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Executor<State> {

    public Map<String, Command> mapOfCmd = new HashMap<String, Command>();

    public void setMapOfCmd(Command[] cmds) {
        for (Command cmd : cmds) {
            mapOfCmd.put(cmd.getName(), cmd);
        }
    }


    public String[] argsCheck(String inCommand) {

        int space = inCommand.indexOf(" ");
        if (-1 == space) {
            return new String[0];
        }
        String substr = inCommand.substring(space + 1);
        return substr.trim().split("\\ ");
    }

    public String cmdCheck(String cmd) {

        String tmp;
        int space = cmd.indexOf(" ");
        if (-1 == space) {
            space = cmd.length();
        }
        tmp = cmd.substring(0, space);
        return tmp;
    }


    public void execute(State state, String cmd) throws IOException {

        if (!mapOfCmd.containsKey(cmdCheck(cmd))) {
            throw new IOException("Can't find key");
        }
        mapOfCmd.get(cmdCheck(cmd)).executeCmd(state, argsCheck(cmd));
    }
}
