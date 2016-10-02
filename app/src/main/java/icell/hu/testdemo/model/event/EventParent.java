package icell.hu.testdemo.model.event;

/**
 * Created by User on 2016. 09. 30..
 */

public class EventParent {

    boolean error = false;

    public void setError(boolean hasError) {
        this.error = hasError;
    }

    public boolean isError() {
        return error;
    }
}
