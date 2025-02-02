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

public class InfoHandler extends Handler {

    private final FileService fileService;

    public InfoHandler(
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
        return URL_PREFIX + "info";
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

    private ResponseEntity<InfoResponse> doGet(URI uri) throws IOException {
        var params = splitQuery(uri.getRawQuery());
        var dir = params.getOrDefault("dir", List.of()).stream().toList();

        if (dir.isEmpty()) {
            var dirs = fileService.getDirectories().stream().map(d -> d.getFullName()).toList();
            return new ResponseEntity<>(new InfoResponse(dirs),
                    getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
        }

        var dirs = fileService.getDirectories(dir).stream().map(d -> d.getFullName()).toList();
        return new ResponseEntity<>(new InfoResponse(dirs),
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }
}
