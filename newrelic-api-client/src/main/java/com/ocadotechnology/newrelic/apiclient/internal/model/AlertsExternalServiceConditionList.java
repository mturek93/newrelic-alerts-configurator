package com.ocadotechnology.newrelic.apiclient.internal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocadotechnology.newrelic.apiclient.model.conditions.external.AlertsExternalServiceCondition;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class AlertsExternalServiceConditionList extends ObjectList<AlertsExternalServiceCondition, AlertsExternalServiceConditionList> {
    @JsonCreator
    public AlertsExternalServiceConditionList(@JsonProperty("external_service_conditions") List<AlertsExternalServiceCondition> items) {
        super(items, AlertsExternalServiceConditionList::new);
    }
}
