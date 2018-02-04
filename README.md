# Offer Service

## Requirements

Per Wikipedia, "an offer is a proposal to sell a specific product or service under specific conditions". As a merchant I offer goods for sale. I want to create an offer so that I can share it with my customers.

All my offers have shopper friendly descriptions. I price all my offers up front in a defined currency.

An offer is time-bounded, with the length of time an offer is valid for defined as part of the offer, and should expire automatically. Offers may also be explicitly cancelled before they expire.

You are required to create a simple RESTful software service that will allow a merchant to create a new simple offer. Offers, once created, may be queried. After the period of time defined on the offer it should expire and further requests to query the offer should reflect that somehow. Before an offer has expired users may cancel it.

## Assumptions

 - ISO-4217 standard is used for Currency Codes
 - ISO-8601 standard is used for Timestamp string representation
 - Syntactic validation uses JSR-303 Bean Validation API
 - Semantic validation is implemented in the Service-layer
 - Cancellation of an offer results in that resource being deleted (as opposed to having 'expired')
 - Query parameters are optional, and currently restricted to 'Currency'

## Future Enhancements

 - Add non-functional requirements such as enhanced Logging / Auditing
 - Re-implement OfferRepository as a more suitable production-grade implementation (i.e. database persistence)
 - Add support for transaction integrity
 - Add machine-readable error code to Error responses
 - Implement authorization
 - Restructure the Query endpoint to support pagination
 - Enhance query parameters to support additional capabilities (i.e. query by description, expiry time, etc)
 - Introduce HATEOAS media type to provide clients with ability to cancel unexpired offers