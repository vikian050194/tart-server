package tart.app.api;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import tart.app.App;
import tart.app.DependencyFactory;

public class BaseApiTest {

    protected App app;
    final int PORT = 9090;
    final App.RunMode MODE = App.RunMode.DEV;
    protected String baseAddress = "http://localhost:%d/api".formatted(PORT);

    @BeforeEach
    public void initializeApp() throws IOException {
        app = new App(PORT, MODE);
        var dependencyFactory = new DependencyFactory();
        app.init(dependencyFactory);
        app.start();
    }

    @AfterEach
    public void stopApp() {
        app.stop(0);
        app = null;
    }
}
