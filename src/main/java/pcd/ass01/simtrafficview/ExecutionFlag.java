package pcd.ass01.simtrafficview;

/**
 *  Wrapper class to handle the execution flag and to pass the value by reference
 */
public class ExecutionFlag {
    private boolean value;

    public ExecutionFlag(boolean value) {
        this.value = value;
    }

    /**
     * @return the value of the flag
     */
    public boolean get() {
        return value;
    }

    /**
     * @param value update the value of the flag
     */
    public void set(boolean value) {
        this.value = value;
    }
}
