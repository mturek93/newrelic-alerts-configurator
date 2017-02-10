package com.ocado.panda.newrelic.sync.internal.entities;


import com.ocado.newrelic.api.NewRelicApi;
import com.ocado.newrelic.api.model.applications.Application;
import com.ocado.panda.newrelic.sync.exception.NewRelicSyncException;

import java.util.Optional;

import static java.lang.String.format;

class ApplicationIdProvider implements IdProvider {
    @Override
    public Integer getId(NewRelicApi api, String name) {
        Optional<Application> applicationOptional = api.getApplicationsApi().getByName(name);
        Application application = applicationOptional.orElseThrow(
                () -> new NewRelicSyncException(format("Application %s does not exist", name)));
        return application.getId();
    }
}
