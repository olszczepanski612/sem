
package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AppUnitTest {

    // Inject a connection into App
    private void setConnection(App app, Connection connection)
            throws Exception {
        Field field = App.class.getDeclaredField("con");
        field.setAccessible(true);
        field.set(app, connection);
    }

    // Test getCity when no database connection exists
    @Test
    void testGetCityWithoutConnection() {
        App app = new App();

        assertThrows(
                IllegalStateException.class,
                () -> app.getCity(1)
        );
    }

    // Test getCity when a city is not found
    @Test
    void testGetCityNotFound() throws Exception {
        App app = new App();

        ResultSet rs = (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class[]{ResultSet.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("next")) {
                        return false;
                    }
                    return null;
                }
        );

        PreparedStatement stmt = (PreparedStatement)
                Proxy.newProxyInstance(
                        PreparedStatement.class.getClassLoader(),
                        new Class[]{PreparedStatement.class},
                        (proxy, method, args) -> {
                            if (method.getName().equals("executeQuery")) {
                                return rs;
                            }
                            return null;
                        }
                );

        Connection con = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("prepareStatement")) {
                        return stmt;
                    }
                    return null;
                }
        );

        setConnection(app, con);

        assertNull(app.getCity(999));
    }

    // Test getCity when a city is found
    @Test
    void testGetCityFound() throws Exception {
        App app = new App();

        ResultSet rs = (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class[]{ResultSet.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "next":
                            return true;
                        case "getLong":
                            return args[0].equals("ID") ? 458L : 619680L;
                        case "getString":
                            switch ((String) args[0]) {
                                case "Name": return "Glasgow";
                                case "CountryCode": return "GBR";
                                case "District": return "Scotland";
                            }
                    }
                    return null;
                }
        );

        PreparedStatement stmt = (PreparedStatement)
                Proxy.newProxyInstance(
                        PreparedStatement.class.getClassLoader(),
                        new Class[]{PreparedStatement.class},
                        (proxy, method, args) -> {
                            if (method.getName().equals("executeQuery")) {
                                return rs;
                            }
                            return null;
                        }
                );

        Connection con = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("prepareStatement")) {
                        return stmt;
                    }
                    return null;
                }
        );

        setConnection(app, con);

        City city = app.getCity(458);

        assertNotNull(city);
        assertEquals(458, city.getId());
        assertEquals("Glasgow", city.getName());
        assertEquals("GBR", city.getCountryCode());
        assertEquals("Scotland", city.getDistrict());
        assertEquals(619680, city.getPopulation());
    }

    // Test getCity when SQL throws an exception
    @Test
    void testGetCitySQLException() throws Exception {
        App app = new App();

        Connection con = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("prepareStatement")) {
                        throw new SQLException("Database error");
                    }
                    return null;
                }
        );

        setConnection(app, con);

        assertThrows(
                IllegalStateException.class,
                () -> app.getCity(1)
        );
    }

    // Test disconnect without a connection
    @Test
    void testDisconnectWithoutConnection() {
        App app = new App();

        assertDoesNotThrow(app::disconnect);
    }

    // Test successful disconnect
    @Test
    void testDisconnectSuccessful() throws Exception {
        App app = new App();

        Connection con = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> null
        );

        setConnection(app, con);

        assertDoesNotThrow(app::disconnect);

        // Disconnecting twice should also be safe
        assertDoesNotThrow(app::disconnect);
    }

    // Test disconnect when closing the connection fails
    @Test
    void testDisconnectSQLException() throws Exception {
        App app = new App();

        Connection con = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("close")) {
                        throw new SQLException("Close failed");
                    }
                    return null;
                }
        );

        setConnection(app, con);

        assertThrows(
                IllegalStateException.class,
                app::disconnect
        );
    }

    // Test failed database connection
    @Test
    void testConnectFailure() {
        App app = new App();

        assertThrows(
                IllegalStateException.class,
                () -> app.connect("localhost:1", -1000)
        );
    }
}
