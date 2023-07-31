package com.pupdict.fastaid.ui.screens.learn

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import com.pupdict.fastaid.R
import com.pupdict.fastaid.testcomposables.OpenEmailer
import com.pupdict.fastaid.ui.components.learn.BulletPointText
import com.pupdict.fastaid.ui.components.learn.NoRippleInteractionSource
import com.pupdict.fastaid.ui.components.learn.NormalText
import com.pupdict.fastaid.ui.components.learn.NumberedText
import com.pupdict.fastaid.utils.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CovidScreen(
    modifier: Modifier = Modifier,
    onNavigate: (route: String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Learn - COVID-19") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(Routes.FASTAID_MAINSCREEN) }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier.height(48.dp))
            CovidScreenContent()
        }
    }
}

@Composable
fun CovidScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.covid_photo_1),
                    contentDescription = "Covid Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(text = "Creator: Maksim Tkachenko | Credit: Getty Images/iStockphoto", style = MaterialTheme.typography.bodySmall)
            }
        }
       ExpandableContentCard1(title = "Symptoms")
       ExpandableContentCard2(title = "Risk Factors")
       ExpandableContentCard3(title = "Treatment / Medication")
       ExpandableContentCard4(title = "FAQs")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCard1(
    modifier: Modifier = Modifier,
    title: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300 ,
                    easing = LinearEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { expandedState = !expandedState },
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = modifier
                            .weight(6f),
                        text = it,
                        style = titleStyle.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                    )
                }
                IconButton(
                    modifier = modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {

                Column(
                    modifier,
                    Arrangement.spacedBy(16.dp)
                ) {
                    NormalText(
                        text = "COVID-19 can present with various symptoms, and these may vary from one person to another. " + "Some of the common symptoms of COVID-19 include:"
                    )
                    BulletPointText(
                        text = "Fever or elevated body temperature"
                    )
                    BulletPointText(
                        text = "Cough or difficulty breathing"
                    )
                    BulletPointText(
                        text = "Fatigue or weakness"
                    )
                    BulletPointText(
                        text = "Runny nose or sore throat"
                    )
                    BulletPointText(
                        text = "Headache, sore throat, or muscle aches"
                    )
                    BulletPointText(
                        text = "Loss of smell or taste"
                    )
                    NormalText(
                        text = "Some people may also experience the following symptoms:"
                    )
                    BulletPointText(
                        text = "Nausea or diarrhea"
                    )
                    BulletPointText(
                        text = "Sore throat"
                    )
                    BulletPointText(
                        text = "Difficulty sleeping or loss of appetite"
                    )
                    NormalText(
                        text = "It is important to note that not everyone who has COVID-19 will develop symptoms, and some people may only have mild symptoms. " +
                                "However, they can still transmit the virus to others, so it is important to practice good hygiene and follow guidelines to prevent the spread of the virus."
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCard2(
    modifier: Modifier = Modifier,
    title: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300 ,
                    easing = LinearEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { expandedState = !expandedState },
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = modifier
                            .weight(6f),
                        text = it,
                        style = titleStyle.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                    )
                }
                IconButton(
                    modifier = modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Column(
                    modifier,
                    Arrangement.spacedBy(16.dp)
                )
                {
                    NormalText(
                        text = "The risk factors for COVID-19 can vary among individuals. Here are some common factors that may increase the risk of developing severe illness from COVID-19:"
                    )
                    BulletPointText(
                        title = "Advanced age",
                        text = "Older adults, especially those aged 65 and above, are at higher risk of severe illness and complications from COVID-19."
                    )
                    BulletPointText(
                        title = "Underlying Medical Conditions",
                        text = "People with certain underlying health conditions, such as heart disease, diabetes, chronic lung disease, obesity, and weakened immune systems, are more susceptible to severe illness if they contract COVID-19."
                    )
                    BulletPointText(
                        title = "Weakened Immune System",
                        text = "Individuals with weakened immune systems due to medical conditions or treatments, such as cancer treatments or organ transplantation, have an increased risk of severe illness."
                    )
                    BulletPointText(
                        title = "Living or Working in High-risk Settings",
                        text = "Close living quarters (such as nursing homes or prisons) or working in high-contact environments (such as healthcare facilities or crowded workplaces) can increase the risk of exposure to the virus."
                    )
                    BulletPointText(
                        title = "Ethnicity and Socioeconomic Factors",
                        text = "Certain racial and ethnic groups and individuals from lower socioeconomic backgrounds may face higher risk due to factors such as disparities in access to healthcare, living conditions, and occupation."
                    )
                    BulletPointText(
                        title = "Pregnancy",
                        text = "Pregnant individuals are at a slightly higher risk of severe illness compared to non-pregnant individuals, especially in the later stages of pregnancy."
                    )

                    NormalText(
                        text = "It is important to note that anyone, regardless of risk factors, can contract COVID-19 and experience severe illness. Taking preventive measures, such as practicing good hygiene, wearing masks, maintaining physical distancing, and getting vaccinated, can help reduce the risk of infection and severe outcomes."
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCard3(
    modifier: Modifier = Modifier,
    title: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300 ,
                    easing = LinearEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { expandedState = !expandedState },
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = modifier
                            .weight(6f),
                        text = it,
                        style = titleStyle.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                    )
                }
                IconButton(
                    modifier = modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {

                Column(
                    modifier,
                    Arrangement.spacedBy(10.dp)
                )
                {
                    NormalText(
                        text = "The treatment and medications for COVID-19 can vary depending on the severity of the illness. Here are some commonly used approaches:"
                    )
                    BulletPointText(
                        title = "Supportive Care",
                        text = "For mild cases, treatment focuses on supportive care, such as getting plenty of rest, staying hydrated, and using over-the-counter medications to relieve symptoms like fever and pain."
                    )
                    BulletPointText(
                        title = "Hospitalization:",
                        text = "Severe cases may require hospitalization for closer monitoring and advanced care. In the hospital, treatments may include supplemental oxygen to help with breathing, the use of antiviral medications, and administration of corticosteroids to reduce inflammation in the lungs."
                    )
                    BulletPointText(
                        title = "Oxygen Therapy",
                        text = "For individuals with low oxygen levels, oxygen therapy can be provided through various methods, such as nasal prongs, face masks, or ventilators. This helps to ensure adequate oxygen supply to the body."
                    )
                    BulletPointText(
                        title = "Antiviral Medications:",
                        text = "Specific antiviral medications, such as remdesivir, may be prescribed in certain cases to help inhibit the replication of the virus."
                    )
                    BulletPointText(
                        title = "Anti-inflammatory Medications:",
                        text = "In severe cases with excessive inflammation, corticosteroids like dexamethasone may be used to reduce inflammation and prevent further damage to the lungs."
                    )
                    BulletPointText(
                        title = "Monoclonal Antibodies",
                        text = "Monoclonal antibody treatments, such as casirivimab and imdevimab, may be given to individuals who are at high risk of developing severe illness or those with mild to moderate symptoms."
                    )

                    NormalText(
                        text = "It's important to note that the specific treatment and medication options may vary based on factors such as the patient's condition, available resources, and evolving medical guidelines. It is recommended to consult with healthcare professionals and follow their guidance for the most appropriate and up-to-date treatment options for COVID-19."
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCard4(
    modifier: Modifier = Modifier,
    title: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300 ,
                    easing = LinearEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { expandedState = !expandedState },
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = modifier
                            .weight(6f),
                        text = it,
                        style = titleStyle.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                    )
                }
                IconButton(
                    modifier = modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {

                Column(
                    modifier,
                    Arrangement.spacedBy(16.dp)
                )
                {
                    NumberedText(
                        number = 1 ,
                        title = "What is COVID-19?",
                        description = "A: COVID-19 is a respiratory illness caused by the novel coronavirus SARS-CoV-2."
                    )
                    NumberedText(
                        number = 2 ,
                        title = "How does COVID-19 spread?",
                        description = "A: COVID-19 primarily spreads through respiratory droplets when an infected person talks, coughs, or sneezes. It can also spread by touching surfaces or objects contaminated with the virus and then touching the face."
                    )
                    NumberedText(
                        number = 3 ,
                        title = "What are the common symptoms of COVID-19?",
                        description = "A: Common symptoms include fever, cough, shortness of breath, fatigue, muscle or body aches, headache, sore throat, loss of taste or smell, and congestion or runny nose. Symptoms can range from mild to severe."
                    )
                    NumberedText(
                        number = 4 ,
                        title = "How long does it take for symptoms to appear after exposure?",
                        description = "A: Symptoms typically appear within 2 to 14 days after exposure to the virus. The average incubation period is around 5 to 6 days."
                    )
                    NumberedText(
                        number = 5 ,
                        title = "How can I protect myself and others from COVID-19?",
                        description = "A: To protect yourself and others, it is important to practice good hand hygiene, wear masks in public settings, maintain physical distancing, avoid large gatherings, and follow local health guidelines and regulations."
                    )
                    NumberedText(
                        number = 6 ,
                        title = "Is there a vaccine for COVID-19?",
                        description = "A: Yes, vaccines for COVID-19 have been developed and approved for use in many countries. Vaccination is an effective way to protect against severe illness and reduce the spread of the virus."
                    )
                    NumberedText(
                        number = 7 ,
                        title = "Are there any approved treatments for COVID-19?",
                        description = "A: Several treatments have been approved for COVID-19, including antiviral medications, anti-inflammatory drugs, and monoclonal antibodies. Treatment plans may vary depending on the severity of the illness."
                    )
                    NumberedText(
                        number = 8 ,
                        title = "What should I do if I think I have COVID-19?",
                        description = "A: If you have symptoms or think you have been exposed to the virus, it is important to self-isolate, avoid contact with others, and seek medical advice. Contact your healthcare provider or local health authorities for guidance on testing and further steps."
                    )
                    NormalText(
                        text = "It's important to note that the information provided here is general and may not cover all specific cases or developments. For the most accurate and up-to-date information, it is recommended to refer to reputable sources such as the World Health Organization (WHO) or your local health department."
                    )

                    Text(text = "Do you have suggestions? Or noticed a bug? Send us an e-mail! Tap the button below.", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))

                    OpenEmailer(
                        receiver = "fastaidpup@gmail.com",
                        subject = "Fast-aid App Suggestions / Bug Report" ,
                        body = "Hi! I would to <please state purpose here>."
                    )

                }
            }
        }
    }
}

