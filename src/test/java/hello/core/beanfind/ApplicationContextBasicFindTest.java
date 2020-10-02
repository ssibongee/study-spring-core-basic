package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    public void findBeanByName() throws Exception {
        // given
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        // when

        // then
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    public void findBeanByType() throws Exception {
        // given
        MemberService memberService = ac.getBean(MemberService.class);
        // when

        // then
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    // 역할과 구현을 구분해서 역할에만 의존하도록 해야하므로 구현에 의존한 이 코드는 별로 좋은코드가 아니다.
    @Test
    @DisplayName("구체 타입으로 조회")
    public void findBeanByName2() throws Exception {
        // given
        MemberService memberService = ac.getBean(MemberServiceImpl.class);
        // when

        // then
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    @Test
    @DisplayName("빈 이름으로 조회 X")
    public void findBeanByNameX() throws Exception {
        // given
        // when

        // then
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("XXXXX", MemberService.class));
    }
}
