import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kid_quest.R
import com.example.kid_quest.data.navItems
import com.example.kid_quest.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun BottomNav(
    navController: NavController
) {
    val navlist= listOf(navItems(label = "Home", icon = R.drawable.homebar),
        navItems(label = "Competition", icon = R.drawable.competitionbar),
        navItems(label = "Learning", icon = R.drawable.learningbar),
        navItems(label = "Profile", icon = R.drawable.profilebar))
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val user = Firebase.auth.currentUser
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.Black) {
        navlist.forEachIndexed { index, navItems ->
            NavigationBarItem(
            selected  = currentRoute == when (index) {

                0 -> Screens.HomeScreen.route
                1 -> Screens.CompetitionScreen.route
                2 -> Screens.LearningScreen.route
                3 -> if(user?.email =="Admin@gmail.com")Screens.AdminProfile.route else Screens.ProfileScreen.route
                else -> ""
            },
            onClick = {
                selectedIndex = index
                when (index) {
                    0 -> navController.navigate(Screens.HomeScreen.route)
                    1 -> navController.navigate(Screens.CompetitionScreen.route)
                    2 -> navController.navigate(Screens.LearningScreen.route)
                    3 -> if (user?.email?.lowercase() == "admin@gmail.com")
                            navController.navigate(Screens.AdminProfile.route)
                        else
                            navController.navigate(Screens.ProfileScreen.route)
                }
            },
                icon = {
                    if (selectedIndex == index) {
                        Icon(
                            painter = painterResource(navItems.icon),
                            contentDescription = "Icons",
                            tint = Color.White
                        )
                    } else {
                        Icon(
                            painter = painterResource(navItems.icon),
                            contentDescription = "Icons",
                            tint = Color(0xFF6E6A6A)
                        )
                    }
                },
                label = {
                    Text(text = navItems.label,
                        color = if(selectedIndex==index)Color.White
                    else
                    Color(0xFF6E6A6A))
                        },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0x99A6A6B6),
                    selectedTextColor = Color.Black
                )
            )
        }
    }
}