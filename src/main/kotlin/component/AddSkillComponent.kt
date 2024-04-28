package component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import domain.enum.Proficiency
import repository.SkillRepository
import repository.SkillRepository.Companion.skillCollection

class AddSkillComponent {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun createComponent(
        onDismissRequest: () -> Unit,
        onConfirmation: (String, Proficiency, String, String) -> Unit
    ) {

        var isSkillItemExpanded by remember { mutableStateOf(false) }
        var isProficiencyItemExpanded by remember { mutableStateOf(false) }

        val skillRepository = SkillRepository(skillCollection)

        var skill by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf("") }
        var expiryDate by remember { mutableStateOf("") }
        var proficiency by remember { mutableStateOf(Proficiency.NONE) }

        Dialog(
            onDismissRequest = { onDismissRequest() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add Skill")
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = isSkillItemExpanded,
                        onExpandedChange = {
                            isSkillItemExpanded = it
                        }
                    ) {
                        TextField(
                            value = skill,
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = isSkillItemExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = isSkillItemExpanded,
                            onDismissRequest = {
                                isSkillItemExpanded = false
                            }
                        ) {
                            skillRepository.getSkillTitles().forEach { title ->
                                DropdownMenuItem(
                                    onClick = {
                                        skill = title
                                        isSkillItemExpanded = false
                                    }
                                ) {
                                    Text(title)
                                }
                            }
                        }
                    }
                    /*
                    *
                    * Expiry
                    *
                    * */
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = isProficiencyItemExpanded,
                        onExpandedChange = {
                            isProficiencyItemExpanded = it
                        }
                    ) {
                        TextField(
                            value = proficiency.title,
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = isProficiencyItemExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = isProficiencyItemExpanded,
                            onDismissRequest = {
                                isProficiencyItemExpanded = false
                            }
                        ) {
                            Proficiency.entries.forEach { title ->
                                DropdownMenuItem(
                                    onClick = {
                                        proficiency = title
                                        isProficiencyItemExpanded = false
                                    }
                                ) {
                                    Text(title.title)
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = notes,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { notes = it },
                        label = { Text("Notes") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = expiryDate,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { expiryDate = it },
                        label = { Text("Expiry Date") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                onDismissRequest()
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Cancel")
                        }
                        TextButton(
                            onClick = {
                                onConfirmation(skill, proficiency, notes, expiryDate)
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Add Skill")
                        }
                    }
                }
            }
        }
    }
}