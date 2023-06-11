import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// New imports: --------------
import java.util.LinkedList;
//----------------------------

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler {
    private PriorityQueue<Event> eventQueue;
    private Map<Entity, List<Event>> pendingEvents;
    private double currentTime;

    public PriorityQueue<Event> getEventQueue() {
        return eventQueue;
    }

    public Map<Entity, List<Event>> getPendingEvents() {
        return pendingEvents;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public EventScheduler() {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.currentTime = 0;
    }

    public void scheduleEvent(Entity entity, Action action, double afterPeriod) {
        double time = this.currentTime + afterPeriod;

        Event event = new Event(action, time, entity);

        this.eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList<>());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }

    public void unscheduleAllEvents(Entity entity) {
        List<Event> pending = this.pendingEvents.remove(entity);
    
        if (pending != null) {
            for (Event event : pending) {
                this.eventQueue.remove(event);
            }
        }
    }

    public void removePendingEvent(Event event) {
        List<Event> pending = this.getPendingEvents().get(event.entity);
    
        if (pending != null) {
            pending.remove(event);
        }
    }

    public void updateOnTime(double time) {
        double stopTime = this.getCurrentTime() + time;
        while (!this.getEventQueue().isEmpty() && this.getEventQueue().peek().time <= stopTime) {
            Event next = this.getEventQueue().poll();
            this.removePendingEvent(next);
            this.setCurrentTime(next.time);
            next.action.executeAction(this); // changed to non-static
        }
        this.setCurrentTime(stopTime);
    }


}
