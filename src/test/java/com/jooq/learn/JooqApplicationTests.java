package com.jooq.learn;

import com.jooq.learn.database.enums.Gender;
import com.jooq.learn.model.Employee;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.jooq.learn.database.Tables.EMPLOYEES;
import static com.jooq.learn.database.Tables.SALARIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jooq.impl.DSL.count;

@Transactional
@SpringBootTest
@ActiveProfiles("local")
class JooqApplicationTests {

  @Autowired private DSLContext context;

  @Test
  void shouldFetchEmployeesCount() {

    final var employeesRecords = context.selectFrom(EMPLOYEES).fetch();
    assertThat(employeesRecords).hasSize(300024);
  }

  @Test
  void shouldFetchCountOfEmployeesByGender() {

    final Result<Record2<Gender, Integer>> record2Result =
        context.select(EMPLOYEES.GENDER, count()).from(EMPLOYEES).groupBy(EMPLOYEES.GENDER).fetch();

    assertThat(record2Result).hasSize(2);
  }

  @Test
  void shouldFetchEmployeesWithSalary() {

    final List<Employee> employees =
        context
            .select(EMPLOYEES.EMP_NO, EMPLOYEES.FIRST_NAME, EMPLOYEES.HIRE_DATE, SALARIES.SALARY)
            .from(EMPLOYEES)
            .join(SALARIES)
            .on(EMPLOYEES.EMP_NO.eq(SALARIES.EMP_NO))
            .orderBy(SALARIES.SALARY.desc())
            .limit(100)
            .fetchInto(Employee.class);
    assertThat(employees).isNotNull();
  }
}
