package lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() throws Exception {
        // given
        // close 를 위해서 ConfigurableApplicationContext를 사용
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient networkClient = context.getBean(NetworkClient.class);
        context.close();
        // when

        // then

    }

    @Configuration
    static class LifeCycleConfig {

        // destroyMethod를 지정하지 않아도 스프링 빈으로 등록하면 자동으로 shutdown이나 close라는 종료메소드를 추론하여
        // 있는 경우 호출한다 추론기능을 사용하고 싶지 않은경우 destroyMethod=""로 지정하면 된다.
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://spring.io");
            return networkClient;
        }
    }
}
