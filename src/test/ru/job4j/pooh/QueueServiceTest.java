package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req(Req.POST, PoohServer.QUEUE, "weather", paramForPostMethod)
        );
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = queueService.process(
                new Req(Req.GET, PoohServer.QUEUE, "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenPostThenGetEmpty() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req(Req.POST, PoohServer.QUEUE, "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("", PoohServer.QUEUE, "weather", null)
        );
        assertThat(result.text()).isEqualTo("");
    }

    @Test
    public void whenPostThenGetTwo() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req(Req.POST, PoohServer.QUEUE, "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req(Req.GET, PoohServer.QUEUE, "weather", null)
        );
        Resp result1 = queueService.process(
                new Req(Req.GET, PoohServer.QUEUE, "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
        assertThat(result1.text()).isEqualTo(null);
    }
}
