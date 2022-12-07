package ru.hehnev.tacocloud.repository;

import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.hehnev.tacocloud.models.taco.IngredientRef;
import ru.hehnev.tacocloud.models.taco.Taco;
import ru.hehnev.tacocloud.models.taco.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(
                "INSERT INTO taco_order " +
                        "(delivery_name, " +
                        "delivery_street, " +
                        "delivery_city, " +
                        "delivery_state, " +
                        "delivery_zip, " +
                        "cc_number, " +
                        "cc_expiration, " +
                        "cc_cvv, " +
                        "placed_at) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);

        creatorFactory.setReturnGeneratedKeys(true);
        tacoOrder.setPlacedAt(new Date());

        PreparedStatementCreator psc = creatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        tacoOrder.getDeliveryName(),
                        tacoOrder.getDeliveryStreet(),
                        tacoOrder.getDeliveryCity(),
                        tacoOrder.getDeliveryState(),
                        tacoOrder.getDeliveryZip(),
                        tacoOrder.getCcNumber(),
                        tacoOrder.getCcExpiration(),
                        tacoOrder.getCcCVV(),
                        tacoOrder.getPlacedAt()));

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, generatedKeyHolder);
        long orderId = (long) generatedKeyHolder.getKeys().get("id");
        tacoOrder.setId(orderId);

        List<Taco> tacos = tacoOrder.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return tacoOrder;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(
                "INSERT INTO taco " +
                        "(name, taco_order, taco_order_key, created_at) " +
                        "VALUES (?,?,?,?)",
                Types.VARCHAR, Type.LONG, Type.LONG, Types.TIMESTAMP);

        creatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = creatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        orderId,
                        orderKey,
                        taco.getCreatedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = (long) keyHolder.getKeys().get("id");
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());
        return 0;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientsRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientsRefs) {
            jdbcOperations.update(
                    "INSERT INTO ingredient_ref " +
                            "(ingredient, taco, taco_key) " +
                            "VALUES (?,?,?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }

    }
}
