package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        /* Режим topic. Подписываемся на топик weather. client407. */
        topicService.process(
                new Req(Req.GET, PoohServer.TOPIC, "weather", paramForSubscriber1)
        );
        /* Режим topic. Добавляем данные в топик weather. */
        topicService.process(
                new Req(Req.POST, PoohServer.TOPIC, "weather", paramForPublisher)
        );
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
        Resp result1 = topicService.process(
                new Req(Req.GET, PoohServer.TOPIC, "weather", paramForSubscriber1)
        );
        /* Режим topic. Пытаемся забрать данные из индивидуальной очереди в топике weather. Очередь client6565.
        Эта очередь отсутствует, т.к. client6565 еще не был подписан, поэтому он получит пустую строку. Будет создана индивидуальная очередь для client6565 */
        Resp result2 = topicService.process(
                new Req(Req.GET, PoohServer.TOPIC, "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");
    }

    @Test
    public void whenTopicErrorGet() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber = "client407";
        /* Режим topic. Подписываемся на топик weather. client407. */
        topicService.process(
                new Req(Req.GET, PoohServer.TOPIC, "weather", paramForSubscriber)
        );
        /* Режим topic. Добавляем данные в топик weather. */
        topicService.process(
                new Req(Req.POST, PoohServer.TOPIC, "weather", paramForPublisher)
        );
        /* Режим topic. Ошибка запроса. Очередь client407. */
        Resp result = topicService.process(
                new Req("", PoohServer.TOPIC, "weather", paramForSubscriber)
        );
        assertThat(result.text()).isEqualTo("");
    }
}