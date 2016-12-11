# Backend Dacty

An approach for trying to transport the micro-services-idea to frontends which are based
on components.

A set of urls are loaded for html-fragments which get injected in the basic call for
the index.html file delivered to the frontend. This allows for the same frontend
to be build up out of the micro-frontends of several other services, which together
make up the UI for the user. The resulting services can still be individually deployed
without dependencies and the assets can be treated as immutable for caching.

The name is inspired by https://github.com/dactylographsy/browser-dactylographsy

## Installation

1. Switch to `external-server`
2. Do a `yarn` or `npm install`
3. Do an `npm start`
4. Switch back to the root level and do `./sbt run`
