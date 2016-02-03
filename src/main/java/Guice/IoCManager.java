package Guice;
import com.google.inject.Guice;
import com.google.inject.Injector;
/**
 * Created by sevenbits on 03.02.16.
 */
public class IoCManager {
    private Injector injector;

    public IoCManager() {
        this.injector = Guice.createInjector(new GuiceConfigurator());
    }

    public <T> T getInstance(Class<T> variable) {
        return injector.getInstance(variable);
    }
}
