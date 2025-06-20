package com.example.kid_quest.navigation

sealed class Screens(val route:String){
    object SplashScreen : Screens("SplashScreen")
    object LoginScreen : Screens("LoginScreen")
    object CreateAccount : Screens("CreateAccount")
    object HomeScreen : Screens("HomeScreen")
    object CompetitionScreen : Screens("CompetitionScreen")
    object LearningScreen : Screens("LearningScreen")
    object CreateCompetition : Screens("CreateCompetition")
    object CreatedCompetition : Screens("CreatedCompetition")
    object EditProfile : Screens("EditProfile")
    object ProfileScreen : Screens("ProfileScreen")
    object AdminProfile: Screens("AdminProfile")
    object PostScreen : Screens("PostScreen")
    object JoinCompetiton: Screens("JoinCompetition/{id}")
    {
        fun passId(id: String): String = "JoinCompetition/$id"
    }
    object AdminApprovalScreen: Screens("AdminApprovalScreen/{id}")
    {
        fun passId(id: String): String = "AdminApprovalScreen/$id"
    }
    object VideoList : Screens("VideoList/{classLevel}") {
        fun passClass(classLevel: Int): String = "VideoList/$classLevel"
    }
    object AddVideoFirestore : Screens("AddVideoFirestore")
}