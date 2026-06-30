package com.sam.audit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.sam.audit", importOptions = ImportOption.DoNotIncludeTests.class)
class LayerBoundaryArchUnitTest {

  @ArchTest
  static final ArchRule apiDoesNotDependOnInfrastructure =
      noClasses()
          .that()
          .resideInAPackage("..api..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("..infrastructure..");

  @ArchTest
  static final ArchRule domainDoesNotDependOnApi =
      noClasses()
          .that()
          .resideInAPackage("..domain..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage("..api..");
}
