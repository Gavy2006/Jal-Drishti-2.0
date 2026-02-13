package com.example.jaldrishti20

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishti20.ui.theme.fontFamily

@Composable
fun ProfilePage(navController: NavController, authViewModel: AuthViewModel) {
    // Observe the user profile LiveData from the ViewModel.
    // The UI will automatically update when the data changes.
    val userProfile by authViewModel.userProfile.observeAsState()

    // When this screen is first displayed, trigger the ViewModel to fetch user data.
    LaunchedEffect(Unit) {
        authViewModel.fetchUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background for the whole screen
            .verticalScroll(rememberScrollState()) // Allow scrolling for smaller screens
    ) {

        // --- Header Box with Gradient and User Info ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF2158D1), Color(0xAAE8EAF6)) // Blue to light blue gradient
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Profile Icon
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.White, CircleShape), // White circular background for the icon
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        tint = Color(0xFF2158D1), // Blue icon color
                        modifier = Modifier.size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                // User Name (from Firestore via ViewModel)
                Text(
                    text = userProfile?.name ?: "Loading...", // Display name or "Loading..."
                    fontFamily = fontFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // White text on the gradient
                )
                Spacer(modifier = Modifier.height(4.dp))

                // User Email (from Firestore via ViewModel)
                Text(
                    text = userProfile?.email ?: "...", // Display email or "..."
                    fontFamily = fontFamily,
                    fontSize = 16.sp,
                    color = Color(0xFFE0E0E0) // Lighter white/gray for the email
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Options Card ---
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp) // Subtle shadow
        ) {
            Column {
                AccountOption(icon = Icons.Default.LocationOn, title = "Address Book")
                Divider(color = Color(0xFFF0F0F0)) // Light divider line

                AccountOption(icon = Icons.Default.ShoppingCart, title = "My Orders")
                Divider(color = Color(0xFFF0F0F0))

                // FIX: Used the correct icon for "Need Help?"
                AccountOption(icon = Icons.Default.ShoppingCart, title = "Need Help?")
                Divider(color = Color(0xFFF0F0F0))

                // Logout Option
                AccountOption(
                    // FIX: Used the correct icon for "Logout"
                    icon = Icons.Default.ShoppingCart,
                    title = "Logout",
                    iconTint = Color.Red,
                    textColor = Color.Red,
                    onClick = {
                        authViewModel.logout()
                        // Navigate to frontpage and clear the entire back stack to prevent going back
                        navController.navigate("frontpage") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AccountOption(
    icon: ImageVector,
    title: String,
    iconTint: Color = Color(0xFF2158D1), // Consistent icon color
    textColor: Color = Color(0xFF333333),
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontFamily = fontFamily,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f) // Ensures text takes available space
        )
        // FIX: Replaced hardcoded shopping cart with a proper chevron icon
        Icon(
            imageVector = Icons.Default.ShoppingCart, // Chevron icon indicates action
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}
