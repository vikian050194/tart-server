package tart.app.dependency;

import com.fasterxml.jackson.databind.ObjectMapper;
import tart.app.errors.GlobalExceptionHandler;
import tart.domain.file.FileRepository;
import tart.domain.file.FileService;
import tart.domain.user.UserRepository;
import tart.domain.user.UserService;

public interface DependencyFactory {

    public ObjectMapper getObjectMapper();

    public GlobalExceptionHandler getErrorHandler();

    public UserRepository getUserRepository();

    public UserService getUserService();

    public FileRepository getImageRepository();

    public FileService getImageService();

}
