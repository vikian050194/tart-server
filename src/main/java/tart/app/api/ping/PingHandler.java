package tart.app.api.ping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import tart.app.api.Constants;
import tart.app.api.Handler;
import tart.app.api.ResponseEntity;
import tart.app.api.StatusCode;
import tart.app.errors.ApplicationExceptions;
import tart.app.errors.GlobalExceptionHandler;

public class PingHandler extends Handler {

    public PingHandler(ObjectMapper objectMapper,
            GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    public String url() {
        // TODO is it better to store or return array?
        return URL_PREFIX + "ping";
    }

    @Override
    public boolean auth() {
        return false;
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        byte[] response;

        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doGet();
//            response = super.writeResponse(e.getBody());
            response = e.getBody().toString().getBytes();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), response.length);
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    private ResponseEntity<String> doGet() {
        var response = "pong";

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.TEXT_HTML), StatusCode.OK);
    }
}