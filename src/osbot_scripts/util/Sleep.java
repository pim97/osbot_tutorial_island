package osbot_scripts.util;

import org.osbot.rs07.utility.ConditionalSleep;

public final class Sleep extends ConditionalSleep {

    private final boolean condition;

    public Sleep(final boolean condition, final int timeout) {
        super(timeout);
        this.condition = condition;
    }

    public Sleep(final boolean condition, final int timeout, final int interval) {
        super(timeout, interval);
        this.condition = condition;
    }

    @Override
    public final boolean condition() throws InterruptedException {
        return condition;
    }

    public static boolean sleepUntil(final boolean condition, final int timeout) {
        return new Sleep(condition, timeout).sleep();
    }

    public static boolean sleepUntil(final boolean condition, final int timeout, final int interval) {
        return new Sleep(condition, timeout, interval).sleep();
    }
}