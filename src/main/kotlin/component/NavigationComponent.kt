package component

import DashboardComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class NavigationComponent : DashboardComponent() {
    @Composable
    fun createComponent(
        listItems: List<Pair<ImageVector, String>>,
        onItemClick: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = Color(0xFFD6D6D6),
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            listItems.forEach { (icon, title) ->
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .clickable { onItemClick(title) }
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(16.dp)
                    )
                }
            }
        }
        Text("Navigation Component")
    }
}