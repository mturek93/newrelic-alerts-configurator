package com.ocadotechnology.newrelic.alertsconfigurator.configuration.channel;

import com.ocadotechnology.newrelic.alertsconfigurator.configuration.channel.internal.PagerDutyChannelTypeSupport;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * PagerDuty channel configuration.
 * Configuration parameters:
 * <ul>
 *     <li>{@link #channelName}</li>
 *     <li>{@link #serviceKey}</li>
 * </ul>
 */
@Builder
@Getter
public class PagerDutyChannel implements Channel {
    private final ChannelType type = ChannelType.PAGERDUTY;
    /**
     * Name of your alerts channel
     */
    @NonNull
    private String channelName;
    /**
     * PagerDuty service key
     */
    @NonNull
    private String serviceKey;

    private final ChannelTypeSupport channelTypeSupport = new PagerDutyChannelTypeSupport(this);

    @Override
    public ChannelTypeSupport getChannelTypeSupport() {
        return channelTypeSupport;
    }
}
