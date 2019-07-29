package ua.com.gavluk.referent;

import ua.com.gavluk.commons.EnvironmentContext;
import ua.com.gavluk.commons.PageableEntityList;
import ua.com.gavluk.commons.SimpleEntityFilter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceService {

    private final User user;
    private final EnvironmentContext context;
    private Booking tmpBooking; // todo: delete it when implement all methods!!!

    ResourceService(User actingUser, EnvironmentContext context) {
        this.user = actingUser;
        this.context = context;
    }

    public Domain createDomain(String domainName, String domainTitle) {
        // todo: create and persist domain owned by user
        return new Domain(this.user, domainName, domainTitle);
    }

    public void addModeratorToAllResources(User moderator, Domain domain) {
        // todo: assign moderator to whole domain (all domain resources)
    }

    public Resource createResource(Domain domain, String resourceURI, String resourceTitle) {
        // todo: create if not exists with this uri in this domain
        return new SimpleResource(domain, resourceURI, resourceTitle);
    }

    public void addModeratorToResource(User moderator, Resource resource) {
        // todo: implement persisted binding this resource to moderator (grant moderator)
    }

    public ResourceSchedule availableTimeSlots(Resource resource, Instant from, Instant to) {
        // todo: select all timeslots according working time, weekends, calendard, other resource options will be available
        ArrayList<ResourceTimeSlot> result = new ArrayList<>();
        for (int i=0; i<10; i++)
            result.add(new ResourceTimeSlot());
        return new ResourceSchedule(result);
    }

    public Booking requestBooking(Resource resource, ResourceTimeSlot timeSlot, BookingOptions bookingOptions) {
        // todo: make booking request as Booking with status "CLAIMED" or "REQUESTED"
        this.tmpBooking = new Booking(resource, timeSlot, bookingOptions);
        return this.tmpBooking;
    }

    public PageableEntityList<Booking> findBookingRequests(Resource resource1, SimpleEntityFilter simpleEntityFilter) {
        // todo: find all booking of this resource, which are in "REQUESTED" state and actual (not in past)
        return new PageableEntityList<>(Arrays.asList(this.tmpBooking));
    }

    public Booking confirmBooking(Booking booking, CommentsForm comments) {
        // todo: make booking status confirmed, add to history with comments
        return booking;
    }

    public Booking requestRevoking(Booking booking, CommentsForm comments) {
        // todo: check the user is booking author or moderator and make resource status "REVOKE_REQUESTED", add to history with comments
        return booking;
    }

    public Booking acknowledgeRevoking(Booking booking, CommentsForm commentsForm) {
        // todo: check if user is moderator and apply revoking making status to "REVOKED"
        return booking;
    }

    public Resource setOption(Resource resource1, ResourceOptions autoAcknowledgeRevoking) {
        // todo: set this option to resource having this option
        return resource1;
    }
}
