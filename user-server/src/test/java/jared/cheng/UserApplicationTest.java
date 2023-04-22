package jared.cheng;

import jared.cheng.resource.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.util.List;
import java.util.Map;

/**
 * @author jared
 * @since 2023/3/12
 */
@SpringBootTest(classes = UserApplication.class)
@Import({TestChannelBinderConfiguration.class})
public class UserApplicationTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Test
    void testSendMessage() {
        final UserRequest u1 = new UserRequest();
        u1.setScore(30);
        u1.setUserName("jared");

        final UserRequest u2 = new UserRequest();
        u2.setScore(100);
        u2.setUserName("angel");

        final MessageHeaders headers = new MessageHeaders(Map.of());
        final MappingJackson2MessageConverter msgConverter = new MappingJackson2MessageConverter();

        input.send(msgConverter.toMessage(List.of(u1, u2), headers));
    }
}
