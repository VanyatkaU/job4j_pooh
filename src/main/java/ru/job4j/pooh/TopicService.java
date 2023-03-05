package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.*;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topic = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscriber;
        if (POST.equals(req.httpRequestType())) {
            subscriber = topic.get(req.getSourceName());
            if (subscriber.equals(topic.get(req.getSourceName()))) {
                for (String keys : subscriber.keySet()) {
                    subscriber.get(keys).add(req.getParam());
                }
            }
        } else if (GET.equals(req.httpRequestType())) {
            topic.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            subscriber = topic.get(req.getSourceName());
            if (subscriber.get(req.getParam()) != null) {
                text = subscriber.get(req.getParam()).poll();
                status = STATUSTEXT.equals(text) ? STATUS404 : STATUS200;
            }
            subscriber.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
        }
        return new Resp(text, status);
    }
}