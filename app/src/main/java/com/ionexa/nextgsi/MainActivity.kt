package com.ionexa.nextgsi

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.google.firebase.auth.FirebaseAuth
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.Components.FilePicker
import com.ionexa.nextgsi.DataClass.Order
import com.ionexa.nextgsi.FIreBase.FireBaseStorage
import com.ionexa.nextgsi.FIreBase.FirebaseAuthManager
import com.ionexa.nextgsi.FIreBase.FirebaseGoogleAuth
import com.ionexa.nextgsi.MVVM.*
import com.ionexa.nextgsi.Pages.*
import com.ionexa.nextgsi.SingleTon.*
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
    private val SignInMVVM by viewModels<SignInMVVM>()
    private val OTP by viewModels<OtpVerificationViewModel>()
    private val googleAuthUiClient by lazy {
        FirebaseGoogleAuth(
            context = applicationContext,
            oneTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(
                applicationContext
            )
        )
    }

    companion object {
        val FBAuth: FirebaseAuth = FirebaseAuth.getInstance()
    }

    val FBauthManager = FirebaseAuthManager()
val fbsb=FireBaseStorage()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var corutinescope = rememberCoroutineScope()
            HideSystemUI()
            addLocalData()
            Navigation.navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            val currentUser = authViewModel.currentUser.observeAsState()

            if (currentUser.value != null) {
                NaveLabels.DefaultLoag = NaveLabels.Home
            } else {
                NaveLabels.DefaultLoag = NaveLabels.SplashScreen
            }

            Main(
                loginViewModel = LoginViewModel,
                homeViewModel = HomeViewModel,
                profileViewModel = ProfileViewModel,
                navController = Navigation.navController,
                mapeViewModel = MapeViewModel,
                locationProvider = locationProvider,
                orderList = orderList,
                signInMVVM = SignInMVVM,
                googleAuthUiClient = googleAuthUiClient,
                FBauthManager = FBauthManager,
                OTP = OTP,
                activity = this
            )

           /*

           * */
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
    loginViewModel: Loginmvvm,
    homeViewModel: HomeMVVM,
    profileViewModel: ProfileMVVM,
    mapeViewModel: MapeKCMVVM,
    locationProvider: LocationProvider,
    orderList: MutableList<Order>,
    signInMVVM: SignInMVVM,
    googleAuthUiClient: FirebaseGoogleAuth,
    FBauthManager: FirebaseAuthManager,
    OTP: OtpVerificationViewModel,
    activity: MainActivity
) {
    val coroutineScope = rememberCoroutineScope()
    val duration = 300
    val state by signInMVVM.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data ?: return@rememberLauncherForActivityResult
            (context as? ComponentActivity)?.lifecycleScope?.launch {
                val signInResult = googleAuthUiClient.getSignInResultFromIntebt(intent)
                signInMVVM.onSignInResult(signInResult)
            }
        }
    }

    NavHost(navController = navController,
        startDestination = NaveLabels.DefaultLoag,
        enterTransition = { fadeIn(tween(durationMillis = duration)) },
        exitTransition = { fadeOut(tween(durationMillis = duration)) }) {
        composable(NaveLabels.Login) {
            NextGsiTheme {
                LoginPage(
                    loginViewModel,
                    FBauthManager = FBauthManager,
                    navController = navController,
                    state = state,
                    OTP = OTP
                ) {
                    (context as? ComponentActivity)?.lifecycleScope?.launch {
                        val signInIntentSender = googleAuthUiClient.signinwithgoogle()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            }
        }
        composable(NaveLabels.SplashScreen) {
            Splashscreen(modifier = Modifier, navController)
        }
        composable(NaveLabels.Home) {
            ScreenWithBottomBar(navController) { innerPadding ->
                HomePage(
                    modifier = Modifier.padding(innerPadding),
                    homeViewModel = homeViewModel,
                    locationProvider = locationProvider,
                    mapViewModel = mapeViewModel
                )
            }
        }
        composable(NaveLabels.Profile) {
            ScreenWithBottomBar(navController) { innerPadding ->
                ProfilePage(modifier = Modifier.padding(innerPadding),
                    naveController = navController,
                    ProfileViewModel = profileViewModel,
                    googleUserData = googleAuthUiClient.getSignedInUser(),
                    LogOutPRofile = {
                        coroutineScope.launch {
                            googleAuthUiClient.signOut();
                            navController.navigate(NaveLabels.Login)
                        }


                    })
            }
        }
        composable(NaveLabels.SerchWithLocatation) {
            ScreenWithBottomBar(navController) { innerPadding ->
                MapeWithSerchBar(mapeKCMVVM = mapeViewModel)
            }
        }
        composable(NaveLabels.OTPVerificatation) {
            OtpVerification(activity = activity, viewModel = OTP)
        }
        composable(NaveLabels.Cart) {
            ScreenWithBottomBar(navController) { innerPadding ->
                CartPage()
            }
        }
        composable(NaveLabels.CartHistory) {
            ScreenWithBottomBar(navController) { innerPadding ->
                OrderHistoryScreen(orderList = orderList)
                
            }
        }
        composable(NaveLabels.AboutUs) {
            ScreenWithBottomBar(navController) { innerPadding ->
                AboutUs()
            }
        }
        composable(NaveLabels.AccountPravicy) {
            ScreenWithBottomBar(navController) { innerPadding ->
                AccountPravicy()
            }
        }
        composable(NaveLabels.product) {
            ScreenWithBottomBar(navController) { innerPadding ->
                ProductDetailScreen()
            }
        }
        composable(NaveLabels.Tracking) {
            ScreenWithBottomBar(navController) { innerPadding ->
                OrderTrackingscreen(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(top = 30.dp, start = 10.dp, end = 10.dp), imageUrl = listOf(
                        "https://plus.unsplash.com/premium_photo-1683865776032-07bf70b0add1?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    ), MapeViewModel = mapeViewModel, navController = navController
                )
            }
        }
    }
}

@Composable
fun ScreenWithBottomBar(
    navController: NavHostController, content: @Composable (PaddingValues) -> Unit
) {
    Box {
        Column {
            content(PaddingValues(0.dp))
        }

        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(), verticalAlignment = Alignment.Bottom
        ) {
            NaviGatationWithFloatingActionButton(NaveContainerColor = Mediumpurple,
                FloatingActionButtonIconSize = 50.dp,
                ButtonFour = { navController.navigate(NaveLabels.Profile) },
                ButtonOne = { navController.navigate(NaveLabels.Home) },
                ButtonTwo = { navController.navigate(NaveLabels.Cart) },
                FloatingButton = { navController.navigate(NaveLabels.Tracking) })
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
