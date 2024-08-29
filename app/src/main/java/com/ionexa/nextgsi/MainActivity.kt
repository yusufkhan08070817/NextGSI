package com.ionexa.nextgsi

import SellerInfo
import SellerProfile
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.DataClass.Customer
import com.ionexa.nextgsi.DataClass.Order
import com.ionexa.nextgsi.DataClass.ProductTypeId
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.FBFireBase.FireBaseStorage
import com.ionexa.nextgsi.FBFireBase.FireBaseStoreDBMyClass
import com.ionexa.nextgsi.FBFireBase.FirebaseAuthManager
import com.ionexa.nextgsi.FBFireBase.FirebaseGoogleAuth


import com.ionexa.nextgsi.MVVM.*
import com.ionexa.nextgsi.Pages.*
import com.ionexa.nextgsi.SingleTon.*
import com.ionexa.nextgsi.SingleTon.common.db
import com.ionexa.nextgsi.ui.theme.Mediumpurple
import com.ionexa.nextgsi.ui.theme.NextGsiTheme
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), PaymentResultListener {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val HomeViewModel by viewModels<HomeMVVM>()
    private val ProfileViewModel by viewModels<ProfileMVVM>()
    private val MapeViewModel by viewModels<MapeKCMVVM>()
    private val orderList = mutableListOf<Order>()
    private val locationProvider by lazy { LocationProvider(this) }
    private val SignInMVVM by viewModels<SignInMVVM>()
    private val OTP by viewModels<OtpVerificationViewModel>()
    private val ProductpageMvvm by viewModels<ProductpageMvvm>()
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
        val FBauthManager = FirebaseAuthManager()
        val fbsb = FireBaseStorage()
        val FireBaseStorage = FireBaseStorage()

    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)

        // Initialize App Check
        val appCheck = FirebaseAppCheck.getInstance()
        appCheck.installAppCheckProviderFactory(SafetyNetAppCheckProviderFactory.getInstance())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            var corutinescope = rememberCoroutineScope()
            HideSystemUI()
            addLocalData()
            Navigation.navController = rememberNavController()
            val fsdb = FSDB()
            val authViewModel: AuthViewModel = viewModel()
            val currentUser = authViewModel.currentUser.observeAsState()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1
                )
            }
            if (currentUser.value != null) {
                common.myid.value = common.replaceSpecialChars(
                    currentUser.value!!.email ?: common.replaceSpecialChars(LoginViewModel.email)
                )

                Log.e("get uid", "uid")
            }


            // Fetch the user token
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    // Send this token to your server or store it for future use
                    println("User Token: $token")
                    Log.e("userToken", token)
                }
            }

            LaunchedEffect(
                key1 = MapeViewModel.currentLatitude, key2 = MapeViewModel.currentLongitude
            ) {
                CoroutineScope(Dispatchers.IO).launch {

                    val locationName = getLocationName(
                        MapeViewModel.currentLatitude.toString(),
                        MapeViewModel.currentLongitude.toString()
                    )
                    // Use the result
                    Log.d("LocationName", locationName)
                }
            }

            /*


             */

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
                ProductpageMvvm = ProductpageMvvm,

                activity = this
            )

            /*


                         */
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

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        Toast.makeText(this, "Payment Successful: $razorpayPaymentID", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(code: Int, description: String?) {
        Toast.makeText(this, "Payment Failed: $description", Toast.LENGTH_LONG).show()
    }

}


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
    ProductpageMvvm: ProductpageMvvm,
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



    Toast.makeText(context, NaveLabels.DefaultLoag, Toast.LENGTH_SHORT).show()
    NavHost(navController = navController,
        startDestination = NaveLabels.SplashScreen,
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
              //  Toast.makeText(context, "role " + common.roll, Toast.LENGTH_SHORT).show()
                HomePage(
                    modifier = Modifier.padding(innerPadding),
                    homeViewModel = homeViewModel,
                    locationProvider = locationProvider,
                    mapViewModel = mapeViewModel,
                    ProductpageMvvm = ProductpageMvvm,
                    navController = navController
                )
            }
        }
        composable(NaveLabels.Profile) {
            ScreenWithBottomBar(navController) { innerPadding ->
                ProfilePage(modifier = Modifier.padding(innerPadding),
                    naveController = navController,
                    ProfileViewModel = profileViewModel,
                    googleUserData = googleAuthUiClient.getSignedInUser(),
                    Loginmmvm = loginViewModel,
                    LogOutPRofile = {
                    coroutineScope.launch {
                        FirebaseAuth.getInstance().signOut()
                        delay(500)
                        Navigation.navController.navigate(NaveLabels.Login)
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
            OtpVerification(activity = activity, viewModel = OTP, loginViewModel = loginViewModel)
        }
        composable(NaveLabels.Cart) {
            ScreenWithBottomBar(navController) { innerPadding ->
                CartPage()
            }
        }
        composable(NaveLabels.CartHistory) {
            ScreenWithBottomBar(navController) { innerPadding ->
                OrderHistoryScreen()

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


                ProductDetailScreen(ProductpageMvvm)
            }
        }
        composable(NaveLabels.SellerAddProduct) {
            ScreenWithBottomBar(navController) { innerPadding ->
                ProductEntryPage()
            }
        }
        composable(NaveLabels.fave) {
            ScreenWithBottomBar(navController) { innerPadding ->
                FaveIteam()
            }
        }
        composable(NaveLabels.Couppen) {
            ScreenWithBottomBar(navController) { innerPadding ->
                coupons()
            }
        }
        composable(NaveLabels.CustomerCare) {
            ScreenWithBottomBar(navController) { innerPadding ->
                Customercare()
            }
        }
        composable(NaveLabels.Payment) {
            ScreenWithBottomBar(navController) { innerPadding ->
                PaymentScreen(activity)
            }
        }
        composable(NaveLabels.seller) {
         //   Toast.makeText(context, "role " + common.roll, Toast.LENGTH_SHORT).show()
            ScreenWithBottomBarseller(navController) { innerPadding ->
                ProductEntryPage()
            }
        }
        composable(NaveLabels.sellerprofile) {
            ScreenWithBottomBarseller(navController) { innerPadding ->
                var seller by remember {
                    mutableStateOf(SellerInfo(
                        name = "",
                        description = "",
                        imageUrl = "",
                        email = "",
                        phoneNumber = "",
                        chatOption = "",
                        overallRating = 9.9f,
                        customerReviews = listOf("sfsf", "SDfs", "fgdrg"),
                        returnPolicy = "",
                        warrantyInfo = "",
                        fulfillmentRate = 0.8f,
                        responseTime = "",
                        salesVolume = 87,
                        socialMediaLinks = listOf("sfsf", "SDfs", "fgdrg")
                    ))
                }

                LaunchedEffect(key1 = true) {
                    FSDB().getDataFromFireStoreDB("users", common.myid.value, onSuccess = { result ->
                        result?.let {
                            val data = it["personel_info"] as Map<String, Any>
                            Log.e("msg", "$data")

                            // Update the seller state with new data
                            seller = seller.copy(
                                name = data["name"].toString(),
                                description = data["sellerDescription"].toString(),
                                imageUrl = common.decodeFromBase64(data["profilePic"].toString()),
                                email = common.decodeFromBase64(data["email"].toString()),
                                phoneNumber = data["phone"].toString(),
                                chatOption = data["sellerChatOption"].toString(),
                                returnPolicy = data["returnPolicy"].toString(),
                                responseTime = data["responseTime"].toString()
                            )
                        }
                    }, onFailure = {
                        Log.e("Product_enter_page", it)
                    })
                }

                Log.e("msgselerprofil", "$seller")

                SellerProfile(seller) {
                    // Callback or action if needed
                }

            }
        }
        composable(NaveLabels.sellerprodectpagelist) {
            ScreenWithBottomBarseller(navController) { innerPadding ->
                var productList by remember { mutableStateOf<List<Product>>(emptyList()) }

                LaunchedEffect(key1 = true) {
                    FSDB().getDataFromFireStoreDB("users", common.myid.value, onSuccess = {
                        if (it != null) {
                            val data = it["products"] as List<Map<String, Any>>
                            Log.e("msg", "$data")

                            // Map the data and update the state
                            productList = data.map { productData ->
                                val image = productData["images"] as List<String>
                                Product(
                                    productData["productid"].toString(),
                                    productData["name"].toString(),
                                    productData["price"].toString(),
                                    common.decodeFromBase64(image[0].toString())
                                )
                            }
                        }
                    }, onFailure = {
                        Log.e("Product_enter_page", it)
                    })
                }

                // ProductList will be called only after the data is loaded and the list is updated
                if (productList.isNotEmpty()) {
                    ProductList(productList, {}, {})
                } else {
                    // Show loading or empty state if the list is still empty
                    Text("Loading products...")
                }

            }
        }
        composable(NaveLabels.Sellerinfo) {
            ScreenWithBottomBarseller(navController) { innerPadding ->
               SellerInfo()
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
                ButtonThree = { navController.navigate(NaveLabels.fave) },
                FloatingButton = { navController.navigate(NaveLabels.Tracking) })
        }
    }
}

@Composable
fun ScreenWithBottomBarseller(
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
                ButtonFour = {Navigation.navController.navigate(NaveLabels.sellerprofile) },
                ButtonOnevisibility =  true,
                Buttontwovisibility = false
                ,
                Buttonthreevisibility = false
                , NormalIconList = listOf(R.drawable.home
                ,R.drawable.home,
                    R.drawable.fave,
                    R.drawable.user
                    ),
                ButtonOne = { Navigation.navController.navigate(NaveLabels.seller)},
                ButtonTwo = { },
                ButtonThree = { },
                FloatingButton = {Navigation.navController.navigate(NaveLabels.sellerprodectpagelist) })
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


