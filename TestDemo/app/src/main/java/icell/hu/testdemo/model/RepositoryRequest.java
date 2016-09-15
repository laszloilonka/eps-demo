package icell.hu.testdemo.model;

/**
 * Created by ilaszlo on 13/09/16.
 */

public class RepositoryRequest {

    private String name;

    public RepositoryRequest(String name) {
        this.name = name;
    }

    public RepositoryRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
