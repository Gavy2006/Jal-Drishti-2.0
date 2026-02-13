package com.example.jaldrishti20

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import com.example.jaldrishti20.ui.theme.fontFamily

private val PrimaryBlue = Color(0xFF2158D1)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorPage(navController: NavController) {

    val lightBg = Color(0xFFF3F7F9)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Rooftop RWH Connect",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue
                )
            )
        },
        bottomBar = {
            BottomNavBarV(navController)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(lightBg)
        ) {

            SearchVendorBar()
            VendorTabs()

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    VendorCard(
                        name = "AquaSave Systems",
                        subtitle = "Installation & Maintenance",
                        buttonText = "Connect Now"
                    )
                }
                item {
                    VendorCard(
                        name = "RainHarvest Pro",
                        subtitle = "Consultation & Equipment",
                        buttonText = "Connect Now"
                    )
                }
                item {
                    VendorCard(
                        name = "GreenDrops Experts",
                        subtitle = "Specializes in filtration",
                        buttonText = "Connect Now"
                    )
                }
            }
        }
    }
}

@Composable
fun SearchVendorBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, null, tint = Color.Gray)
        Spacer(Modifier.width(8.dp))
        Text(
            "Find experts near you...",
            fontFamily = fontFamily,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Default.Place, null, tint = PrimaryBlue)
    }
}

@Composable
fun VendorTabs() {
    val tabs = listOf("Installation", "Maintenance", "Supply")
    var selectedTab by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, text ->
            Text(
                text = text,
                fontFamily = fontFamily,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                color = if (selectedTab == index) PrimaryBlue else Color.Gray,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable { selectedTab = index }
            )
        }
    }
}

@Composable
fun VendorCard(
    name: String,
    subtitle: String,
    buttonText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = PrimaryBlue.copy(alpha = 0.12f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = subtitle,
                    fontFamily = fontFamily,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(Modifier.width(4.dp))
                }
            }
            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                border = BorderStroke(1.dp, PrimaryBlue),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White, // background color
                    contentColor = PrimaryBlue    // text/icon color
                )
            ) {
                Text(
                    text = buttonText,
                    fontFamily = fontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        }
    }
}

@Composable
fun BottomNavBarV(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(1) }

    NavigationBar(
        containerColor = PrimaryBlue,
        modifier = Modifier.height(82.dp)
    ) {

        BottomNavItem(
            icon = { NavIcon(Icons.Default.Home, selectedIndex == 0) },
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
            icon = { NavIcon(painterResource(R.drawable.scheme), selectedIndex == 2) },
            label = "Scheme",
            selected = selectedIndex == 2
        ) {
            selectedIndex = 2
            navController.navigate("news")
        }

        BottomNavItem(
            icon = { NavIcon(painterResource(R.drawable.product), selectedIndex == 3) },
            label = "Products",
            selected = selectedIndex == 3
        ) {
            selectedIndex = 3
            navController.navigate("list")
        }

        BottomNavItem(
            icon = { NavIcon(Icons.Default.ShoppingCart, selectedIndex == 4) },
            label = "Cart",
            selected = selectedIndex == 4
        ) {
            selectedIndex = 4
            navController.navigate("cart")
        }
    }
}

@Composable
fun RowScope.BottomNavItemV(
    icon: @Composable () -> Unit,
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
                    .size(40.dp)
                    .background(if (selected) Color.White else Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(Modifier.size(24.dp)) { icon() }
            }
        },
        label = {
            Text(label, fontSize = 10.sp, color = Color.White)
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}

@Composable
fun NavIcon(icon: Any, selected: Boolean) {
    when (icon) {
        is androidx.compose.ui.graphics.vector.ImageVector ->
            Icon(icon, null, tint = if (selected) PrimaryBlue else Color.White, modifier = Modifier.fillMaxSize())
        is androidx.compose.ui.graphics.painter.Painter ->
            Icon(icon, null, tint = if (selected) PrimaryBlue else Color.White, modifier = Modifier.fillMaxSize())
    }
}
