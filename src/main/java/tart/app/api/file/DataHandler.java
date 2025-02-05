package tart.app.api.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import tart.app.api.*;
import tart.app.errors.*;
import tart.domain.file.FileService;

public class DataHandler extends Handler {

    private final FileService fileService;

    public DataHandler(
            FileService fileService,
            ObjectMapper objectMapper,
            GlobalExceptionHandler exceptionHandler
    ) {
        super(objectMapper, exceptionHandler);
        this.fileService = fileService;
    }

    @Override
    public String url() {
        // TODO is it better to store or return array?
        return URL_PREFIX + "data";
    }

    @Override
    public boolean auth() {
        return true;
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        byte[] response;

        if ("GET".equals(exchange.getRequestMethod())) {
            var e = doGet(exchange.getRequestURI());
            response = e.getBody();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), response.length);
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }

        // TODO is it needed?
        exchange.close();
    }

    private ResponseEntity<byte[]> doGet(URI uri) throws IOException {
        var params = splitQuery(uri.getRawQuery());
        // TODO return 400 if dir is empty
        var dir = params.getOrDefault("dir", List.of()).stream().toList();
        // TODO return 400 is name is empty
        var name = params.get("name").stream().findFirst().orElseThrow();
        var file = fileService.getFileData(dir, name);
        return new ResponseEntity<>(file.getData(),
                getHeaders(Constants.CONTENT_TYPE, Constants.IMAGE_JPEG), StatusCode.OK);

    }
}
