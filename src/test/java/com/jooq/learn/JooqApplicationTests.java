package com.jooq.learn;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static com.jooq.learn.database.Tables.EMPLOYEES;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class JooqApplicationTests {

  @Autowired private DSLContext context;

  @Test
  void shouldFetchEmployeesCount() {

    final var employeesRecords = context.selectFrom(EMPLOYEES).fetch();
    assertThat(employeesRecords).hasSize(300024);
  }
}
