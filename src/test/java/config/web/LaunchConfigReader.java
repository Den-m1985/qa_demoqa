package config.web;

import org.aeonbits.owner.ConfigFactory;

public enum LaunchConfigReader {

    Instance;

    private static final LaunchConfig CONFIG = ConfigFactory.create(LaunchConfig.class, System.getProperties());

    public LaunchConfig read() {
        return CONFIG;
    }

}
