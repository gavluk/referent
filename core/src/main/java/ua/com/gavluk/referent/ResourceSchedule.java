package ua.com.gavluk.referent;

import java.util.ArrayList;
import java.util.List;

public class ResourceSchedule {

    private List<ResourceTimeSlot> slots = new ArrayList<>();

    ResourceSchedule(List<ResourceTimeSlot> slots) {
        this.slots = slots;
    }

    public ResourceTimeSlot getSingleSlot(int i) {
        return this.slots.get(i);
    }

    public ResourceTimeSlot getRangedSlot(int fromInclusive, int toInclusive) {
        return new RangedTimeSlot(this.slots.subList(fromInclusive, toInclusive));
    }
}
