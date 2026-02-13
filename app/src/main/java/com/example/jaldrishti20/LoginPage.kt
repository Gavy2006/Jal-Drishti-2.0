package com.example.jaldrishti20

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishti20.ui.theme.fontFamily

object LoginUIStyle {
    val TitleFontSize = 32.sp
    val SubtitleFontSize = 16.sp
    val LabelFontSize = 14.sp
    val PlaceholderFontSize = 14.sp
    val ButtonFontSize = 18.sp
    val FooterFontSize = 16.sp

    val TitleFontWeight = FontWeight.Bold
    val SubtitleFontWeight = FontWeight.Normal
    val LabelFontWeight = FontWeight.SemiBold
    val ButtonFontWeight = FontWeight.Bold
    val FooterFontWeight = FontWeight.Normal
    val FooterLinkFontWeight = FontWeight.Bold

    val TitleColor = Color(0xFF333333)
    val SubtitleColor = Color(0xFF666666)
    val LabelColor = Color(0xFF000000)
    val PlaceholderColor = Color(0xFFBDBDBD)
    val ButtonTextColor = Color.White
    val FooterColor = Color(0xFF666666)
    val FooterLinkColor = Color(0xFF1565C0)

    val InputHeight = 64.dp
    val InputRadius = 8.dp
    val InputBackgroundColor = Color.White
    val IconSize = 20.dp

    val HorizontalPadding = 20.dp
    val VerticalSpacingLarge = 32.dp
}

@Composable
fun LoginPage(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 25.dp, start = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LoginUIStyle.HorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(25.dp))
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(84.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Log In",
                fontFamily = fontFamily,
                fontSize = LoginUIStyle.TitleFontSize,
                fontWeight = LoginUIStyle.TitleFontWeight,
                color = LoginUIStyle.TitleColor,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = "Sign in to continue using our app",
                fontFamily = fontFamily,
                fontSize = LoginUIStyle.SubtitleFontSize,
                fontWeight = LoginUIStyle.SubtitleFontWeight,
                color = LoginUIStyle.SubtitleColor,
                modifier = Modifier.padding(bottom = 38.dp)
            )

            // Form Fields
            Column {
                // Email Field
                InputFieldLabel("Email")
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(LoginUIStyle.IconSize)) },
                    placeholder = { Text("Enter your email", fontSize = LoginUIStyle.PlaceholderFontSize) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(LoginUIStyle.InputRadius),
                    modifier = Modifier.fillMaxWidth().height(LoginUIStyle.InputHeight),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Password Field
                InputFieldLabel("Password")
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(LoginUIStyle.IconSize)) },
                    placeholder = { Text("Enter your Password", fontSize = SignUpUIStyle.PlaceholderFontSize) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(LoginUIStyle.InputRadius),
                    modifier = Modifier.fillMaxWidth().height(LoginUIStyle.InputHeight),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            // Login Button with persistent session logic
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.login(email, password) { success, errorMessage ->
                            if (success) {
                                // Navigate to home and clear navigation history
                                navController.navigate("home") {
                                    popUpTo("frontpage") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, errorMessage ?: "Login Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LoginUIStyle.InputHeight),
                shape = RoundedCornerShape(LoginUIStyle.InputRadius),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2158D1))
            ) {
                Text(
                    text = "Log In",
                    fontFamily = fontFamily,
                    fontSize = LoginUIStyle.ButtonFontSize,
                    fontWeight = LoginUIStyle.ButtonFontWeight,
                    color = LoginUIStyle.ButtonTextColor
                )
            }

            //Spacer(modifier = Modifier.height(LoginUIStyle.VerticalSpacingLarge))

            TextButton(
                onClick = { navController.navigate("signuppage") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Text(
                        text = "Don't have an account?",
                        fontFamily = fontFamily,
                        fontSize = LoginUIStyle.FooterFontSize,
                        fontWeight = LoginUIStyle.FooterFontWeight,
                        color = LoginUIStyle.FooterColor
                    )
                    Text(
                        text = " Sign Up",
                        fontFamily = fontFamily,
                        fontSize = LoginUIStyle.FooterFontSize,
                        fontWeight = LoginUIStyle.FooterLinkFontWeight,
                        color = LoginUIStyle.FooterLinkColor
                    )
                }
            }
        }
    }
}

@Composable
fun InputFieldLabel(label: String) {
    Text(
        text = label,
        fontFamily = fontFamily,
        fontWeight = LoginUIStyle.LabelFontWeight,
        fontSize = LoginUIStyle.LabelFontSize,
        color = LoginUIStyle.LabelColor,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}