package config.web;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/${env}.properties",
        "classpath:config/local.properties"
})
public interface LaunchConfig extends Config {
    @Key("browserName")
    BrowserName browserName();

    @Key("browserVersion")
    String browserVersion();

    @Key("browserSize")
    String browserSize();

    @Key("baseURL")
    String baseURL();

    @Key("isRemote")
    boolean isRemote();

    @Key("remoteURL")
    String remoteURL();

    @Key("pageLoadStrategy")
    String pageLoadStrategy();
}
