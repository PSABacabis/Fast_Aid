package com.pupdict.fastaid

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pupdict.fastaid.ui.screens.learn.CovidScreen
import com.pupdict.fastaid.ui.screens.learn.HeartAttackScreen
import com.pupdict.fastaid.ui.screens.learn.medkit.MedkitScreen
import com.pupdict.fastaid.ui.screens.medicalID.add_edit_screen.BasicInfoAddEditScreen
import com.pupdict.fastaid.ui.screens.medicalID.add_edit_screen.MedicalNotesAddEditScreen
import com.pupdict.fastaid.ui.screens.medicalID.display_screen.MedicalIDScreen
import com.pupdict.fastaid.ui.theme.FastAidTheme
import com.pupdict.fastaid.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastAidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.FASTAID_MAINSCREEN
                    ) {
                        composable(
                            Routes.FASTAID_MAINSCREEN
                        ) {
                            FastAidMainScreen(
                                onNavigate = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }

                        //THREE INDEPENDENT BUT LINKED SCREENS FOR MEDICAL ID, BASIC INFO/MEDICAL NOTE EDIT
                        composable(
                            route = Routes.MEDICAL_ID_MEDICALID_VIEW
                        ) {
                            MedicalIDScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.MEDICAL_ID_BASICINFO_EDIT + "?basicInfoID={basicInfoID}",
                            arguments = listOf(
                                navArgument(name = "basicInfoID") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            BasicInfoAddEditScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                            )
                        }
                        composable(
                            route = Routes.MEDICAL_ID_MEDICALNOTES_EDIT + "?medicalNotesID={medicalNotesID}",
                            arguments = listOf(
                                navArgument(name = "medicalNotesID") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            MedicalNotesAddEditScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            route = Routes.LEARN_LEARN_TOPICS
                        ) {
                            CovidScreen(
                                onNavigate = { route ->
                                    navController.navigate(route)}
                            )
                        }
                        composable(
                            route = Routes.LEARN_AFA_TOPICS_HA
                        ) {
                            HeartAttackScreen(
                                onNavigate = { route ->
                                navController.navigate(route)}
                            )
                        }
                        composable(
                            route = Routes.LEARN_MEDKIT_TOPICS_EPP
                        ) {
                            MedkitScreen(
                                onNavigate = { route ->
                                    navController.navigate(route)}
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FastAidMainScreen(
    onNavigate: (route: String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp) ,
    ) {
        Column(
            modifier = Modifier,
            Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(), Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_fastaid_logo_light) , contentDescription = "fastaidlogo")
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(
                text = "Welcome to Fast Aid!",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp) ,
                content = {

                    item {
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .padding(4.dp)
                                .size(152.dp)
                                .clickable { onNavigate(Routes.MEDICAL_ID_MEDICALID_VIEW) },
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(3f),
                                Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(painter = painterResource(id = R.drawable.medicalid) , contentDescription = null, modifier = Modifier.size(56.dp) )
                            }

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer))
                            {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Medical ID",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .padding(4.dp)
                                .size(152.dp)
                                .clickable { onNavigate(Routes.LEARN_AFA_TOPICS_HA) },
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(3f),
                                Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(painter = painterResource(id = R.drawable.medkiticon) , contentDescription = null, modifier = Modifier.size(56.dp) )
                            }

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer))
                            {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Apply First Aid",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .padding(4.dp)
                                .size(152.dp)
                                .clickable { onNavigate(Routes.LEARN_LEARN_TOPICS) },
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(3f),
                                Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(painter = painterResource(id = R.drawable.learnicon) , contentDescription = null, modifier = Modifier.size(56.dp) )
                            }

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer))
                            {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Learn",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .padding(4.dp)
                                .size(152.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(3f)
                                .clickable { onNavigate(Routes.LEARN_MEDKIT_TOPICS_EPP) },
                                Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(painter = painterResource(id = R.drawable.medicalid) , contentDescription = null, modifier = Modifier.size(56.dp) )
                            }

                            Row(modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer))
                            {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Medkit",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            )

            Text(
                text = "Emergency Panic Buttons",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                content = {
                    item {
                        EmergencyButton(number = "111")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        EmergencyButton(number = "222")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        EmergencyButton(number = "09953339489")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        EmergencyButton(number = "911")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            )
        }
    }
}

@Composable
fun EmergencyButton(number: String) {
    val phoneNumber by remember { mutableStateOf(number) }
    val ctx = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
        onClick = {
            val u = Uri.parse("tel:$phoneNumber")
            val i = Intent(Intent.ACTION_DIAL, u)
            try { ctx.startActivity(i) }
            catch (s: SecurityException)
            { Toast.makeText(ctx, "An error occurred", Toast.LENGTH_LONG).show() }
        }
    ) {
        Text(
            text = "Call $number",
            modifier = Modifier.padding(10.dp),
            color = Color.White,
            fontSize = 15.sp
        )
    }
}






