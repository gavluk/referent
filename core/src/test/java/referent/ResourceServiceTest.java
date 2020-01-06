package referent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ua.com.gavluk.commons.EnvironmentContext;
import ua.com.gavluk.commons.PageableEntityList;
import ua.com.gavluk.commons.SimpleEntityFilter;
import ua.com.gavluk.commons.exception.AuthenticationException;
import ua.com.gavluk.commons.exception.NotFoundException;
import ua.com.gavluk.commons.security.Credentials;
import ua.com.gavluk.referent.*;
import ua.com.gavluk.referent.resource.*;
import ua.com.gavluk.referent.user.*;
import ua.com.gavluk.referent.user.creds.CredentialsCheckerFactory;
import ua.com.gavluk.referent.user.creds.CredentialsRecord;
import ua.com.gavluk.referent.user.creds.CredentialsRecordRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class ResourceServiceTest {

    private ResourceService service;
    private ServiceFactory serviceFactory;

    @BeforeEach
    void beforeAll() {
        this.serviceFactory = new ServiceFactory(new CredentialsRecordRepository() {
            @Override
            public Optional<CredentialsRecord> findOneByTypeAndLoginAndActive(String type, String login, boolean active) {
                return Optional.empty();
            }
        },
        new UserRepository() {
            @Override
            public Optional<User> findById(UUID userId) {
                return Optional.empty();
            }
        },
        new CredentialsCheckerFactory()
        );
    }

    @Test
    void test_typical_resource_lifecycle() throws NotFoundException, AuthenticationException {

        UserService userService = this.serviceFactory.buildUserServiceFor(ServiceFactory.SUPER_ADMIN_CREDS, new EnvironmentContext());

        // User registered in system and setup login-password authentication
        // (could be oauth or two-step like: claimAuth -> addAuth)
        User owner = userService.createUser("john.snow");
        Credentials ownerCredentials = new LoginAndPasswordCredentials("john.snow", "Winter_is_coming_2019");
        userService.addAuthentication(owner, ownerCredentials);

        // Building resource service
        ResourceService resourceService = serviceFactory.buildResourceServiceFor(ownerCredentials, new EnvironmentContext());

        // User creates own domain of resources
        Domain domain = resourceService.createDomain("winterfell", "Resources of Winterfell");

        // Some users wannabe moderators
        User moder1 = userService.createUser("sansa.stark");
        Credentials moder1Creds = new LoginAndPasswordCredentials(moder1.getUsername(), "Bolton_must_die_2019");
        userService.addAuthentication(owner, moder1Creds);
        User moder2 = userService.createUser("aria.stark");
        Credentials moder2Creds = new LoginAndPasswordCredentials(moder2.getUsername(), "A_girl_has_no_name_2019");
        userService.addAuthentication(owner, moder2Creds);

        // Adding Sansa as all-resources-moderator
        resourceService.addModeratorToAllResources(moder1, domain);

        // Creating copuple of resources for rent: North and South towers
        Resource resource1 = resourceService.createResource(domain, "north.tower", "North Tower");
        Resource resource2 = resourceService.createResource(domain, "south.tower", "South Tower");

        // Aria wannabe moderator only for North Tower
        // Sansa do not need this granting cause she is all-domain-moderator
        resourceService.addModeratorToResource(moder2, resource1);

        // Some registered user "guest1" wanna rent some resources
        User guest1 = userService.createUser("guest1");
        Credentials guest1Creds = new LoginAndPasswordCredentials(guest1, "I_am_guest_1");

        ResourceService guest1ResourceService = serviceFactory.buildResourceServiceFor(guest1Creds, new EnvironmentContext());
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
        ResourceService moder1ResourceService = serviceFactory.buildResourceServiceFor(moder1Creds, new EnvironmentContext());
        PageableEntityList<Booking> claims = moder1ResourceService.findBookingRequests(resource1, new SimpleEntityFilter()); // todo: standard date-range & pagination filtering
        booking = moder1ResourceService.confirmBooking(claims.getList().get(0), new CommentsForm("Ok, I confirm"));

        // User can request booking revoke
        guest1ResourceService.requestRevoking(booking, new CommentsForm("Sorry, I changed my mind"));

        // Moderator can mark revoked booking as acknowledged
        moder1ResourceService.acknowledgeRevoking(booking, new CommentsForm("It's a pity but ok"));

        // Owner could set revoking auto-acknowledge options to avoid manual acknowledgement every time when user revokes resource booking
        resourceService.setOption(resource1, ResourceOptions.AUTO_ACKNOWLEDGE_REVOKING);

        // Anonymous could do all the same using other example of service
        ResourceService anonymousResourceService = serviceFactory.buildResourceServiceFor(ServiceFactory.ANONYMOUS_CREDS, new EnvironmentContext());

        //Resource x = new Resource.Builder().build();
        //this.service.createResource()

    }

    @Test
    @Disabled
    void test_user_creation() {

        UserService userService = this.serviceFactory.buildUserServiceFor(ServiceFactory.SUPER_ADMIN_CREDS, new EnvironmentContext());

        User x = userService.createUser("john.snow");
        assertNotNull(x.getId());

        Optional<User> y = userService.findById(x.getId());
        assertTrue(y.isPresent());

        assertNotNull(y.get().getId());
        assertEquals(x.getId(), y.get().getId());
        assertEquals(x.getUsername(), y.get().getUsername());

    }

}
