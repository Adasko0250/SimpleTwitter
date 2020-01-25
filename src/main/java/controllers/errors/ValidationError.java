package controllers.errors;

public class ValidationError {

    private String message;
    private String header;

    public ValidationError(String header, String message) {
        this.header = header;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getHeader() {
        return header;
    }
}
