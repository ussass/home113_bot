package ru.trofimov.Bot;

public class LoopBotFlag {
    private static LoopBotFlag instance;
    private boolean flag;
    private LoopBotFlag() {
        flag = false;
    }

    public static LoopBotFlag getInstance(){
        if(instance == null){
            instance = new LoopBotFlag();
        }
        return instance;
    }

    public boolean isFlagTrueOneTime() {
        if (flag){
            boolean b = flag;
            flag = false;
            return b;
        }
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
