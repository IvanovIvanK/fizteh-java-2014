package ru.fizteh.fivt.students.ivan_ivanov.shell;

public class ShellExecutor extends Executor<ShellState> {

    public ShellExecutor() {

        list();
    }

    public void list() {

        Command pwd = new Pwd();
        mapOfCmd.put(pwd.getName(), pwd);
        Command cd = new Cd();
        mapOfCmd.put(cd.getName(), cd);
        Command mkdir = new Mkdir();
        mapOfCmd.put(mkdir.getName(), mkdir);
        Command cp = new Cp();
        mapOfCmd.put(cp.getName(), cp);
        Command mv = new Mv();
        mapOfCmd.put(mv.getName(), mv);
        Command rm = new Rm();
        mapOfCmd.put(rm.getName(), rm);
        Command exit = new Exit();
        mapOfCmd.put(exit.getName(), exit);
        Command ls = new Ls();
        mapOfCmd.put(ls.getName(), ls);
    }
}
