package asia.huayu.gateway;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author RainZiYu
 * @Date 2023/1/12
 */


@SpringBootApplication
public class ServerStartApplication extends SpringBootServletInitializer implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(ServerStartApplication.class, args);
    }

    /**
     * 方法configure
     * 作用为：向Spring注册实现了CommandLineRunner的Run方法的子类的bean
     *
     * @param application
     * @return org.springframework.boot.builder.SpringApplicationBuilder
     * @throws
     * @author User
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        return application.sources(ServerStartApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}