package tart.app.dependency;

import com.fasterxml.jackson.databind.ObjectMapper;
import tart.app.errors.GlobalExceptionHandler;
import tart.data.file.LocalFileRepository;
import tart.data.user.InMemoryUserRepository;
import tart.domain.file.FileRepository;
import tart.domain.file.FileService;
import tart.domain.user.UserRepository;
import tart.domain.user.UserService;

public class DefaultDependencyFactory implements DependencyFactory {

    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    private UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private UserService USER_SERVICE = new UserService(USER_REPOSITORY);

    private FileRepository IMAGE_REPOSITORY = new LocalFileRepository();
    private FileService IMAGE_SERVICE = new FileService(IMAGE_REPOSITORY);

    @Override
    public ObjectMapper getObjectMapper() {
        if (OBJECT_MAPPER == null) {
            OBJECT_MAPPER = new ObjectMapper();
        }

        return OBJECT_MAPPER;
    }

    @Override
    public GlobalExceptionHandler getErrorHandler() {
        if (GLOBAL_ERROR_HANDLER == null) {
            GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(getObjectMapper());
        }

        return GLOBAL_ERROR_HANDLER;
    }

    @Override
    public UserRepository getUserRepository() {
        if (USER_REPOSITORY == null) {
            USER_REPOSITORY = new InMemoryUserRepository();
        }

        return USER_REPOSITORY;
    }

    @Override
    public UserService getUserService() {
        if (USER_SERVICE == null) {
            USER_SERVICE = new UserService(getUserRepository());
        }

        return USER_SERVICE;
    }

    @Override
    public FileRepository getFileRepository() {
        if (IMAGE_REPOSITORY == null) {
            IMAGE_REPOSITORY = new LocalFileRepository();
        }

        return IMAGE_REPOSITORY;
    }

    @Override
    public FileService getFileService() {
        if (IMAGE_SERVICE == null) {
            IMAGE_SERVICE = new FileService(getFileRepository());
        }

        return IMAGE_SERVICE;
    }

}
