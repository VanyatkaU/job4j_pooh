package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.*;

public class QueueService implements Service {

    private final ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
        } else if (GET.equals(req.httpRequestType())) {
            text = queue.get(req.getSourceName()).poll();
            status = STATUSTEXT.equals(text) ? STATUS404 : STATUS200;
        }
        return new Resp(text, status);
    }
}