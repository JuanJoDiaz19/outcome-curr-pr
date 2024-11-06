package co.edu.icesi.dev.outcome_curr_mgmt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SmokeTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testOracleConnection() {
        String result = jdbcTemplate.queryForObject("SELECT 'Connection OK' FROM dual", String.class);
        assertNotNull(result, "La conexión a Oracle no es válida.");
        assertTrue(result.equals("Connection OK"), "La conexión a Oracle no devolvió el valor esperado.");
    }

    @Test
    public void testCourseTableExists() {
        assertTableExists("COURSE");
    }

    @Test
    public void testRubricTableExists() {
        assertTableExists("RUBRIC");
    }

    @Test
    public void testAssessmentTypeTableExists() {
        assertTableExists("ASSESSMENT_TYPE");
    }

    @Test
    public void testNotificationTableExists() {
        assertTableExists("NOTIFICATION");
    }

    private void assertTableExists(String tableName) {
        try {
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            var resultSet = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
            assertTrue(resultSet.next(), "La tabla " + tableName + " no existe o no es accesible.");
            resultSet.close();
        } catch (SQLException e) {
            throw new AssertionError("Error al acceder a los metadatos de la base de datos.", e);
        }
    }
}