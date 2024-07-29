package com.ionexa.nextgsi


import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.DataClass.Order
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.MVVM.ProfileMVVM
import com.ionexa.nextgsi.Pages.HomePage
import com.ionexa.nextgsi.Pages.LoginPage
import com.ionexa.nextgsi.Pages.MapeWithSerchBar
import com.ionexa.nextgsi.Pages.NaviGatationWithFloatingActionButton
import com.ionexa.nextgsi.Pages.OrderHistoryScreen
import com.ionexa.nextgsi.Pages.OrderTrackingscreen
import com.ionexa.nextgsi.Pages.ProfilePage
import com.ionexa.nextgsi.Pages.Splashscreen
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.SingleTon.getSuggestions
import com.ionexa.nextgsi.ui.theme.Mediumpurple
import com.ionexa.nextgsi.ui.theme.NextGsiTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val HomeViewModel by viewModels<HomeMVVM>()
    private val ProfileViewModel by viewModels<ProfileMVVM>()
    private val MapeViewModel by viewModels<MapeKCMVVM>()
    private val orderList = mutableListOf<Order>()
    private val locationProvider by lazy { LocationProvider(this) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            HideSystemUI()
            addLocalData()
            Navigation.navController= rememberNavController()


            Main(
                LoginViewModel = LoginViewModel,
                HomeViewModel = HomeViewModel,
                ProfileViewModel = ProfileViewModel,
                navController =Navigation.navController
                ,MapeViewModel = MapeViewModel,
                locatationprovider = locationProvider
            )



        }
    }
    private fun addLocalData() {
        val orders = listOf(
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 1", "Details of Item 1", "₹100"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 2", "Details of Item 2", "₹200"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 3", "Details of Item 3", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 4", "Details of Item 4", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 5", "Details of Item 5", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 6", "Details of Item 6", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 7", "Details of Item 7", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 8", "Details of Item 8", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 9", "Details of Item 9", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 10", "Details of Item 10", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 11", "Details of Item 11", "₹400")
        )
        orderList.addAll(orders)
    }

}







@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Main(
    navController: NavHostController,
    LoginViewModel: Loginmvvm,
    HomeViewModel: HomeMVVM,
    ProfileViewModel: ProfileMVVM,
    MapeViewModel: MapeKCMVVM,
    locatationprovider:LocationProvider
) {
    val duratation=500
    NavHost(navController = navController, startDestination = NaveLabels.SplashScreen,
        enterTransition ={ fadeIn(tween(durationMillis = duratation)) },
        exitTransition = { fadeOut(tween(durationMillis = duratation)) }
        ) {
        composable(NaveLabels.Login) {
            NextGsiTheme {
                LoginPage(LoginViewModel, navController)
            }
        }
        composable(NaveLabels.SplashScreen) {
            Splashscreen(modifier = Modifier, navController)
        }
        composable(NaveLabels.Home) {
            ScreenWithBottomBar(navController) { innerPadding ->
                HomePage(modifier = Modifier.padding(innerPadding), navController, HomeViewModel, locationProvider  = locatationprovider, mapViewModel = MapeViewModel)
            }
        }
        composable(NaveLabels.Profile) {
            ScreenWithBottomBar(navController) { innerPadding ->
                ProfilePage(modifier = Modifier.padding(innerPadding), navController,ProfileViewModel= ProfileViewModel)
            }
        }
        composable(NaveLabels.SerchWithLocatation) {
            ScreenWithBottomBar(navController) { innerPadding ->
                MapeWithSerchBar(mapeKCMVVM = MapeViewModel)
            }
        }
        composable(NaveLabels.Tracking) {
            ScreenWithBottomBar(navController) { innerPadding ->
                OrderTrackingscreen(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(top = 30.dp, start = 10.dp, end = 10.dp),
                    imageUrl = listOf(
                        "https://plus.unsplash.com/premium_photo-1683865776032-07bf70b0add1?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    ),
                    MapeViewModel = MapeViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ScreenWithBottomBar(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
   Box {
       Column {
           content(PaddingValues(0.dp))
       }

       Row(
           Modifier
               .fillMaxWidth()
               .fillMaxHeight(), verticalAlignment = Alignment.Bottom) {
           NaviGatationWithFloatingActionButton(
               NaveContainerColor = Mediumpurple,
               FloatingActionButtonIconSize = 50.dp,
               ButtonFour = { navController.navigate(NaveLabels.Profile) },
               ButtonOne = { navController.navigate(NaveLabels.Home) },
               FloatingButton = { navController.navigate(NaveLabels.Tracking) }
           )
       }


   }

}

fun getWindowInsetsController(context: Context): WindowInsetsControllerCompat {
    val window = (context as? ComponentActivity)?.window
    return WindowInsetsControllerCompat(window!!, window.decorView)
}
@Composable
fun HideSystemUI() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val windowInsetsController = getWindowInsetsController(context)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}
