package scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    public void prototypeFind() throws Exception {
        // given
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeBean.class);
        // when
        PrototypeBean prototypeBean1 = context.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);


        PrototypeBean prototypeBean2 = context.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(2);
        // then

    }

    @Test
    public void singletonClientUsePrototype() throws Exception {
        // given
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = context.getBean(ClientBean.class);
        int count1 = clientBean1.logic();

        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = context.getBean(ClientBean.class);
        int count2 = clientBean2.logic();

        assertThat(count2).isEqualTo(1);
        // when

        // then

    }

    @Scope("singleton")
    static class ClientBean {

        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        private Provider<PrototypeBean> prototypeBeanProvider;


        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }


    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
