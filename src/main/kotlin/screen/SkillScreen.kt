package screen

import DashboardScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import component.CreateSkillComponent
import domain.Skill
import domain.User
import org.bson.types.ObjectId
import repository.SkillRepository
import repository.SkillRepository.Companion.skillCollection

class SkillScreen(
    user: User?
) : DashboardScreen(user) {
    @Composable
    override fun createScreen() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                displaySkills()
            }
        }
    }

    @Composable
    fun displaySkills() {
        val isCreateSkillVisible = remember { mutableStateOf(false) }
        val createSkillComponent = CreateSkillComponent()
        val skillRepository = rememberSaveable { SkillRepository(skillCollection) }

        Column {
            Text(
                text = "Skills",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Divider()
            Spacer(modifier = Modifier.size(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                skillRepository.getSkills().forEach { skill ->
                    skillCard(skill = skill)
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            TextButton(onClick = { isCreateSkillVisible.value = true }) {
                Text("Create Skill")
            }
        }

        if (isCreateSkillVisible.value) {
            createSkillComponent.createComponent(
                onDismissRequest = { isCreateSkillVisible.value = false },
                onConfirmation = { title, description, category ->
                    skillRepository.createSkill(
                        Skill(
                            _id = ObjectId.get(),
                            title = title,
                            description = description,
                            category = category,
                            expiryDate = null,
                            proficiency = null,
                            notes = null
                        )
                    )
                    isCreateSkillVisible.value = false
                }
            )
        }
    }

    @Composable
    fun skillCard(skill: Skill) {
        var showSkillEditDialog by remember { mutableStateOf(false) }
        val skillRepository = SkillRepository(skillCollection)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = skill.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description: ${skill.description}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Category: ${skill.category}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                TextButton(
                    onClick = {
                        skillRepository.deleteSkill(skill)
                        println("Successfully deleted ${skill.title} from the database.")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = {
                        showSkillEditDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            }
        }
        if (showSkillEditDialog) {
            editSkill(
                skill = skill,
                onUpdateUser = { updatedSkill ->
                    skillRepository.updateSkill(updatedSkill)
                },
                onDismiss = { showSkillEditDialog = false }
            )
        }
    }

    @Composable
    fun editSkill(
        skill: Skill,
        onUpdateUser: (Skill) -> Unit,
        onDismiss: () -> Unit
    ) {
        var newTitle by remember { mutableStateOf(skill.title) }
        var newDescription by remember { mutableStateOf(skill.description) }
        var newCategory by remember { mutableStateOf(skill.category) }

        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Edit Skill")
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newTitle,
                            onValueChange = { newTitle = it },
                            label = { Text("Title") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newDescription!!,
                            onValueChange = { newDescription = it },
                            label = { Text("Description") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newCategory!!,
                            onValueChange = { newCategory = it },
                            label = { Text("Title") }
                        )
                    }
                    item {
                        Button(
                            onClick = {
                                val updatedSkill = skill.copy(
                                    title = newTitle,
                                    description = newDescription,
                                    category = newCategory
                                )
                                onUpdateUser(updatedSkill)
                                onDismiss()
                            }
                        ) {
                            Text("Save Changes")
                        }
                    }
                }
            }
        }
    }
}
