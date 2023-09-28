package app.waste2wealth.com.reportwaste

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityOptionsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.firebase.firestore.calculatePointsEarned
import app.waste2wealth.com.firebase.firestore.updateInfoToFirebase
import app.waste2wealth.com.firebase.firestore.updateWasteToFirebase
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.rewards.levels
import app.waste2wealth.com.tags.TagItem
import app.waste2wealth.com.tags.TagsScreen
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.CardTextColor
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.baseUI.theme.monteBold
import app.waste2wealth.baseUI.theme.monteNormal
import app.waste2wealth.baseUI.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import app.waste2wealth.com.utils.DefaultSnackbar
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.storage.FirebaseStorage
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
)
@Composable
fun ReportWaste(
    navController: NavHostController,
    viewModel: LocationViewModel,
    email: String,
    name: String,
    pfp: String,
    reportWasteViewModel: ReportWasteViewModel = hiltViewModel()
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val gesturesEnabled by remember { derivedStateOf { permissionDrawerState.isOpen } }
    val coroutineScope = rememberCoroutineScope()
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var address by remember {
        mutableStateOf("")
    }
    var profileList by remember {
        mutableStateOf<List<ProfileInfo>?>(null)
    }
    var maxReported by remember {
        mutableStateOf(0)
    }
    var maxCollected by remember {
        mutableStateOf(0)
    }
    var maxCommunity by remember {
        mutableStateOf(0)
    }
    var userAddress by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    var organization by remember {
        mutableStateOf("")
    }
    var pointsEarned by remember {
        mutableStateOf(0)
    }
    var pointsRedeemed by remember {
        mutableStateOf(0)
    }
    var noOfTimesReported by remember {
        mutableStateOf(0)
    }
    var noOfTimesCollected by remember {
        mutableStateOf(0)
    }
    var noOfTimesActivity by remember {
        mutableStateOf(0)
    }

    var isDialogVisible by remember { mutableStateOf(false) }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var communities by remember {
        mutableStateOf(mutableListOf(""))
    }
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
        animationSpec = tween(
            durationMillis = 250,
            delayMillis = 0,
            easing = FastOutLinearInEasing
        )
    )

    var openTags by remember {
        mutableStateOf(false)
    }
    BackHandler {
        coroutineScope.launch {
            if (modalSheetState.isVisible) {
                // Hide the bottom sheet
                modalSheetState.hide()
            } else {
                // Show the bottom sheet
                navController.popBackStack()
            }
        }
    }

    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
        maxReported = (profileList?.map { it.noOfTimesReported.toDouble() } ?: emptyList())
            .max().toInt()

        maxCollected = (profileList?.map { it.noOfTimesCollected.toDouble() } ?: emptyList())
            .max().toInt()
        maxCommunity = (profileList?.map { it.communities.size.toDouble() } ?: emptyList())
            .max().toInt()
    }) {
        if (profileList != null) {
            for (i in profileList!!) {
                if (i.email == email) {
                    userAddress = i.address ?: ""
                    gender = i.gender ?: ""
                    phoneNumber = i.phoneNumber ?: ""
                    organization = i.organization ?: ""
                    pointsEarned = i.pointsEarned
                    pointsRedeemed = i.pointsRedeemed
                    noOfTimesReported = i.noOfTimesReported
                    noOfTimesCollected = i.noOfTimesCollected
                    noOfTimesActivity = i.noOfTimesActivity
                    communities = i.communities.toMutableList()
                }
            }
        }
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {

            },
        ) {
            LaunchedEffect(key1 = modalSheetState.isVisible){
                reportWasteViewModel.showTips.value = !modalSheetState.isVisible
            }
            LaunchedEffect(key1 = reportWasteViewModel.showTips.value){
                if (reportWasteViewModel.showTips.value && reportWasteViewModel.tagsList.size > 0) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = reportWasteViewModel.tagsList.random().tips.random(),
                        actionLabel = "That's Cool",
                        duration = SnackbarDuration.Long,
                    )
                }
            }
            PermissionDrawer(
                drawerState = permissionDrawerState,
                permissionState = permissionState,
                rationaleText = "To continue, allow Waste2Wealth to access your device's camera" +
                        ". Tap Settings > Permission, and turn \"Access Camera On\" on.",
                withoutRationaleText = "Camera permission required for this feature to be available." +
                        " Please grant the permission.",
                model = R.drawable.camera,
                gesturesEnabled = gesturesEnabled,
                size = 100.dp
            ) {
                ModalBottomSheetLayout(
                    sheetState = modalSheetState,
                    sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    sheetContent = {
                        TagsScreen(reportWasteViewModel)
                    }
                ) {
                    var isCOinVisible by remember {
                        mutableStateOf(false)
                    }


                    val context = LocalContext.current
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.TakePicturePreview(),
                        onResult = {
                            println("Bitmaps is ${it?.asImageBitmap()}")
                            imageBitmap = it?.asImageBitmap()
                            viewModel.getPlaces()
                            bitmap = it


                        }
                    )
                    Surface(
                        modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection())
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .padding(it)
                                    .fillMaxSize()
                                    .background(appBackground)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 30.dp, start = 0.dp)
                                        .clickable {
                                            navController.popBackStack()
                                        },
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBackIos,
                                        contentDescription = "",
                                        tint = textColor,
                                        modifier = Modifier
                                            .padding(start = 15.dp)
                                            .size(25.dp)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(x = (-10).dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Report Waste",
                                            color = textColor,
                                            fontFamily = monteBold,
                                            fontSize = 25.sp
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, top = 30.dp)
                                ) {
                                    Text(
                                        text = "Waste Photograph",
                                        color = textColor,
                                        fontSize = 15.sp,
                                        fontFamily = monteSB
                                    )
                                }
                                Card(
                                    backgroundColor = appBackground,
                                    shape = RoundedCornerShape(7.dp),
                                    elevation = 5.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 7.dp, vertical = 10.dp)

                                        .clickable {
                                            if (imageBitmap == null) {
                                                coroutineScope.launch {
                                                    if (!permissionState.allPermissionsGranted) {
                                                        permissionDrawerState.open()
                                                    } else {
                                                        launcher.launch(
                                                            ActivityOptionsCompat.makeBasic()
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                ) {
                                    if (imageBitmap == null) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.UploadFile,
                                                contentDescription = "",
                                                tint = Color(0xFFCFDCFE),
                                                modifier = Modifier.size(100.dp)
                                            )
                                            Text(
                                                text = "Upload Photo of Waste",
                                                color = textColor,
                                                fontSize = 16.sp,
                                                fontFamily = monteSB
                                            )
                                            Spacer(modifier = Modifier.height(30.dp))
                                        }
                                    } else {
                                        Image(
                                            bitmap = imageBitmap!!,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp),
                                            contentScale = ContentScale.Fit
                                        )

                                    }

                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, top = 30.dp)
                                        .clickable {
                                        }
                                ) {
                                    Text(
                                        text = "Type Your Location",
                                        color = textColor,
                                        fontSize = 15.sp,
                                        fontFamily = monteSB
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, top = 10.dp)
                                ) {
                                    ExposedDropdownMenuBox(
                                        expanded = isExpanded,
                                        onExpandedChange = {
                                            isExpanded = it
                                        }
                                    ) {
                                        OutlinedTextField(
                                            value = address,
                                            onValueChange = {
                                                if (it.length <= 200) {
                                                    address = it
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Maximum 200 words",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = textColor,
                                                backgroundColor = appBackground
                                            ),
                                            textStyle = TextStyle(
                                                color = textColor,
                                                fontFamily = monteSB,
                                                fontSize = 16.sp
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp)
                                                .onFocusEvent { focusState ->
                                                    if (focusState.isFocused) {
                                                        coroutineScope.launch {
                                                            bringIntoViewRequester.bringIntoView()
                                                        }
                                                    }
                                                }
                                                .border(1.dp, textColor),

                                            )
                                        ExposedDropdownMenu(
                                            expanded = isExpanded,
                                            onDismissRequest = {
                                                isExpanded = false
                                            },
                                            modifier = Modifier.background(appBackground)
                                        ) {
                                            viewModel.listOfAddresses.forEach {
                                                if (it != null) {
                                                    DropdownMenuItem(onClick = {
                                                        address = it
                                                        isExpanded = false
                                                    }) {
                                                        Text(text = it)
                                                    }
                                                }
                                            }


                                        }

                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 15.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Max Points you can Earn",
                                        color = textColor,
                                        fontSize = 16.sp,
                                        fontFamily = monteBold,
                                        modifier = Modifier.padding(start = 45.dp, end = 60.dp)
                                    )
                                    Row(modifier = Modifier.padding(end = 25.dp)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.coins),
                                            contentDescription = "coins",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 5.dp),
                                            tint = Color.Unspecified
                                        )
                                        Text(
                                            text = "20",
                                            color = textColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteNormal,
                                        )
                                    }

                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                if (reportWasteViewModel.tagsList.isEmpty()) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 15.dp, start = 10.dp, end = 20.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Tag the type of waste",
                                            color = textColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteSB
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Button(
                                            onClick = {
                                                openTags = !openTags
                                                coroutineScope.launch {
                                                    if (modalSheetState.isVisible) {
                                                        // Hide the bottom sheet
                                                        modalSheetState.hide()
                                                    } else {
                                                        // Show the bottom sheet
                                                        modalSheetState.show()
                                                    }
                                                }
                                            }, colors = ButtonDefaults.buttonColors(
                                                backgroundColor = CardColor,
                                                contentColor = CardTextColor
                                            ),
                                            shape = RoundedCornerShape(35.dp),
                                            modifier = Modifier.padding(start = 0.dp)
                                        ) {
                                            Text(
                                                text = "Add Tags",
                                                color = Color.White,
                                                fontFamily = monteNormal,
                                                fontSize = 15.sp
                                            )

                                        }

                                    }
                                } else {
                                    Column(
                                        modifier = Modifier.padding(
                                            start = 10.dp, end = 10.dp, top = 15.dp,
                                        )
                                    ) {
                                        Text(
                                            text = "Selected Tags",
                                            color = textColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteSB
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        LazyRow(
                                            contentPadding = PaddingValues(10.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            items(reportWasteViewModel.tagsList) { tag ->
                                                TagItem(
                                                    item = tag,
                                                    modifier = Modifier
                                                        .clickable(
                                                            interactionSource = remember { MutableInteractionSource() },
                                                            indication = null,
                                                            onClick = {
                                                                if (reportWasteViewModel.tagsList.contains(
                                                                        tag
                                                                    )
                                                                ) {
                                                                    reportWasteViewModel.tagsList.remove(
                                                                        tag
                                                                    )
                                                                } else {
                                                                    reportWasteViewModel.tagsList.add(
                                                                        tag
                                                                    )
                                                                }
                                                            }
                                                        ),
                                                    isSelected = false
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(100.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 100.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Button(
                                    onClick = {
                                        isDialogVisible = true
                                    }, colors = ButtonDefaults.buttonColors(
                                        backgroundColor = CardColor,
                                        contentColor = CardTextColor
                                    ),
                                    shape = RoundedCornerShape(35.dp),
                                    modifier = Modifier.padding(start = 0.dp)
                                ) {
                                    Text(
                                        text = "Report Waste",
                                        color = Color.White,
                                        fontFamily = monteNormal,
                                        fontSize = 15.sp
                                    )

                                }
                            }
                            DialogBox(
                                isVisible = isDialogVisible,
                                successRequest = {
                                    Toast.makeText(context, "Please Wait", Toast.LENGTH_SHORT)
                                        .show()
                                    if (bitmap != null) {
                                        val storageRef = FirebaseStorage.getInstance().reference
                                        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                                        var imageName = (1..10)
                                            .map { allowedChars.random() }
                                            .joinToString("")
                                        imageName = "Reported/${email}/${imageName}.jpg"

                                        val imageRef =
                                            storageRef.child(imageName) // Set desired storage location

                                        val baos = ByteArrayOutputStream()
                                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                                        val imageData = baos.toByteArray()

                                        val uploadTask = imageRef.putBytes(imageData)
                                        uploadTask.addOnSuccessListener { taskSnapshot ->
                                        }.addOnFailureListener { exception ->
                                            println("Firebase storage exception $exception")
                                        }.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                imageRef.downloadUrl.addOnSuccessListener {
                                                    println("Download url is $it")
                                                    updateWasteToFirebase(
                                                        context = context,
                                                        address = address,
                                                        latitude = viewModel.latitude,
                                                        longitude = viewModel.longitude,
                                                        imagePath = imageName,
                                                        timeStamp = System.currentTimeMillis(),
                                                        userEmail = email,
                                                        tags = reportWasteViewModel.tagsList.map { tag ->
                                                            tag.mapWithoutTips()
                                                        }
                                                    )
                                                    reportWasteViewModel.tagsList.clear()
                                                    viewModel.getCurrentLevel(
                                                        points = pointsEarned + calculatePointsEarned(
                                                            noOfTimesReported,
                                                            noOfTimesCollected,
                                                            noOfTimesActivity,
                                                            maxReported,
                                                            maxCollected,
                                                            maxCommunity
                                                        ),
                                                        levels = levels
                                                    )
                                                    viewModel.pointsEarned =
                                                        pointsEarned + calculatePointsEarned(
                                                            noOfTimesReported,
                                                            noOfTimesCollected,
                                                            noOfTimesActivity,
                                                            maxReported,
                                                            maxCollected,
                                                            maxCommunity
                                                        )
                                                    updateInfoToFirebase(
                                                        context,
                                                        name = name,
                                                        email = email,
                                                        phoneNumber = phoneNumber,
                                                        gender = gender,
                                                        organization = organization,
                                                        address = userAddress,
                                                        pointsEarned = pointsEarned + calculatePointsEarned(
                                                            noOfTimesReported,
                                                            noOfTimesCollected,
                                                            noOfTimesActivity,
                                                            maxReported,
                                                            maxCollected,
                                                            maxCommunity
                                                        ),
                                                        pointsRedeemed = pointsRedeemed,
                                                        noOfTimesReported = noOfTimesReported + 1,
                                                        noOfTimesCollected = noOfTimesCollected,
                                                        noOfTimesActivity = noOfTimesActivity,
                                                        communities = communities
                                                    )
                                                    isCOinVisible = true

                                                }
                                            }

                                        }

                                    }

                                    isDialogVisible = false
                                }) {
                                isDialogVisible = !isDialogVisible
                            }
                        }

                        if (isCOinVisible) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                val currenanim by rememberLottieComposition(
                                    spec = LottieCompositionSpec.Asset("coins.json")
                                )
                                LottieAnimation(
                                    composition = currenanim,
                                    iterations = 1,
                                    contentScale = ContentScale.Crop,
                                    speed = 1f,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .size(250.dp)
                                )
                            }
                            LaunchedEffect(key1 = isCOinVisible) {
                                if (isCOinVisible) {
                                    delay(4000)
                                    viewModel.showLevelDialog = true
                                    navController.navigate(Screens.Dashboard.route)
                                }
                            }

                        }
                        if (reportWasteViewModel.showTips.value){
                            AnimatedVisibility(visible = reportWasteViewModel.showTips.value) {
                                Box(modifier = Modifier, contentAlignment = Alignment.TopStart) {
                                    // Content of the screen
                                    DefaultSnackbar(
                                        snackbarHostState = scaffoldState.snackbarHostState,
                                        modifier = Modifier.align(Alignment.TopStart)
                                    ) {
                                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

