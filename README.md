# NewRelic Alerts configurator

NewRelic Alerts configurator can be used for configuration of NewRelic alerts for your application. Instead of defining alerts through UI you can define them in code.

![](images/new_relic_library_diagram.png)

## How to use NewRelic Alerts configurator

First step is to create Configurator instance using simple constructor accepting API key for given NewRelic account:
```java
Configurator configurator = new Configurator(yourApiKey);
```
**Note that for some configurations you will need Admin User's New Relic API key.**

Next step is to set configurations
 - application configuration
    ```java
    configurator.setApplicationConfigurations(yourApplicationConfigurationsCollection);
    ```

 - policy configuration
    ```java
    configurator.setPolicyConfigurations(yourPolicyConfigurationsCollection);
    ```
Both methods accept Java Collections of configurations.

In last step run single method on Configurator to synchronize your configuration:
```java
configurator.sync();
```
That's all!

## Application configuration
To configure your application in NewRelic you just need to create and pass your application configuration to NewRelic Alerts configurator.
Application configuration can be created using simple builder:
```java
ApplicationConfiguration.builder()
```
What you can set in your configuration:
- application name - Your application name.
- application apdex threshold (optional) - Set apdex threshold for application.
- end user apdex threshold (optional) - Set apdex threshold for end user.
- real user monitoring enabled (optional) - If real user monitoring is enabled. Default value is false.

If application with given application name does not exist exception will be thrown.

Example application configuration:
```java
ApplicationConfiguration configuration = ApplicationConfiguration.builder()
    .applicationName("Application name")
    .appApdexThreshold(0.5f)
    .endUserApdexThreshold(7.0f)
    .enableRealUserMonitoring(true)
    .build();
```

## Policy configuration
To configure your alerts policy in NewRelic you just need to create and pass your policy configuration to NewRelic Alerts configurator.
Policy configuration can be created using simple builder:
```java
PolicyConfiguration.builder()
```
What you can set in your configuration:
- policy name - Name of your alerts policy.
- incident preference (optional) - Rollup strategy options for your alerts policy. Possible values are:
    - Per policy (default)
    - Per condition
    - Per condition and target
- conditions (optional) - Collection of alerts conditions which needs to be configured for your alerts policy. If no conditions are set, your alerts policy won't have any alerts conditions.
- external service conditions (optional) - Collection of alerts external service conditions which needs to be configured for your alerts policy. If no external service conditions are set, your alerts policy won't have any alerts external service conditions.
- channels (optional) - Collection of alerts channels which needs to be configured for your alerts policy. If no channels are set, your alerts policy won't have any alerts channels.

If policy with given policy name exists - it will be updated.

If policy with given policy name does not exist - it will be created.

Example alerts policy configuration:
```java
PolicyConfiguration configuration = PolicyConfiguration.builder()
    .policyName("Policy name")
    .incidentPreference(PolicyConfiguration.IncidentPreference.PER_POLICY)
    .condition(apmAppCondition)
    .condition(apmKeyTransactionCondition)
    .externalServiceCondition(apmExternalServiceCondition)
    .channel(emailChannel)
    .channel(slackChannel)
    .build()
```

### Alerts condition
Currently supported types of alerts policy conditions are:
- APM application metric condition
- APM key transaction metric condition
- Server metric condition

#### APM application metric condition
To create APM application metric condition for your alerts policy use simple builder:
```java
ApmAppCondition.builder()
```
What you can set for APM application metric condition:
- condition name - Name of your APM application metric condition.
- enabled (optional) - If your APM application metric condition is enabled. Default is false.
- entities - Collection of application names for which this condition is applied. If application with given name does not exist exception will be thrown.
- metric - Metric used in given condition. Possible values are:
    - Apdex
    - Error percentage
    - Response time (web)
    - Response time (background)
    - Throughput (web)
    - Throughput (background)
    - User defined
- condition scope - Possible values are:
    -  Application - The average of all application instances' data is used during evaluation.
    -  Instance - Each application instance's data is evaluated individually
- run book url (optional) - The runbook URL to display in notifications.
- terms - Collection of terms used for alerts condition.
- user defined configuration - configuration for user defined metric.

Example APM application metric condition configuration:
```java
Condition apmAppCondition = ApmAppCondition.builder()
    .conditionName("Condition name")
    .enabled(true)
    .entity("Application name")
    .metric(ApmAppCondition.Metric.APDEX)
    .conditionScope(ApmAppCondition.ConditionScope.APPLICATION)
    .term(term)
    .build();
```

#### APM key transaction metric condition
To create APM key transaction metric condition for your alerts policy use simple builder:
```java
ApmKeyTransactionCondition.builder()
```
What you can set for APM key transaction metric condition:
- condition name - Name of your APM key transaction metric condition.
- enabled (optional) - If your APM key transaction metric condition is enabled. Default is false.
- entities - Collection of key transaction names for which this condition is applied. If key transaction with given name does not exist exception will be thrown.
- metric - Metric used in given condition. Possible values are:
    - Apdex
    - Error percentage
    - Error count
    - Response time
    - Throughput
- run book url (optional) - The runbook URL to display in notifications.
- terms - Collection of terms used for alerts condition.

Example APM key transaction metric condition configuration:
```java
Condition apmKeyTransactionCondition = ApmKeyTransactionCondition.builder()
    .conditionName("Condition name")
    .enabled(true)
    .entity("Key Transaction name")
    .metric(ApmKeyTransactionCondition.Metric.APDEX)
    .term(term)
    .build();
```

**Note that key transaction cannot be created via New Relic API. Before configuration of condition, key transaction needs to be manually created in New Relic.**

#### Server metric condition
To create server metric condition for your alerts policy use simple builder:
```java
ServerMetricCondition.builder()
```
What you can set for server metric condition:
- condition name - Name of your server metric condition.
- enabled (optional) - If your server metric condition is enabled. Default is false.
- entities - Collection of server names for which this condition is applied. If server with given name does not exist exception will be thrown.
- metric - Metric used in given condition. Possible values are:
    - CPU percentage
    - Disk I/O percentage
    - Memory percentage
    - Fullest disk percentage
    - Load average one minute
    - User defined
- run book url (optional) - The runbook URL to display in notifications.
- terms - Collection of terms used for alerts condition.
- user defined configuration - configuration for user defined metric.

Example server metric condition configuration:
```java
Condition serverMetricCondition = ServerMetricCondition.builder()
    .conditionName("Condition name")
    .enabled(true)
    .entity("some-host")
    .metric(ServerMetricCondition.Metric.CPU_PERCENTAGE)
    .term(term)
    .build();
```

### Alerts external service condition
Currently supported types of alerts policy external service conditions are:
- APM external service condition

#### APM external service condition
To create APM external service condition for your alerts policy use simple builder:
```java
ApmExternalServiceCondition.builder()
```
What you can set for APM external service condition:
- condition name - Name of your APM external service condition.
- enabled (optional) - If your APM external service condition is enabled. Default is false.
- entities - Collection of application names for which this condition is applied. If application with given name does not exist exception will be thrown.
- metric - Metric used in given condition. Possible values are:
    - Response time (average)
    - Response time (minimum)
    - Response time (maximum)
    - Throughput
- external service url - URL of the external service to be monitored. This string must not include the protocol.
- run book url (optional) - The runbook URL to display in notifications.
- terms - Collection of terms used for alerts condition.

Example APM external service condition configuration:
```java
ExternalServiceCondition apmExternalServiceCondition = ApmExternalServiceCondition.builder()
    .conditionName("Condition name")
    .enabled(true)
    .entity("Application name")
    .externalServiceUrl("externalServiceUrl")
    .metric(ApmExternalServiceCondition.Metric.RESPONSE_TIME_AVERAGE)
    .term(term)
    .build();
```

### User defined configuration
User defined configuration allows to use custom (user defined) metric in alert definition.

Example user defined configuration:
```java
UserDefinedConfiguration config = UserDefinedConfiguration.builder()
    .metric("MY_CUSTOM_METRIC")
    .valueFunction(UserDefinedConfiguration.ValueFunction.MAX)
    .build();
```

### Term
Terms are used both in alerts conditions and alerts external service conditions.
To create term configuration for condition use simple builder:
```java
TermsConfiguration.builder()
```
What you can set in term configuration:
- duration - Time (in minutes) for the condition to persist before triggering an event. Possible values are:
    - 5
    - 10
    - 15
    - 30
    - 60
    - 120
- operator - Determines what comparison will be used between the monitored value and the threshold term value to trigger an event. Possible values are:
    - Above
    - Below
    - Equal
- priority - Severity level for given term in condition. Possible values are:
    - Critical
    - Warning
- time function - Time function in which threshold term have to be reached in duration term to trigger an event. Possible values are:
    - All (for at least)
    - Any (at least once in)
- threshold - Threshold that the monitored value must be compared to using the operator term for an event to be triggered.

Example term configuration:
```java
TermsConfiguration term = TermsConfiguration.builder()
    .durationTerm(DurationTerm.DURATION_5)
    .operatorTerm(OperatorTerm.BELOW)
    .priorityTerm(PriorityTerm.CRITICAL)
    .timeFunctionTerm(TimeFunctionTerm.ALL)
    .thresholdTerm(0.8f)
    .build();
```

### Notification channel
Currently supported types of alerts notification channels are:
- Email channel
- Slack channel
- Webhook channel
- PagerDuty channel
- NewRelic user channel

If notification channel with given name and type does exists - it will be updated

If notification channel with given name and type does not exist - it will be created

If you remove channel from your policy configuration, and it is not configured for any other policy - it will be deleted (except for NewRelic user channel).

#### Email channel
To create email channel use simple builder:
```java
EmailChannel.builder()
```

What you can set in email channel configuration:
- channel name - Name of your alerts channel.
- email address - Email address to which alert event should be sent.
- include json attachment (optional) - If json attachment should be included in alert event email. Default value is false.

Example email channel configuration:
```java
Channel emailChannel = EmailChannel.builder()
    .channelName("Channel name")
    .emailAddress("your-email-address@ocado.com")
    .includeJsonAttachment(true)
    .build();
```

#### Slack channel
To create slack channel use simple builder:
```java
SlackChannel.builder()
```

What you can set in slack channel configuration:
- channel name - Name of your alerts channel.
- slack url - Slack url to your channel.
- team channel (optional) - Name of your team channel.


Example slack channel configuration:
```java
Channel slackChannel = SlackChannel.builder()
    .channelName("Channel name")
    .slackUrl("https://hooks.slack.com/services/...")
    .teamName("My team slack name")
    .build();
```

#### Webhook channel
To create webhook channel use simple builder:
```java
WebhookChannel.builder()
```

What you can set in webhook channel configuration:
- channel name - Name of your alerts channel.
- base URL - URL webhook address to which alert event should be sent.
- auth username - User name for BASIC authorization (optional)
- auth password - User password for BASIC authorization (optional)

Example webhook channel configuration:
```java
Channel webhookChannel = WebhookChannel.builder()
    .channelName("Channel name")
    .baseUrl("https://example.com/webhook/abcd123")
    .authUsername('john.doea')
    .authPassword('starwars1')
    .build();
```


#### PagerDuty channel
To create [PagerDuty](https://www.pagerduty.com/) channel use simple builder:
```java
PagerDutyChannel.builder()
```

What you can set in PagerDUty channel configuration:
- channel name - Name of your alerts channel.
- serviceKey - Service key integration ID defined in PagerDuty.

Example PagerDuty channel configuration:
```java
Channel pagerDutyChannel = PagerDutyChannel.builder()
    .channelName("Channel name")
    .serviceKey('1234-5678-ABCD-XYZ')
    .build();
```


#### NewRelic user channel
To create NewRelic user channel use simple builder:
```java
UserChannel.builder()
```

What you can set in NewRelic user channel:
- email address - NewRelic user's email address 

Example email channel configuration:
```java
Channel userChannel = UserChannel.builder()
    .userEmail("your-email-address@ocado.com")
    .build();
```


## NewRelic API Client

This project contains following tool useful for developers that monitor their application using New Relic:
- [NewRelic API client](api-client/)
  Client for New Relic rest API

## Build and publish:

Set environment variables: `MAVEN_REPO_USERNAME`, `MAVEN_REPO_PASSWORD`.

Then execute:

```
./gradlew clean publish -Pbuild_version=<build_version>
```
If build_verison contains "-SNAPSHOT" suffix, it will be published to snapshot repository. Release repository will be used otherwise.
