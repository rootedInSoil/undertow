package io.undertow.server.handlers;

import io.undertow.attribute.ExchangeAttribute;
import io.undertow.attribute.ExchangeAttributeParser;
import io.undertow.attribute.ExchangeAttributes;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * A redirect handler that redirects to the specifed location via a 302 redirect.
 * <p/>
 * The location is specified as an exchange attribute string.
 *
 * @author Stuart Douglas
 * @see ExchangeAttributes
 */
public class RedirectHandler implements HttpHandler {

    private final ExchangeAttribute[] attributes;

    public RedirectHandler(final String location) {
        ExchangeAttributeParser parser = ExchangeAttributes.parser(getClass().getClassLoader());
        attributes = parser.parse(location);
    }

    public RedirectHandler(final String location, final ClassLoader classLoader) {
        ExchangeAttributeParser parser = ExchangeAttributes.parser(classLoader);
        attributes = parser.parse(location);
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        exchange.setResponseCode(302);
        exchange.getResponseHeaders().put(Headers.LOCATION, ExchangeAttributes.resolve(exchange, attributes));
        exchange.endExchange();
    }

}
