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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import component.AddSkillComponent
import domain.Skill
import domain.User
import domain.enum.Group
import domain.enum.Permission
import repository.UserRepository
import repository.UserRepository.Companion.userCollection

class ProfileScreen(
    user: User?
) : DashboardScreen(user) {
    @Composable
    override fun createScreen() {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    displayInformation()
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    displayUserSkills()
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    displayEmployees()
                }
            }
        }

    }

    @Composable
    fun editUserProfile(
        user: User,
        onUpdateUser: (User) -> Unit,
        onDismiss: () -> Unit
    ) {
        var newForename by remember { mutableStateOf(user.forename) }
        var newSurname by remember { mutableStateOf(user.surname) }

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
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    TextField(
                        value = newForename,
                        onValueChange = { newForename = it },
                        label = { Text("Forename") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = newSurname,
                        onValueChange = { newSurname = it },
                        label = { Text("Surname") }
                    )

                    Button(
                        onClick = {
                            val updatedUser = user.copy(
                                forename = newForename,
                                surname = newSurname
                            )
                            onUpdateUser(updatedUser)
                            onDismiss()
                        }
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }

    @Composable
    fun displayInformation() {
        var showEditDialog by remember { mutableStateOf(false) }

        val userRepository = rememberSaveable { UserRepository(userCollection) }

        Column {
            Text(
                text = "Username",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.size(16.dp)
            )
            Text("${user?.username}")
        }

        Column {
            Text(
                text = "Name",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.size(16.dp)
            )
            Text("${user?.forename} ${user?.surname}")
            TextButton(
                onClick = {
                    showEditDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            if (showEditDialog) {
                editUserProfile(
                    user = user!!,
                    onUpdateUser = { updatedUser ->
                        userRepository.updateUser(updatedUser)
                    },
                    onDismiss = { showEditDialog = false }
                )
            }
        }

        Column {
            Text(
                text = "Job",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.size(16.dp)
            )
            user?.job?.let { Text(it.title) }
        }

        Column {
            Column {
                Text(
                    text = "Name",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
                )
                when (user?.group) {
                    Group.STAFF_USER -> Text("Staff User")
                    Group.MANAGER -> Text("Manager")
                    Group.ADMINISTRATOR -> Text("Administrator")
                    null -> Text("N/A")
                }
            }
        }
    }

    @Composable
    fun displayUserSkills() {

        val isAddSkillVisible = remember { mutableStateOf(false) }
        val addSkillComponent = AddSkillComponent()

        Column {
            Text(
                text = "User Skills",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .size(4.dp)
            )
            Divider()
            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                user?.skills?.forEach { skill ->
                    skillCard(skill = skill)
                }
            }
            TextButton(
                onClick = {
                    isAddSkillVisible.value = true
                }
            ) {
                Text("Add Skill")
            }
        }
        Column {
            if (isAddSkillVisible.value) {
                addSkillComponent.createComponent(
                    onDismissRequest = { isAddSkillVisible.value = false },
                    onConfirmation = { skill, proficiency, notes, expiryDate ->
                        user?.addSkill(skill, proficiency, notes, expiryDate)
                        isAddSkillVisible.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun skillCard(skill: Skill) {
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
                    text = "Proficiency: ${skill.proficiency}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Expiry Date: ${skill.expiryDate}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Notes: ${skill.notes}",
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
                        user?.skills?.remove(skill)
                        println("Successfully removed ${skill.title} from user: ${user?.username}.")
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
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun displayEmployees() {
        val userRepository = UserRepository(userCollection)
        val hasViewStaff by remember { mutableStateOf(user?.hasPermission(user.username, Permission.VIEW_STAFF)) }
        val availableGroups = listOf(Group.STAFF_USER, Group.MANAGER, Group.ADMINISTRATOR)

        var selectedGroup by remember { mutableStateOf<Group?>(null) }
        var selectedSkill by remember { mutableStateOf<Skill?>(null) }

        if (hasViewStaff == true) {
            Column {
                Text("Staff")
                Spacer(modifier = Modifier.size(4.dp))
                Divider()
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Row {
                        ExposedDropdownMenuBox(
                            expanded = selectedGroup != null,
                            onExpandedChange = { expanded ->
                                if (!expanded) {
                                    selectedGroup = null
                                }
                            }
                        ) {
                            ExposedDropdownMenu(
                                expanded = selectedGroup != null,
                                onDismissRequest = { selectedGroup = null }
                            ) {
                                availableGroups.forEach { group ->
                                    DropdownMenuItem(onClick = { selectedGroup = group }) {
                                        Text(group.toString())
                                    }
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = selectedSkill != null,
                            onExpandedChange = { expanded ->
                                if (!expanded) {
                                    selectedSkill = null
                                }
                            }
                        ) {
                            selectedSkill?.let {
                                TextField(
                                    value = it.title,
                                    onValueChange = { },
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = selectedSkill != null
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                                )
                            }
                            ExposedDropdownMenu(
                                expanded = selectedSkill != null,
                                onDismissRequest = { selectedSkill = null }
                            ) {
                                user?.skills?.forEach { skill ->
                                    DropdownMenuItem(onClick = { selectedSkill = skill }) {
                                        Text(skill.title)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    user?.children?.let { children ->
                        val filteredChildren = children.filter { child ->
                            val user = userRepository.getUser(child)
                            selectedGroup?.let { group -> user?.group == group } ?: true &&
                                    selectedSkill?.let { skill -> user?.hasSkill(user.username, skill) ?: false } ?: true
                        }

                        filteredChildren.forEach { child ->
                            userRepository.getUser(child)?.let { newUser ->
                                UserCard(user = newUser)
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun UserCard(user: User?) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "${user?.forename} ${user?.surname}",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Username: ${user?.username}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Job: ${user?.job?.title}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Group: ${user?.group}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Skills: ${user?.skills?.joinToString(", ") { it.title }}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}