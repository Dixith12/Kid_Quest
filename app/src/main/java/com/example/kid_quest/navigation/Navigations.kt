package com.example.kid_quest.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kid_quest.screens.SplashScreen
import com.example.kid_quest.screens.admin.AddVideoFirestore
import com.example.kid_quest.screens.admin.AdminApprovalScreen
import com.example.kid_quest.screens.admin.AdminProfile
import com.example.kid_quest.screens.auth.CreateScreen
import com.example.kid_quest.screens.auth.LoginScreen
import com.example.kid_quest.screens.competition.CompetitionScreen
import com.example.kid_quest.screens.competition.CreateCompition
import com.example.kid_quest.screens.competition.JoinCompetition
import com.example.kid_quest.screens.homeScreen.HomeScreen
import com.example.kid_quest.screens.learning.LearningScreen
import com.example.kid_quest.screens.learning.VideosList
import com.example.kid_quest.screens.post.PostScreen
import com.example.kid_quest.screens.profile.CreatedCompetition
import com.example.kid_quest.screens.profile.EditProfile
import com.example.kid_quest.screens.profile.ProfileScreen

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigations() {
    val navController= rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
            composable(Screens.SplashScreen.route){
                SplashScreen(navController)
            }
            composable(Screens.CreateAccount.route){
                CreateScreen(navController = navController)
            }
            composable(Screens.LoginScreen.route){
                LoginScreen(navController)
            }
            composable(Screens.HomeScreen.route){
                HomeScreen(navController)
            }
            composable(Screens.CompetitionScreen.route){
                CompetitionScreen(navController)
            }
            composable(Screens.CreateCompetition.route){
                CreateCompition(navController)
            }
            composable(Screens.ProfileScreen.route){
                ProfileScreen(navController)
            }
            composable(Screens.LearningScreen.route){
                LearningScreen()
            }
            composable(Screens.EditProfile.route){
                EditProfile(navController)
            }
            composable(Screens.CreatedCompetition.route){
                CreatedCompetition(navController)
            }
            composable(Screens.PostScreen.route) {
                PostScreen(navController)
            }
            composable(Screens.JoinCompetiton.route,
                arguments = listOf(navArgument("id"){
                    type=NavType.StringType
                })
            ){
                backStackEntry ->
                val id=backStackEntry.arguments?.getString("id")
                JoinCompetition(navController,id)
            }
            composable(Screens.AdminApprovalScreen.route,
                arguments = listOf(navArgument("id"){type=NavType.StringType})
            ) {
                backStackEntry ->
                val id=backStackEntry.arguments?.getString("id")
                AdminApprovalScreen(navController,id)
            }
            composable(
                route = Screens.VideoList.route,
                arguments = listOf(navArgument("classLevel") { type = NavType.IntType })
            ) { backStackEntry ->
                val classLevel = backStackEntry.arguments?.getInt("classLevel") ?: 8
                VideosList(navController,classLevel = classLevel)
            }
            composable(Screens.AddVideoFirestore.route) {
                AddVideoFirestore(navController)
            }
            composable(Screens.AdminProfile.route) {
                AdminProfile(navController)
            }
        }
    }
}