import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.kid_quest.R
import com.example.kid_quest.data.navItems
import com.example.kid_quest.navigation.Screens

@Composable
fun BottomNav(
    navController: NavController
) {
    val navlist= listOf(navItems(label = "Home", icon = R.drawable.homebar),
        navItems(label = "Search", icon = R.drawable.competitionbar),
        navItems(label = "Reels", icon = R.drawable.learningbar),
        navItems(label = "Profile", icon = R.drawable.profilebar))
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    NavigationBar(containerColor = Color.White) {
        navlist.forEachIndexed { index, navItems ->
            NavigationBarItem(
            selected = selectedIndex==index,
            onClick = {
                selectedIndex = index
                when (index) {
                    0 -> navController.navigate(Screens.HomeScreen.route)
                    1 -> navController.navigate(Screens.CompetitionScreen.route)
                    2 -> navController.navigate(Screens.LearningScreen.route)
                    3 -> navController.navigate(Screens.ProfileScreen.route)
                }
            },
                icon = {
                    if (selectedIndex == index) {
                        Icon(
                            painter = painterResource(navItems.icon),
                            contentDescription = "Icons",
                            tint = Color.Black
                        )
                    } else {
                        Icon(
                            painter = painterResource(navItems.icon),
                            contentDescription = "Icons",
                            tint = Color(0xFF463F3F)
                        )
                    }
                },
                label = { navItems.label },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0x99A6A6B6),
                    selectedTextColor = Color.Black
                )
            )
        }
    }
}