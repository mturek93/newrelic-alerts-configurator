package com.ocadotechnology.newrelic.alertsconfigurator.configuration.condition;

import com.ocadotechnology.newrelic.alertsconfigurator.configuration.condition.terms.TermsConfiguration;

import java.util.Collection;

/**
 * Alerts external service condition configuration.
 * Implementations:
 * <ul>
 *     <li>{@link ApmExternalServiceCondition}</li>
 * </ul>
 */
public interface ExternalServiceCondition {
    /**
     * Returns condition type. Each ExternalServiceCondition implementation should have unique type.
     *
     * @return condition type
     */
    ExternalServiceConditionType getType();

    /**
     * Returns condition type in string format.
     *
     * @return condition type
     */
    String getTypeString();

    /**
     * Returns name of the condition.
     *
     * @return condition name
     */
    String getConditionName();

    /**
     * Returns if condition is enabled.
     *
     * @return {@code true} if condition is enabled, {@code false} otherwise
     */
    boolean isEnabled();

    /**
     * Returns collection of entities for which this condition is applied.
     * The type of the entity depends on concrete condition implementation. This can be an application
     * name or host name for example.
     * <p>
     * If entity with given name does not exist an exception will be thrown.
     *
     * @return entity names
     */
    Collection<String> getEntities();

    /**
     * Returns URL of the external service to be monitored. This string must not include the protocol.
     *
     * @return external service URL
     */
    String getExternalServiceUrl();

    /**
     * Returns metric used in given condition.
     *
     * @return metric
     */
    String getMetric();

    /**
     * Returns the runbook URL to display in notifications.
     *
     * @return runbook URL
     */
    String getRunBookUrl();

    /**
     * Returns collection of terms used for alerts condition.
     *
     * @return terms
     */
    Collection<TermsConfiguration> getTerms();
}
