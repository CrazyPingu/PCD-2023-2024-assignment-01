package pcd.ass01.simtrafficview;

public class ExecutionFlag {
    private boolean value;

    public ExecutionFlag(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        this.value = value;
    }
}
