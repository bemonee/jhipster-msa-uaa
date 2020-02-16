package com.bemonee.msa;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.bemonee.msa");

        noClasses()
            .that()
                .resideInAnyPackage("com.bemonee.msa.service..")
            .or()
                .resideInAnyPackage("com.bemonee.msa.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.bemonee.msa.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
