package tart.app.api.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import tart.app.api.*;
import tart.app.errors.*;
import tart.domain.file.DateFilter;
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
        // TODO extract magic string
        var filter = new DateFilter();
        var yearsFilterValues = params.getOrDefault("years", Collections.<String>emptyList()).stream().map(Integer::valueOf).toList();
        filter.years.addAll(yearsFilterValues);
        var monthsFilterValues = params.getOrDefault("months", Collections.<String>emptyList()).stream().map(Integer::valueOf).toList();
        filter.months.addAll(monthsFilterValues);
        var daysFilterValues = params.getOrDefault("days", Collections.<String>emptyList()).stream().map(Integer::valueOf).toList();
        filter.days.addAll(daysFilterValues);

        var fullPath = uri.getPath();
        var dirPath = fullPath.substring(url().length());
        var delimiter = "/";
        var path = List.of(dirPath.split(delimiter)).stream().filter(p -> !p.isEmpty()).toList();

        var dirs = fileService.getDirectories(path);
        var files = fileService.getFiles(path, filter);
        var years = fileService.getYears(path, filter);
        var months = fileService.getMonths(path, filter);
        var days = fileService.getDays(path, filter);
        var r = new InfoResponse(dirs, files);
        r.years.addAll(years);
        r.months.addAll(months);
        r.days.addAll(days);
        return new ResponseEntity<>(r,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }
}
