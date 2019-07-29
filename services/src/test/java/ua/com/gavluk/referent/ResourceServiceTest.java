package ua.com.gavluk.referent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.gavluk.commons.EnvironmentContext;
import ua.com.gavluk.commons.PageableEntityList;
import ua.com.gavluk.commons.SimpleEntityFilter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ResourceServiceTest {

    private ResourceService service;
    private UserService userService;

    @BeforeEach
    void beforeAll() {
        this.userService = new UserService();
    }

    @Test
    void test_typical_resource_lifecycle() {


        // User registered in system and setup login-password authentication
        // (could be oauth or two-step like: claimAuth -> addAuth)
        User owner = this.userService.createUser("john.snow");
        Credentials ownerCredentials = new LoginAndPasswordCredentials("john.snow", "Winter_is_coming_2019");
        this.userService.addAuthentication(owner, ownerCredentials);

        // Building resource service
        ResourceService resourceService = this.userService.buildResourceServiceFor(ownerCredentials, new EnvironmentContext());

        // User creates own domain of resources
        Domain domain = resourceService.createDomain("winterfell", "Resources of Winterfell");

        // Some users wannabe moderators
        User moder1 = this.userService.createUser("sansa.stark");
        Credentials moder1Creds = new LoginAndPasswordCredentials(moder1.getUsername(), "Bolton_must_die_2019");
        this.userService.addAuthentication(owner, moder1Creds);
        User moder2 = this.userService.createUser("aria.stark");
        Credentials moder2Creds = new LoginAndPasswordCredentials(moder2.getUsername(), "A_girl_has_no_name_2019");
        this.userService.addAuthentication(owner, moder2Creds);

        // Adding Sansa as all-resources-moderator
        resourceService.addModeratorToAllResources(moder1, domain);

        // Creating copuple of resources for rent: North and South towers
        Resource resource1 = resourceService.createResource(domain, "north.tower", "North Tower");
        Resource resource2 = resourceService.createResource(domain, "south.tower", "South Tower");

        // Aria wannabe moderator only for North Tower
        // Sansa do not need this granting cause she is all-domain-moderator
        resourceService.addModeratorToResource(moder2, resource1);

        // Some registered user "guest1" wanna rent some resources
        User guest1 = this.userService.createUser("guest1");
        Credentials guest1Creds = new LoginAndPasswordCredentials(guest1, "I_am_guest_1");
        // As well some ad-hoc anonymous visitor could ask resource for rent as well
        User anonymous = this.userService.createAnonymous();


        ResourceService guest1ResourceService = this.userService.buildResourceServiceFor(guest1Creds, new EnvironmentContext());
        // get all time-slots during next 30 days
        ResourceSchedule res1Schedule = guest1ResourceService.availableTimeSlots(
                resource1,
                Instant.now(),
                Instant.now().plus(30, ChronoUnit.DAYS)
        );
        ResourceTimeSlot slotSingle = res1Schedule.getSingleSlot(2); // get third slot
        ResourceTimeSlot slotBlock = res1Schedule.getRangedSlot(3,5); // get slots 3,4 and 5 as one slot

        Booking booking = guest1ResourceService.requestBooking(resource1, new GrouppedResourceTimeSlot(slotSingle, slotBlock), new BookingOptions("Plz, call me asap"));

        // Moderator 1 confirms booking
        ResourceService moder1ResourceService = this.userService.buildResourceServiceFor(moder1Creds, new EnvironmentContext());
        PageableEntityList<Booking> claims = moder1ResourceService.findBookingRequests(resource1, new SimpleEntityFilter()); // todo: standard date-range & pagination filtering
        booking = moder1ResourceService.confirmBooking(claims.getList().get(0), new CommentsForm("Ok, I confirm"));

        // User can request booking revoke
        guest1ResourceService.requestRevoking(booking, new CommentsForm("Sorry, I changed my mind"));

        // Moderator can mark revoked booking as acknowledged
        moder1ResourceService.acknowledgeRevoking(booking, new CommentsForm("It's pitty but ok"));

        // Owner could set revoking auto-acknowledge options to avoid manual acknowledgement every time when user revokes resource booking
        resourceService.setOption(resource1, ResourceOptions.AUTO_ACKNOWLEDGE_REVOKING);

        // Anonymous could do all the same using other example of service
        ResourceService anonymousResourceService = this.userService.buildResourceServiceFor(Credentials.ANONYMOUS, new EnvironmentContext());

        //Resource x = new Resource.Builder().build();
        //this.service.createResource()


    }

}
