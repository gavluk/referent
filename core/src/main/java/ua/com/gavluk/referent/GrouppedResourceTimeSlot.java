package ua.com.gavluk.referent;

import java.util.Arrays;
import java.util.List;

public class GrouppedResourceTimeSlot extends ResourceTimeSlot {

    private final List<ResourceTimeSlot> slots;

    public GrouppedResourceTimeSlot(ResourceTimeSlot... slotsToGroup) {
        this.slots = Arrays.asList(slotsToGroup);
    }
}
