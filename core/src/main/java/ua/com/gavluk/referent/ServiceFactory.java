package ua.com.gavluk.referent;

import ua.com.gavluk.commons.EnvironmentContext;
import ua.com.gavluk.commons.exception.AuthenticationException;
import ua.com.gavluk.commons.exception.NotFoundException;
import ua.com.gavluk.commons.security.AnonymousCredentials;
import ua.com.gavluk.commons.security.Credentials;
import ua.com.gavluk.commons.security.CredentialsChecker;
import ua.com.gavluk.commons.security.SuperAdminCredentials;
import ua.com.gavluk.referent.resource.ResourceService;
import ua.com.gavluk.referent.user.User;
import ua.com.gavluk.referent.user.UserRepository;
import ua.com.gavluk.referent.user.UserService;
import ua.com.gavluk.referent.user.creds.CredentialsCheckerFactory;
import ua.com.gavluk.referent.user.creds.CredentialsRecord;
import ua.com.gavluk.referent.user.creds.CredentialsRecordRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Root factory for creating any service in behalf of some system actor.
 * There are two actor types: SuperAdmin and User
 */
public class ServiceFactory {

    public static final Credentials ANONYMOUS_CREDS = new ReferentAnonymousCredentials();
    public static final Credentials SUPER_ADMIN_CREDS = new ReferentSuperAdminCredentials();

    private static final User ANONYMOUS_USER = new AnonymousUser();
    private static final User SUPER_ADMIN_USER = new SuperAdminUser();

    private CredentialsRecordRepository credentialsRecordRepo;
    private UserRepository userRepo;
    private final CredentialsCheckerFactory credentialsCheckerFactory;

    public ServiceFactory(
            CredentialsRecordRepository credentialsRecordRepo,
            UserRepository userRepo,
            CredentialsCheckerFactory credentialsCheckerFactory
    )
    {
        this.credentialsRecordRepo = credentialsRecordRepo;
        this.userRepo = userRepo;
        this.credentialsCheckerFactory = credentialsCheckerFactory;
    }


    public UserService buildUserServiceFor(Credentials credentials, EnvironmentContext context) {

        if (credentials instanceof ReferentAnonymousCredentials)
            return new UserService(ANONYMOUS_USER, context);
        else if (credentials instanceof ReferentSuperAdminCredentials) {
            return new UserService(SUPER_ADMIN_USER, context);
        }
        else {
            // TODO: check credentials, then if ok, return userservice
            return new UserService(SUPER_ADMIN_USER, context);
        }

    }

    public ResourceService buildResourceServiceFor(Credentials activeUserCredentials, EnvironmentContext context)
            throws AuthenticationException, NotFoundException {

        if (activeUserCredentials instanceof AnonymousCredentials)
            return new ResourceService(new AnonymousUser(), context);
        else if (activeUserCredentials instanceof SuperAdminCredentials)
            return new ResourceService(new SuperAdminUser(), context);

        LoginAndPasswordCredentials loginAndPassword = (LoginAndPasswordCredentials) activeUserCredentials;
        User owner = this.checkCredentials(activeUserCredentials, context);
        return new ResourceService(owner, context);
    }

    private User checkCredentials(Credentials creds, EnvironmentContext context)
            throws AuthenticationException, NotFoundException {


        Optional<CredentialsRecord> foundCredRec = this.credentialsRecordRepo.findOneByTypeAndLoginAndActive(
                creds.getClass().getSimpleName(),
                creds.getSubjectIdentifier().toString(),
                true
        );

        if (!foundCredRec.isPresent())
            throw new NotFoundException(User.class, creds.getSubjectIdentifier().toString());

        // todo: check credentials and if ok, build ResourceService
        CredentialsChecker checker = this.credentialsCheckerFactory.buildCheckerFor(foundCredRec.get());
        checker.check(creds);

        Optional<User> foundUser = this.userRepo.findById(foundCredRec.get().getUserId());

        return foundUser.orElseThrow(() -> new NotFoundException(User.class, foundCredRec.get().getUserId()));



    }

}
