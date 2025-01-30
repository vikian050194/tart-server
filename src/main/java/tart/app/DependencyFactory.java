package tart.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import tart.app.errors.GlobalExceptionHandler;
import tart.data.file.LocalFileRepository;
import tart.data.user.InMemoryUserRepository;
import tart.domain.file.FileRepository;
import tart.domain.file.FileService;
import tart.domain.user.UserRepository;
import tart.domain.user.UserService;

public class DependencyFactory {

    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    private UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private UserService USER_SERVICE = new UserService(USER_REPOSITORY);

    private FileRepository IMAGE_REPOSITORY = new LocalFileRepository();
    private FileService IMAGE_SERVICE = new FileService(IMAGE_REPOSITORY);

    public ObjectMapper getObjectMapper() {
        if (OBJECT_MAPPER == null) {
            OBJECT_MAPPER = new ObjectMapper();
        }

        return OBJECT_MAPPER;
    }

    public GlobalExceptionHandler getErrorHandler() {
        if (GLOBAL_ERROR_HANDLER == null) {
            GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(getObjectMapper());
        }

        return GLOBAL_ERROR_HANDLER;
    }

    public UserRepository getUserRepository() {
        if (USER_REPOSITORY == null) {
            USER_REPOSITORY = new InMemoryUserRepository();
        }

        return USER_REPOSITORY;
    }

    public UserService getUserService() {
        if (USER_SERVICE == null) {
            USER_SERVICE = new UserService(getUserRepository());
        }

        return USER_SERVICE;
    }

    FileRepository getImageRepository() {
        if (IMAGE_REPOSITORY == null) {
            IMAGE_REPOSITORY = new LocalFileRepository();
        }

        return IMAGE_REPOSITORY;
    }

    public FileService getImageService() {
        if (IMAGE_SERVICE == null) {
            IMAGE_SERVICE = new FileService(getImageRepository());
        }

        return IMAGE_SERVICE;
    }

}
