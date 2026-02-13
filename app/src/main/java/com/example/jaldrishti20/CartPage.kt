import androidx.compose.ui.res.painterResource
import com.example.jaldrishti20.R

import android.R.attr.color
import android.R.attr.fontWeight
import android.R.attr.text
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jaldrishti20.BottomNavItem
import com.example.jaldrishti20.ui.theme.fontFamily

private val PrimaryBlue = Color(0xFF2158D1)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(navController: NavController) {

    val blue = PrimaryBlue
    val lightGray = Color(0xFFF5F5F5)
    val textGray = Color(0xFF9E9E9E)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Products",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* cart action */ }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = blue
                )
            )
        },
        bottomBar = {
            BottomNavBarC(navController = navController)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Empty cart icon
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = blue.copy(alpha = 0.6f),
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No products found",
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(6.dp))

            }
        }
    }
}



@Composable
fun BottomNavBarC(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(4) }

    NavigationBar(
        containerColor = PrimaryBlue,
        modifier = Modifier.height(82.dp)
    ) {

        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedIndex == 0) PrimaryBlue else Color.White
                )
            },
            label = "Home",
            selected = selectedIndex == 0
        ) {
            selectedIndex = 0
            navController.navigate("home")
        }


        BottomNavItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.vendor211),
                    contentDescription = "Vendor",
                    tint = if (selectedIndex == 1) PrimaryBlue else Color.White
                )
            },
            label = "Vendor",
            selected = selectedIndex == 1
        ) {
            selectedIndex = 1
            navController.navigate("vendor")
        }



        BottomNavItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.scheme),
                    contentDescription = "Scheme",
                    tint = if (selectedIndex == 2) PrimaryBlue else Color.White
                )
            },
            label = "Scheme",
            selected = selectedIndex == 2
        ) {
            selectedIndex = 2
            navController.navigate("news")
        }

        BottomNavItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.product),
                    contentDescription = "Products",
                    tint = if (selectedIndex == 3) PrimaryBlue else Color.White
                )
            },
            label = "Products",
            selected = selectedIndex == 3
        ) {
            selectedIndex = 3
            navController.navigate("list")
        }



        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = if (selectedIndex == 4) PrimaryBlue else Color.White
                )
            },
            label = "Cart",
            selected = selectedIndex == 4
        ) {
            selectedIndex = 4
            navController.navigate("cart")
        }

    }
}

@Composable
fun RowScope.BottomNavItemC(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        if (selected) Color.White.copy(alpha = 0.9f)
                        else Color.Transparent,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = if (selected) PrimaryBlue else Color.White
                )
            }
        },
        label = {
            Text(
                label,
                fontFamily = fontFamily,
                fontSize = 10.sp,
                color = Color.White
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}
