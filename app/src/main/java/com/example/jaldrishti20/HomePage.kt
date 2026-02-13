package com.example.jaldrishti20
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
import com.example.jaldrishti20.ui.theme.fontFamily

private val PrimaryBlue = Color(0xFF2158D1)

@Composable
fun HomePage(navController: NavController) {
    Scaffold(
        topBar = { HomeTopBar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {


            Divider(
                thickness = 3.8.dp,
                color = Color.White.copy(alpha = 0.3f)
            )

            HeroSection()

            LazyColumn {
                item {
                    CategorySection(navController)
                }
                item {
                    FeaturedSection(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Logo",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Jal Drishti",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        actions = {

            IconButton(
                onClick = {},
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }

            IconButton(
                onClick = {navController.navigate("maintenance")},
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }

            IconButton(
                onClick = {navController.navigate("profile")},
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryBlue,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2158D1))
            .padding(16.dp)

    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Find Everything You Need",
            fontFamily = fontFamily,
            color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Expert size assessment , verified \nvendors & smart harvesting",
            fontFamily = fontFamily,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        SearchBar()

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Search for products...",
            fontFamily = fontFamily,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Default.Home,
            contentDescription = null,
            tint = PrimaryBlue
        )
    }
}

data class ServiceItem(
    val title: String,
    val color: Color,
    val route: String,
    val imageRes: Int
)

val serviceList = listOf(
    ServiceItem(
        title = "Feasibility",
        color = Color(0xFFE3F2FD),
        route = "feasible",
        imageRes = R.drawable.tick
    ),
    ServiceItem(
        title = "Dashboard",
        color = Color(0xFFE8F5E9),
        route = "dash",
        imageRes = R.drawable.dash
    ),
    ServiceItem(
        title = "AR Estimation",
        color = Color(0xFFFFF3E0),
        route = "ar",
        imageRes = R.drawable.ar
    ),
    ServiceItem(
        title = "Maintenance",
        color = Color(0xFFE3F2FD),
        route = "maintenance",
        imageRes = R.drawable.maintenance
    ) ,
    ServiceItem(
        title = "Awareness",
        color = Color(0xFFFCE4EC),
        route = "news",
        imageRes = R.drawable.awareness
    )
)

@Composable
fun ServiceSectionCard(
    title: String,
    bgColor: Color,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(115.dp)
            .height(140.dp)
            .clickable { onClick() }
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(75.dp)
                .background(bgColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier.size(54.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun CategorySection(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Quick Services",
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow {
            items(serviceList) { item ->
                ServiceSectionCard(
                    title = item.title,
                    bgColor = item.color,
                    imageRes = item.imageRes
                ) {
                    navController.navigate(item.route)
                }
            }
        }
    }
}

@Composable
fun FeaturedSection(navController: NavController) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 12.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Featured Products",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            TextButton(
                onClick = { navController.navigate("list") },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "View All",
                    color = Color(0xFF2158D1),
                    fontFamily = fontFamily
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow {
            items(featuredProductss) { product ->
                FeaturedProductCard(product)
            }
        }
    }
}

data class FeaturedProductss(
    val name: String,
    val price: String,
    val image: Int
)
val featuredProductss = listOf(
    FeaturedProductss(
        name = "Water Diversion System",
        price = "₹3539",
        image = R.drawable.img_10
    ),
    FeaturedProductss(
        name = "Water purifier pump",
        price = "₹3500",
        image = R.drawable.img_7
    )
)

@Composable
fun FeaturedProductCard(product: FeaturedProductss) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(170.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {

        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(product.image),
                contentDescription = product.name,
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.name,
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            maxLines = 1
        )

        Text(
            text = product.price,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun BottomNavBar(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(0) }

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
fun RowScope.BottomNavItem(
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
                    .size(40.dp) // container size
                    .background(
                        if (selected) Color.White else Color.Transparent,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(24.dp) // 🔥 ICON SIZE (same for all)
                ) {
                    icon()
                }
            }
        },
        label = {
            Text(
                text = label,
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


