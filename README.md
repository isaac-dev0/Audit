# Audit

## Bugs

### Global
- UI formatting is terrible, this will be fixed in Part 2 of the assignment.

### Profile
- Skills section does not update if a skill is deleted.
- Upon updating personal details, the composable needs to be reloaded (logout)
- Skills section does not align with the users document in the database.
- Upon deleting a skill, and attempting to re-add the same skill, it notes that the skill already exists on user.
  - Temporary fix will be to disable duplication checking.