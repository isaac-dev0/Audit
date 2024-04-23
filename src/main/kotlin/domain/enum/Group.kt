package domain.enum

enum class Group(val permissions: Set<Permission>) {
    STAFF_USER(setOf(
        Permission.VIEW_PROFILE,
        Permission.EDIT_PROFILE
    )),
    MANAGER(setOf(
        Permission.VIEW_STAFF,
        Permission.VIEW_SKILLS,
        Permission.EDIT_STAFF,
        Permission.EDIT_SKILLS
    )),
    ADMINISTRATOR(setOf(
        Permission.ALL
    ))
}