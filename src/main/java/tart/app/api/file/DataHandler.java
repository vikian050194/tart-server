package tart.app.api.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import tart.app.api.*;
import tart.app.errors.*;
import tart.domain.file.DirectoryInfo;
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
            response = super.writeResponse(e.getBody());
            // TODO split file handler that returns bytes and all other endpoints that return JSON
//            response = e.getBody();
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

    private ResponseEntity<List<DirectoryInfo>> doGet(URI uri) throws IOException {
        var path = uri.getRawPath();
        var foo = path.substring(url().length());

        switch (foo) {
            case "": {
                var params = splitQuery(uri.getRawQuery());
                var dir = params.getOrDefault("dir", List.of()).stream().toList();
                var name = params.get("name").stream().findFirst().orElseThrow();
//                var name = "";

                if (dir.isEmpty()) {
                    var dirs = fileService.getDirectories();

                    return new ResponseEntity<>(dirs,
                            getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
                }

                // TODO name is optional
//                var file = fileService.getFileData(dir, name);
//
//                return new ResponseEntity<>(file.getData(),
//                        getHeaders(Constants.CONTENT_TYPE, Constants.IMAGE_JPEG), StatusCode.OK);
                var dirs = fileService.getDirectories();

                return new ResponseEntity<>(dirs,
                        getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

            }
            default:
                throw new AssertionError();
        }
    }
}
