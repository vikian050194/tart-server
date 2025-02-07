package tart.app.api.user;

class RegistrationResponse {

    String id;

    public RegistrationResponse() {

    }

    public RegistrationResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }
}
