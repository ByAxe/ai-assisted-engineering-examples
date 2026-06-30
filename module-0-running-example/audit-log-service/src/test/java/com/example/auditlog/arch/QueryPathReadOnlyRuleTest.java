package com.example.auditlog.arch;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.base.DescribedPredicate;
import java.util.Set;

@AnalyzeClasses(packages = "com.example.auditlog", importOptions = ImportOption.DoNotIncludeTests.class)
class QueryPathReadOnlyRuleTest {

  private static final String SELF_CORRECTION_MESSAGE =
      "VIOLATION: query code must stay read-only. You imported or called the append/write path "
          + "from the query path, which breaks the append-only invariant. To self-correct: remove "
          + "that dependency and read events only through the read repository; if you think a write "
          + "is required here, STOP - the query API never writes. See .specs/api/query-api.md.";

  private static final Set<String> WRITE_METHOD_NAMES =
      Set.of("save", "saveAll", "saveAndFlush", "persist", "merge", "remove", "delete", "deleteById");

  // Red-stage demo import: uncomment an import of
  // com.example.auditlog.application.append.AppendAuditEventService from query code to trip the rule.

  @ArchTest
  static final ArchRule queryPathDoesNotDependOnAppendPath =
      noClasses()
          .that(queryPath())
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("..api.append..", "..application.append..", "..persistence.append..")
          .because(SELF_CORRECTION_MESSAGE);

  @ArchTest
  static final ArchRule queryPathDoesNotCallWriteMethods =
      classes().that(queryPath()).should(notCallWriteMethods()).because(SELF_CORRECTION_MESSAGE);

  private static DescribedPredicate<JavaClass> queryPath() {
    return resideInAnyPackage("..api.query..", "..application.query..", "..persistence.query..");
  }

  private static ArchCondition<JavaClass> notCallWriteMethods() {
    return new ArchCondition<>("not call save/persist/merge/remove/delete methods") {
      @Override
      public void check(JavaClass item, ConditionEvents events) {
        for (JavaMethodCall call : item.getMethodCallsFromSelf()) {
          var methodName = call.getTarget().getName();
          if (WRITE_METHOD_NAMES.contains(methodName)) {
            events.add(
                SimpleConditionEvent.violated(
                    item, item.getName() + " calls write-like method " + call.getDescription()));
          }
        }
      }
    };
  }
}

