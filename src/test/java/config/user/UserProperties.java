package config.user;

import org.aeonbits.owner.ConfigFactory;

public class UserProperties {
    private static final UserConfig CONFIG = ConfigFactory.create(UserConfig.class, System.getProperties());

    public static final String USERNAME = CONFIG.username(),
            PASSWORD = CONFIG.password();
}
