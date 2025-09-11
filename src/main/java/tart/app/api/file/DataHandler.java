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
    }

    private String getMimeType(List<String> path) {
        var fileType = fileService.getFileType(path);
        // TODO switch vs. rule switch? pros and cons
        switch (fileType) {
            case JPEG:
                return Constants.IMAGE_JPEG;
            case PNG:
                return Constants.IMAGE_PNG;
            case MP4:
                return Constants.VIDEO_MP4;
            default:
                throw new AssertionError();
        }
    }

    private ResponseEntity<byte[]> doGet(URI uri) throws IOException {
        var fullPath = uri.getPath();
        var filePath = fullPath.substring(url().length());
        var delimiter = "/";
        var path = List.of(filePath.split(delimiter)).stream().filter(p -> !p.isEmpty()).toList();
        var file = fileService.getFileData(path);
        var mimeType = getMimeType(path);
        return new ResponseEntity<>(file,
                getHeaders(Constants.CONTENT_TYPE, mimeType), StatusCode.OK);

    }
}
