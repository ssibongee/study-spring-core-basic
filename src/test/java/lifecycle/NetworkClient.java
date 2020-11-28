package lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 인터페이스를 이용하는 방법은 잘 사용하지 않는다
// InitializingBean : 빈 초기화 콜백을 지원
// DisposableBean : 빈 소멸전 콜백을 지원
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect");
    }

    public void call(String message) {
        System.out.println("call : " + url + " message : " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }

    // 의존관계 주입이 끝나면 수행할 것
    @PostConstruct
    public void init() throws Exception {
        System.out.println("init");
        connect();
        call("초기 연결 메세지");
    }

    // 빈이 종료될 때 수행
    @PreDestroy
    public void close() throws Exception {
        System.out.println("close");
        disconnect();
    }
}
