import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kid_quest.R
import com.example.kid_quest.models.navItems
import com.example.kid_quest.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun BottomNav(
    navController: NavController
) {
    val navlist = listOf(
        navItems(label = "Home", icon = R.drawable.homebar),
        navItems(label = "Competition", icon = R.drawable.competitionbar),
        navItems(label = "Learning", icon = R.drawable.learningbar),
        navItems(label = "Profile", icon = R.drawable.profilebar)
    )

    val user = Firebase.auth.currentUser
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.Black) {
        navlist.forEachIndexed { index, navItems ->

            val targetRoute = when (index) {
                0 -> Screens.HomeScreen.route
                1 -> Screens.CompetitionScreen.route
                2 -> Screens.LearningScreen.route
                3 -> if (user?.email?.lowercase() == "admin@gmail.com") {
                    Screens.AdminProfile.route
                } else {
                    Screens.ProfileScreen.route
                }

                else -> ""
            }

            val isSelected = currentRoute == targetRoute

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(targetRoute) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(navItems.icon),
                        contentDescription = navItems.label,
                        tint = if (isSelected) Color.White else Color(0xFF6E6A6A)
                    )
                },
                label = {
                    Text(
                        text = navItems.label,
                        color = if (isSelected) Color.White else Color(0xFF6E6A6A)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0x99A6A6B6)
                )
            )
        }
    }
}
