package com.example.demo.util.event;

import com.example.demo.MyUI;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

public class MyEventBus implements SubscriberExceptionHandler {

    private final EventBus bus = new EventBus(this);

    public static void post(final Object object) {
        MyUI.getEventBusUI().bus.post(object);
    }

    public static void register(final Object object) {
        MyUI.getEventBusUI().bus.register(object);
    }

    public static void unregister(final Object object) {
        MyUI.getEventBusUI().bus.unregister(object);
    }

    @Override
    public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        throwable.printStackTrace();
    }
}
