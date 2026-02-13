package com.example.jaldrishti20

import CartPage
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(authViewModel: AuthViewModel) {

    val navccontroller = rememberNavController()

    // DETERMINING START DESTINATION
    // If authViewModel.currentUser is not null, it means the user is already logged in.
    // We check this state immediately to skip the login/signup screens.
    val startDest = if (authViewModel.currentUser != null) "home" else "frontpage"

    NavHost(
        navController = navccontroller,
        startDestination = startDest
    ) {

        composable("frontpage") {
            FontPage(navccontroller)
        }

        composable("signuppage") {
            SignUpPage(navccontroller, authViewModel = authViewModel)
        }

        composable("loginpage") {
            LoginPage(navccontroller, authViewModel = authViewModel)
        }

        composable("home") {
            HomePage(navccontroller)
        }

        composable("profile") {
            ProfilePage(navccontroller , authViewModel)
        }

        composable("feasible") {
            FeasibilityPage(navccontroller)
        }

        composable("maintenance") {
            NotificationScreen()
        }

        composable("ar") {
            ArPage()
        }

        composable("dash") {
            DashboardPage()
        }

        composable("list") {
            FeaturedProductsScreen(navccontroller)
        }

        composable("news") {
            NewsScreen(navccontroller)
        }

        composable("Feasibility") {
            FeasibilityPage(navccontroller)
        }
        composable("cart") {
            CartPage(navccontroller)
        }

        composable("vendor") {
            VendorPage(navController = navccontroller)
        }
    }
}