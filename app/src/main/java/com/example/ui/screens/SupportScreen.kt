package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.BharatViewModel
import com.example.ui.theme.*

@Composable
fun SupportScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formRequests by viewModel.formRequests.collectAsState()

    var customInquiry by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Govt Yojana Form Filling") }

    val categories = listOf(
        "Govt Yojana Form Filling",
        "PM Vishwakarma / Skill Card",
        "Artisan / Worker Verification",
        "Cab & Bus Ride Help",
        "Wallet & Payment Issue"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BharatBgLight)
    ) {
        // Header
        Surface(color = BharatNavy, shadowElevation = 2.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Customer Support & Real-time Helpdesk",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Official Support on Telegram: @ArjunRajput04",
                    color = Color(0xFFFFD54F),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Hero Telegram Banner
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("telegram_support_card")
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF0F3057),
                                        Color(0xFF1B497E),
                                        BharatTelegramBlue
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Telegram",
                                        tint = BharatTelegramBlue,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Telegram Live Agent",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = Color(0x33FFFFFF),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = "Online Now",
                                                color = Color(0xFF69F0AE),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "@ArjunRajput04",
                                        color = Color(0xFFFFD54F),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Have trouble filling Government Scheme forms (PM Vishwakarma, PMKVY, Awas, Ayushman)? Or need worker verification help? Contact our dedicated executive directly on Telegram for real-time form filling assistance!",
                                color = Color(0xFFE2E8F0),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.openTelegramSupport(context) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("launch_telegram_support_button")
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, tint = BharatNavy, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Chat with @ArjunRajput04 on Telegram",
                                    color = BharatNavy,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            // Quick Inquiry & Form Assistance Generator
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Submit Form Filling or Support Ticket",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BharatNavy
                        )
                        Text(
                            text = "Our agent @ArjunRajput04 will reach out immediately",
                            fontSize = 11.sp,
                            color = BharatTextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Category Dropdown / chips
                        Text("Select Inquiry Topic:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            categories.forEach { cat ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedCategory = cat }
                                ) {
                                    RadioButton(
                                        selected = selectedCategory == cat,
                                        onClick = { selectedCategory = cat }
                                    )
                                    Text(cat, fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = customInquiry,
                            onValueChange = { customInquiry = it },
                            label = { Text("Details / Scheme Name / Problem") },
                            placeholder = { Text("e.g. Need help to apply PM Vishwakarma toolkit grant") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (customInquiry.isNotBlank()) {
                                    viewModel.repository.submitFormAssistanceRequest(
                                        schemeId = "CUSTOM",
                                        schemeTitle = selectedCategory,
                                        name = viewModel.userProfile.value.name,
                                        phone = viewModel.userProfile.value.phone,
                                        address = viewModel.userProfile.value.location,
                                        notes = customInquiry
                                    )
                                    customInquiry = ""
                                    Toast.makeText(context, "Ticket created! Redirecting to Telegram @ArjunRajput04...", Toast.LENGTH_SHORT).show()
                                    viewModel.openTelegramSupport(context)
                                } else {
                                    Toast.makeText(context, "Please enter details of your inquiry", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Submit Ticket & Open Telegram Support", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // My Active Requests
            item {
                Text(
                    text = "My Assistance Requests (${formRequests.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
            }

            items(formRequests) { req ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = req.schemeTitle,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BharatNavy
                            )
                            Surface(
                                color = BharatEmeraldContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = req.status,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BharatEmerald,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Applicant: ${req.applicantName} (${req.applicantPhone})",
                            fontSize = 11.sp,
                            color = BharatTextSecondary
                        )
                        Text(
                            text = "Assigned Support: ${req.telegramAgent}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BharatTelegramBlue
                        )
                        Text(
                            text = "Notes: ${req.notes}",
                            fontSize = 11.sp,
                            color = BharatTextPrimary
                        )
                    }
                }
            }

            // Quick Verification Shortcut
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.navigateTo(AppScreen.VERIFICATION) }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = BharatEmerald, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("User & Worker Verification Desk", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BharatEmerald)
                            Text("Get verified green badge with Aadhaar & skill certificate", fontSize = 11.sp, color = BharatTextSecondary)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = BharatEmerald)
                    }
                }
            }

            // Frequently Asked Questions
            item {
                Text(
                    text = "Frequently Asked Questions (FAQ)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
            }

            val faqs = listOf(
                Pair(
                    "How do I apply for PM Vishwakarma ₹15,000 Toolkit?",
                    "Under PM Vishwakarma, eligible artisans in 18 traditional trades receive 5-7 days training and ₹15,000 voucher. Our customer support @ArjunRajput04 will help you verify your Aadhaar and submit your biometric trade verification."
                ),
                Pair(
                    "Are the skilled workers verified?",
                    "Yes, all workers on BharatSeva undergo multi-tier verification including phone OTP, Aadhaar KYC, and Gram Panchayat / Trade Skill checks."
                ),
                Pair(
                    "How does Bharat Wallet payment work?",
                    "You can load money into your wallet via any UPI app. When booking a service or cab, the funds are safely held until the service is completed to your satisfaction."
                )
            )

            items(faqs) { (q, a) ->
                FaqAccordionItem(question = q, answer = a)
            }
        }
    }
}

@Composable
fun FaqAccordionItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = BharatNavy,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = BharatNavy
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    HorizontalDivider(color = BharatBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = answer,
                        fontSize = 12.sp,
                        color = BharatTextSecondary,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
