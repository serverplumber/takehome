# Hatch Takehome exercise

## Overview

This is a takehome exercise defined by Hatch to evaluate candidates for java
developer positions. The exercise is to create a simple REST API which provides
a list of countries grouped by continent. The exercise seems to be a smoke test
for knowledge of Spring Boot more than anything else.

## The exercise

The complete problem statement is as follows can be found in the [`INSTRUCTIONS.md`].
The problem statement is as follows:
> Design and implement a production-ready REST API that accepts a list of country codes
> and returns a list of country codes that are in the same continent as the country code input.
> Further down, the expected output is specified:

```json
{
  "continent": [
    {
      "countries": [ "CA", "US" ],
      "name": "North America",
      "otherCountries": [ "AG", "AI", "AW", "BB", ... ] // should not have CA or US here
    }
  ]
}
```

While the problem statement is sufficiently vague that creating a REST API which
fits the description is possible, the example output kills the idea of a REST API.
REST is about hypermedia; while this problem statement describes one endpoint
with a relationship only to itself. The problem is now a simple rpc call over
http. If we pick apart the expected output we quickly realize that the problem is
to simply pull a list of countries from a continent, remove the countries provided
place them in a list called `countries` and the rest in `otherCountries`. If countries
for other continents are provided, no output is specified. I assumed a list of
continents should be returned.

Another peculiar thing about the problem statement is the use of the qualifier
`production-ready` without further specification. Production on what? Are we going
to production in a Kubernetes cluster, a VM, a bare metal server or
perhaps a raspberry pi? These questions are not mockery. This is an exercise for a senior
position; I have written software for deployment to production all of these
environments. It could also be argued that while this is a senior position, it
is not the lead position and that as such, a CI/CD pipeline already exists. One
could start guessing at what, according to Hatch, is production ready for a
Spring Boot application. Having never lead or operated a Spring Boot application
I will veer away from that discussion and propose the following: I expect Epic,
as Hatch's customer, has specifications on what "production ready" means for them.
I expect this to include things like logging, monitoring, security, and packaging
standards. I expect that Hatch already has templates for setting up appropriate
profiles when starting new projects and that understanding the tools and frameworks
used is the only requirement.

### Bonus task

The bonus task is to add rate limiting to the API. I assume that this is a simple
probe into my knowledge of [Resilience4j] or of some sidecar proxy like [Envoy]. I
understand both types of solutions.

## My solution

The data, while it could be pulled from trevorblades' GraphQL endpoint dynamically is
declared statically. Countries and continents are static data, these change at most 
every few decades. As such, introducing a dependency on an external service for querying
static data seems like folly as we are writing a "production ready" application.

I store the data in a simple graph structure provided by the [Guava] library even
though the api is still marked as beta. I have rarely been bitten by Google's
software, even in beta. Many other libraries could have been used, but I keep [Guava]
around by default when I write Java code. Using a graph structure makes querying the
data trivial.

The endpoint itself is a few lines of code, nothing fancy, nothing to see here.

[`INSTRUCTIONS.md`]: INSTRUCTIONS.md
[Resilience4j]: https://resilience4j.readme.io/docs/getting-started-3
[Envoy]: https://www.envoyproxy.io/
[Guava]: https://github.com/google/guava

### Testing

I have written a few tests to verify the functionality of the API. I have not written
tests which verify Spring's datamapping. I don't believe these tests are necessary, though
some teams do require them.

### Building

The application is built using [Gradle]. A simple `./gradlew build` will build the application.

[Gradle]: https://gradle.org/