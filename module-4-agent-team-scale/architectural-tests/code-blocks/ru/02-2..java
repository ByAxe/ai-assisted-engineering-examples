// ArchUnit: query path stays read-only
@ArchTest
static final ArchRule query_path_must_not_touch_write_path =
    noClasses()
        .that().resideInAnyPackage("..api.query..", "..application.query..")
        .should().dependOnClassesThat()
        .resideInAnyPackage("..api.append..", "..application.append..");
