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
import com.example.jaldrishti20.R
import com.example.jaldrishti20.ui.theme.fontFamily

object SignUpUIStyle {
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
    val IconSize = 20.dp

    val HorizontalPadding = 20.dp
}

@Composable
fun SignUpPage(navController: NavController, authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(SignUpUIStyle.HorizontalPadding)
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
            text = "Create Account",
            fontFamily = fontFamily,
            fontSize = SignUpUIStyle.TitleFontSize,
            fontWeight = SignUpUIStyle.TitleFontWeight,
            color = SignUpUIStyle.TitleColor,
            modifier = Modifier.padding(bottom = 12.dp).align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Sign up to get started",
            fontFamily = fontFamily,
            fontSize = SignUpUIStyle.SubtitleFontSize,
            fontWeight = SignUpUIStyle.SubtitleFontWeight,
            color = SignUpUIStyle.SubtitleColor,
            modifier = Modifier.padding(bottom = 18.dp).align(Alignment.CenterHorizontally)
        )

        // Name Field
        SignUpLabel("Name")
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(SignUpUIStyle.IconSize)) },
            placeholder = { Text("Enter your full name", fontSize = SignUpUIStyle.PlaceholderFontSize) },
            shape = RoundedCornerShape(SignUpUIStyle.InputRadius),
            modifier = Modifier.fillMaxWidth().height(SignUpUIStyle.InputHeight),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Email Field
        SignUpLabel("Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(SignUpUIStyle.IconSize)) },
            placeholder = { Text("Enter your email", fontSize = SignUpUIStyle.PlaceholderFontSize) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(SignUpUIStyle.InputRadius),
            modifier = Modifier.fillMaxWidth().height(SignUpUIStyle.InputHeight),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Password Field
        SignUpLabel("Password")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(SignUpUIStyle.IconSize)) },
            placeholder = { Text("Enter your Password", fontSize = SignUpUIStyle.PlaceholderFontSize) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(SignUpUIStyle.InputRadius),
            modifier = Modifier.fillMaxWidth().height(SignUpUIStyle.InputHeight),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Sign Up Button with Auth logic
        Button(
            onClick = {
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    authViewModel.signup(
                        email = email,
                        name = name,
                        password = password
                    ) { success, errorMessage ->
                        if (success) {
                            // NAVIGATE AND CLEAR HISTORY
                            navController.navigate("home") {
                                popUpTo("frontpage") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, errorMessage ?: "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(SignUpUIStyle.InputHeight),
            shape = RoundedCornerShape(SignUpUIStyle.InputRadius),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text(
                text = "Sign Up",
                fontFamily = fontFamily,
                fontSize = SignUpUIStyle.ButtonFontSize,
                fontWeight = SignUpUIStyle.ButtonFontWeight,
                color = SignUpUIStyle.ButtonTextColor
            )
        }


        // Footer
        TextButton(
            onClick = { navController.navigate("loginpage") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = "Already have an account?",
                    fontFamily = fontFamily,
                    fontSize = SignUpUIStyle.FooterFontSize,
                    fontWeight = SignUpUIStyle.FooterFontWeight,
                    color = SignUpUIStyle.FooterColor
                )
                Text(
                    text = " Log In",
                    fontFamily = fontFamily,
                    fontSize = SignUpUIStyle.FooterFontSize,
                    fontWeight = SignUpUIStyle.FooterLinkFontWeight,
                    color = SignUpUIStyle.FooterLinkColor
                )
            }
        }
    }
}

@Composable
fun SignUpLabel(text: String) {
    Text(
        text = text,
        fontFamily = fontFamily,
        fontWeight = SignUpUIStyle.LabelFontWeight,
        fontSize = SignUpUIStyle.LabelFontSize,
        color = SignUpUIStyle.LabelColor,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}